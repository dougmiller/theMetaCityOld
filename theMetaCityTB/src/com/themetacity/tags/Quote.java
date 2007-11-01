package com.themetacity.tags;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This tag generates formatted quotes. The output looks like this.<br />
 * <br />
 * &lt;div&nbsp;class=&quot;quote&quot;&gt;<br />
 * &nbsp;&nbsp;&lt;span&nbsp;class=&quot;quotetext&quot;&gt;&quot;Some&nbsp;guy's&nbsp;quote&nbsp;text&nbsp;here&quot;&lt;/span&gt;<br />
 * &nbsp;&nbsp;&lt;hr&nbsp;/&gt;<br />
 * &nbsp;&nbsp;&lt;div&nbsp;class=&quot;authorposition&quot;&gt;<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;span&nbsp;class=&quot;quoteauthor&quot;&gt;Some&nbsp;guy&nbsp;quoted&lt;/span&gt;<br />
 * &nbsp;&nbsp;&lt;/div&gt;<br />
 * &lt;/div&gt;<br />
 */
public class Quote extends SimpleTagSupport {

    // Only need to set the quote author in a variable as the actual quote is captures as a jsp
    // fragment and handles by the tag class
    private String quoteAuthor = null;

    public void doTag() throws JspException, IOException {

        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();

        JspWriter out = jspContext.getOut();

        // Begin outputting content
        out.println("<div class=\"quote\">");
        out.println("  <span class=\"quotetext\">");
        // This gets the contents of the body and outputs it. Very important!
        getJspBody().invoke(null);
        out.println("  </span>");
        out.println("  <hr />");
        out.println("  <div class=\"authorposition\">");
        out.println("    <span class=\"quoteauthor\">" + quoteAuthor + "</span>");
        out.println("  </div>");
        out.println("</div>");
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public void release() {
        quoteAuthor = null;
    }
}
