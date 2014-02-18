package com.themetacity.tags;

import com.themetacity.typebeans.ImportantNoticeBean;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImportantNotice extends SimpleTagSupport {

    private ImportantNoticeBean importantNotice = new ImportantNoticeBean();

    static Logger logger = LogManager.getLogger(ImportantNotice.class);

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("<aside class=\"importantNotice\">");
            out.println("   <p>" + importantNotice.getMessage() + "</p>");
            out.println("   <p class=\"importantNoticeFooter\">Starting: " + importantNotice.getDateFrom() + " Finishing: " + importantNotice.getDateTo() + "</p>");
            out.println("</aside>");
        } catch (IOException IOEx) {
            logger.warn("There was an error in ImportantNotices tag.");
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
