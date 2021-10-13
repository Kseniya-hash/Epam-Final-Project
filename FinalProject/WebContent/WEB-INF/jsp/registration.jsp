<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<fmt:setLocale value="${ pageContext.response.locale }" scope="session"/>
<title>
	<fmt:bundle basename="resources.pagecontent" prefix="register.">
		<fmt:message key="registration"/>
	</fmt:bundle>
</title>
</head>
<body>
<fmt:bundle basename="resources.pagecontent" prefix="register.">
<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<div class="container">
	<div class="row">
		<div class="col-md-7 mb-12">
			<form name="LoginForm" class="form-control" method="POST" action="Controller">
				<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
				<input type="hidden" name="command" value="registration"/>
				${ errorRegistrationMessage }<br/>
				<label><fmt:message key="phonenumber"/></label><br/>
				<input type="text" class="form-control" name="phone" pattern="^([0-9]){12}$" required value=""/><br/>
				<label><fmt:message key="login"/></label><br/>
				<input type="text" class="form-control" name="login" required value=""/><br/>
				<label><fmt:message key="password"/></label><br/>
				<input type="password" class="form-control" name="password" required 
				attern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[\]:;<>,.?\/\\~_+-=|]).{8,20}$"
				value=""/>
				<small class="text-muted">
				<fmt:message key="passwordhint"/>
				 <br/>
				</small><br/>
				<label><fmt:message key="passwordrepeat"/></label><br/>
				<input type="password" class="form-control" name="passwordrepeat" required
				pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[\]:;<>,.?\/\\~_+-=|]).{8,20}$"
				value=""/><br>
				<label><fmt:message key="name"/></label><br/>
				<input type="text" class="form-control" name="name" required value=""/><br/>
				<label><fmt:message key="email"/></label><br/>
				<input type="text" class="form-control" name="email" pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[\]:;<>,.?\/\\~_+\-=|]).{8,20}$" value=""/><br/>
				<button type="submit" class="btn btn-primary"><fmt:message key="register"/></button></br>
			</form>
		</div>
	</div>
</div>
<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>