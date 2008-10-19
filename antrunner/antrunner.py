import serial
import os

ser = serial.Serial()

try:
    ser = serial.Serial('/dev/tty.usbserial-A7007ber', 9600, timeout=1)
except serial.SerialException:
    print 'Could not find the serial connection. Is the Arduino connected and on?'
    os._exit(1)

while 1:
    try:
        fromArduino = ser.readline().rstrip()
    except OSError:
        print 'Arduino disconnected.'
        break
    if fromArduino == 'go':
        ser.write("p")   # p for processing, to tell the arduino it is running
        print 'Building project. Please wait.'
        result = os.popen("ant -f /Users/doug/Code/theMetaCity/utils/masterCityBuild.xml").readlines()
        try:
            if result[-2:-1][0].rstrip() == 'BUILD SUCCESSFUL':
                print 'Build successful.', result[-1:][0].rstrip()
                ser.write("1")   # for completed successfully (true)
            else:
                ser.write("0")   # there was a problem (false)
                for line in result:
                    print line.rstrip()
        except IndexError:       # There was a problem parseing the file. This is indicitive of something breaking in the popen line
            ser.write("0")