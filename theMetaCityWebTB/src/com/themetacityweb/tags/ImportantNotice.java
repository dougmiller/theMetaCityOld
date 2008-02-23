package com.themetacityweb.tags;

import com.themetacityweb.typebeans.ImportantNoticeBean;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class ImportantNotice extends SimpleTagSupport {
    private ImportantNoticeBean importantNotice = new ImportantNoticeBean();

    public void doTag() throws JspException, IOException {
             JspWriter out = getJspContext().getOut();
        try {
            out.println("<div class=\"importantNotice\">");
            out.println(importantNotice.getMessage());
            out.println("<div class=\"noticeAuthor\">- " + importantNotice.getAuthor() + "</div>");
            out.println("</div>");

        } catch (IOException IOex) {
            System.out.println("There was an error.");
        }

    }

    public void release(){
     importantNotice = null;
    }

    public ImportantNoticeBean getImportantNotice() {
        return importantNotice;
    }

    public void setImportantNotice(ImportantNoticeBean importantNotice) {
        this.importantNotice = importantNotice;
    }    
}
