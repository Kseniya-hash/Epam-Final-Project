package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.CommentService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.util.RequestUtil;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class CreateCommentCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		ServiceFactory factory = ServiceFactory.getInstance();
		
		CommentService commentService = factory.getCommentService();
		RequestUtil requestUtil = factory.getRequestUtil();
		
		Comment comment = new Comment();
		comment.setProductId(Integer.parseInt(request.getParameter(ParameterName.PRODUCT_ID)));
		comment.setUserId(user.getId());
		comment.setRating(requestUtil.takeIntegerWithNull(request, ParameterName.COMMENT_RATING));
		comment.setText(requestUtil.takeString(request, ParameterName.COMMENT_TEXT));
		try {
			commentService.createComment(comment);
		} catch (ServiceException | InvalidException e) {
			page = Page.ERROR404;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
