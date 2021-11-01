package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.util.MessageManager;
import by.epamtc.dubovik.shop.controller.util.RequestUtil;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.CommentService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class CreateCommentCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	private final static String INVALID_COMMENT = "modal.comment";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		ServiceFactory factory = ServiceFactory.getInstance();
		
		CommentService commentService = factory.getCommentService();
		RequestUtil requestUtil = RequestUtilFactory.getInstance().getRequestUtil();
		
		Comment comment = new Comment();
		comment.setProductId(Long.parseLong(request
				.getParameter(ParameterName.PRODUCT_ID)));
		comment.setUserId(user.getId());
		comment.setRating(requestUtil
				.takeIntegerWithNull(request, ParameterName.COMMENT_RATING));
		comment.setText(request.getParameter(ParameterName.COMMENT_TEXT));
		
		try {
			commentService.createComment(comment);
			
		} catch (InvalidException e) {
			logger.error(e);
			
			MessageManager messageManager = 
					RequestUtilFactory.getInstance().getMessageManager();
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(INVALID_COMMENT, response.getLocale()));
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}

		request.getRequestDispatcher(page).forward(request, response);
	}

}
