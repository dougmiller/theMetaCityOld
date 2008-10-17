package com.themetacity.tags;

import com.themetacity.typebeans.ImportantNoticeBean;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ImportantNotice extends SimpleTagSupport {

    private ImportantNoticeBean importantNotice = new ImportantNoticeBean();

    static Logger logger = Logger.getLogger(ImportantNotice.class);

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("<div class=\"importantNotice\">");
            out.println("   " + importantNotice.getMessage());
            out.println("   <div class=\"importantnoticedates\">Posted: " + importantNotice.getDateFrom() + " End: " + importantNotice.getDateTo() + "</div>");
            out.println("   <div class=\"noticeAuthor\">- " + importantNotice.getAuthor() + "</div>");
            out.println("</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error.");
            logger.warn(IOEx);
        }
    }

    public void release() {
        importantNotice = null;
    }

    public ImportantNoticeBean getImportantNotice() {
        return importantNotice;
    }

    public void setImportantNotice(ImportantNoticeBean importantNotice) {
        this.importantNotice = importantNotice;
    }
}
