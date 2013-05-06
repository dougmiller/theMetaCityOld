package com.themetacity.tags;

import com.themetacity.typebeans.ImportantNoticeBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class AdminImportantNotice extends SimpleTagSupport {
    private ImportantNoticeBean noticeBean = new ImportantNoticeBean();

    private static Logger logger = LogManager.getLogger(AdminImportantNotice.class);

    public void doTag() {
        JspWriter out = getJspContext().getOut();

        try {
            out.println("<div>");
            out.println("   " +noticeBean.getMessageID());
            out.println("   <input type=\"hidden\" name=\"messageID\" value=\"" + noticeBean.getMessageID() + "\"/>" );
            out.println("   <button type=\"submit\" class=\"submitbutton\" name=\"submit\" value=\"edit\"><img src=\"siteimages/page_edit.png\" alt=\"Update\"/>Edit</button>");
            out.println("   <button type=\"submit\" class=\"deletebutton\" name=\"submit\" value=\"delete\"><img src=\"siteimages/delete.png\" alt=\"Delete\"/>Delete</button>");
            out.println("</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    public ImportantNoticeBean getNoticeBean() {
        return noticeBean;
    }

    public void setNoticeBean(ImportantNoticeBean noticeBean) {
        this.noticeBean = noticeBean;
    }
}
