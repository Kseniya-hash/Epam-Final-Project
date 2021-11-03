package by.epamtc.dubovik.shop.controller.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class MultipartFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws ServletException, IOException
    {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	HttpServletRequest parsedRequest = parseRequest(httpRequest);
    	chain.doFilter(parsedRequest, response);
    }

    public void destroy() {
    }

    private HttpServletRequest parseRequest(HttpServletRequest request) 
    		throws ServletException {

        if (!ServletFileUpload.isMultipartContent(request)) {
            return request;
        }

        List<FileItem> multipartItems = null;

        try {
            multipartItems = new ServletFileUpload(new DiskFileItemFactory())
            		.parseRequest(request);
        } catch (FileUploadException e) {
            throw new ServletException(e);
        }

        Map<String, String[]> parameterMap = new HashMap<String, String[]>();

        for (FileItem multipartItem : multipartItems) {
            if (multipartItem.isFormField()) {
                processFormField(multipartItem, parameterMap, request.getCharacterEncoding());
            } else {
                processFileField(multipartItem, request);
            }
        }

        return wrapRequest(request, parameterMap);
    }

    private void processFormField(FileItem formField, 
    		Map<String, String[]> parameterMap, 
    		String encoding) {
    	
        String name = formField.getFieldName();
        String value;
        String[] values;
		try {
			value = formField.getString(encoding);
		} catch (UnsupportedEncodingException e) {
			value = formField.getString();
		}
		values = parameterMap.get(name);
        if (values != null) {
        	int length = values.length;
            String[] newValues = new String[length + 1];
            newValues[length] = value;
            parameterMap.put(name, newValues);
        } else {
        	parameterMap.put(name, new String[] { value });
        }
    }

    private void processFileField(FileItem fileField, HttpServletRequest request) {
        if (fileField.getName().length() <= 0) {
            request.setAttribute(fileField.getFieldName(), null);
        } else {
            request.setAttribute(fileField.getFieldName(), fileField);
        }
    }

    private static HttpServletRequest wrapRequest(
        HttpServletRequest request, final Map<String, String[]> parameterMap)
    {
        return new HttpServletRequestWrapper(request) {
            public Map<String, String[]> getParameterMap() {
                return parameterMap;
            }
            public String[] getParameterValues(String name) {
                return parameterMap.get(name);
            }
            public String getParameter(String name) {
                String[] params = getParameterValues(name);
                return params != null && params.length > 0 ? params[0] : null;
            }
            public Enumeration<String> getParameterNames() {
                return Collections.enumeration(parameterMap.keySet());
            }
        };
    }
}