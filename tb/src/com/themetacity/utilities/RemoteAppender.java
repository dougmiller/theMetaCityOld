package com.themetacity.utilities;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * This appender works much like log4j's Socket Appender.
 * The main difference is that it sends strings to
 * remote clients rather than sending serialized
 * LoggingEvent objects. This approach has the
 * advantages of being considerably faster (serialization
 * is not cheap) and of not requiring the client
 * application to be coupled to log4j at all.
 * <p/>
 * <p>This appender takes only one "parameter," which specifies
 * the port number (defaults to 9999). Set it with:
 * <PRE>
 * log4j.appender.R=com.holub.log4j.RemoteAppender;
 * ...
 * log4j.appender.R.Port=1234
 * </PRE>
 */

public class RemoteAppender extends AppenderSkeleton {
    // The iterator across the "clients" Collection must
    // support a "remove()" method.

    private LinkedList<OutputStreamWriter> clients = new LinkedList<OutputStreamWriter>();
    private int port = 7890;
    private ServerSocket listenerSocket;
    private Thread listenerThread;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public boolean requiresLayout() {
        return true;
    }

    /**
     * Called once all the options have been set. Starts
     * listening for clients on the specified socket.
     */
    public void activateOptions() {
        try {
            listenerSocket = new ServerSocket(port);
            listenerThread = new Thread() {
                public void run() {
                    try {
                        Socket clientSocket;
                        while ((clientSocket = listenerSocket.accept()) != null) {   // Create a (deliberately) unbuffered writer
                            // to talk to the client and add it to the
                            // collection of listeners.

                            synchronized (clients) {
                                clients.add(new OutputStreamWriter(clientSocket.getOutputStream()));
                            }
                        }
                    }
                    catch (SocketException e) {   // Normal close operation. Doing nothing
                        // terminates the thread gracefully.
                    }
                    catch (IOException e) {   // Other IO errors also kill the thread, but with
                        // a logged message.
                        errorHandler.error("I/O Exception in accept loop" + e);
                    }
                }
            };
            listenerThread.setDaemon(true);
            listenerThread.start();
        }
        catch (IOException e) {
            errorHandler.error("Can't open server socket: " + e);
        }
    }

    /**
     * Actually do the logging. The AppenderSkeleton's
     * doAppend() method calls append() to do the
     * actual logging after it takes care of required
     * housekeeping operations.
     */

    public synchronized void append(LoggingEvent event) {
        // If this Appender has been closed or if there are no
        // clients to service, just return.

        if (listenerSocket == null || clients.size() <= 0) {
            return;
        }

        if (this.layout == null) {
            errorHandler.error("No layout for appender " + name,
                    null, ErrorCode.MISSING_LAYOUT);
            return;
        }

        String message = this.layout.format(event);

        // Normally, an exception is thrown by the synchronized collection
        // when somebody (i.e., the listenerThread) tries to modify it
        // while iterations are in progress. The following synchronized
        // statement causes the listenerThread to block in this case,
        // but note that connections that can't be serviced quickly
        // enough might be refused.

        synchronized (clients) {
            for (Iterator i = clients.iterator(); i.hasNext();) {
                Writer out = (Writer) (i.next());
                try {
                    out.write(message, 0, message.length());
                    out.flush();
                    // Boilerplate code: handle exceptions if not
                    // handled by layout object:
                    //
                    if (layout.ignoresThrowable()) {
                        String[] messages = event.getThrowableStrRep();
                        if (messages != null) {
                            for (String message1 : messages) {
                                out.write(message1, 0, message1.length());
                                out.write('\n');
                                out.flush();
                            }
                        }
                    }
                }
                catch (IOException e)  // Assume that the write failed
                {
                    i.remove();         // because the connection is closed.
                }
            }
        }
    }

    public synchronized void close() {
        try {
            if (listenerSocket == null) // Already closed.
            {
                return;
            }
            listenerSocket.close();     // Also kills listenerThread.

            // Now close all the client connections.
            for (Iterator i = clients.iterator(); i.hasNext();) {
                ((Writer) i.next()).close();
                i.remove();
            }

            listenerThread.join();      // Wait for thread to die.
            listenerThread = null;     // Allow everything to be
            listenerSocket = null;     // garbage collected.
            clients = null;
        }
        catch (Exception e) {
            errorHandler.error("Exception while closing: " + e);
        }
    }
}


