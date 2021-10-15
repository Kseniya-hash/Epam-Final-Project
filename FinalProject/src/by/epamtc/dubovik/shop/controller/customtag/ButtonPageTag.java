package by.epamtc.dubovik.shop.controller.customtag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class ButtonPageTag extends TagSupport {
	private int currentPage;
	private int lastPage;
	private int offset;
	private String buttonClass;
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}
	
	@Override
	public int doStartTag() throws JspException {
		StringBuilder pageList = new StringBuilder();
		addButton(pageList, 1, buttonClass);
		int current = (currentPage > offset  + 1 )? currentPage - offset : 2;
		if(current != 2) {
			pageList.append(" ... ");
		}
		while(current <= currentPage + offset && current < lastPage) {
			addButton(pageList, current, buttonClass);
			++current;
		}
		if(current < lastPage) {
			pageList.append(" ... ");
		}
		if(current <= lastPage) {
			addButton(pageList, lastPage, buttonClass);
		}
		try {
			JspWriter out = pageContext.getOut();
			out.write(pageList.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
 		return SKIP_BODY;
	}
	
	private void addButton(StringBuilder builder, int currentPageValue, String buttonClass) {
		builder.append( "<button type=\"submit\" class=\"" + buttonClass + "\" name=\"page_number\" value=\"");
		builder.append(currentPageValue);
		builder.append( "\">" + currentPageValue + "</button>   ");
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
