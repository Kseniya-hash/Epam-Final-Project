<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
Login
<fmt:bundle basename="resources.pagecontent" prefix="login.">
		<fmt:message key="log_in"/>
	</fmt:bundle>
</title>
</head>
<body>
<fmt:bundle basename="resources.pagecontent" prefix="login.">
<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<div class="container">
	<div class="row">
		<div class="col-md-7 mb-12">
			<form class="form-control" name="LoginForm" method="POST" action="Controller">
				<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
				<input type="hidden" name="command" value="login"/>
				<label><fmt:message key="login"/></label><br/>
				<input type="text" class="form-control" name="login" value="" required/><br/>
				<label><fmt:message key="password"/></label><br/>
				<input type="password" class="form-control" name="password" value="" required/><br/>
				${ errorLoginPassMessage }
				${ wrongAction }
				${ nullPage }<br/>
				<button type="submit" class="btn btn-primary"><fmt:message key="log_in"/></button>
			</form>
		</div>
	</div>
</div>
<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>