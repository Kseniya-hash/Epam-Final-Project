<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><c:out value="${ product.name }"/></title>
</head>
<body>
<fmt:bundle basename="resources.pagecontent" prefix="product.">
<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<div class="container">
	<div class="row">
		<div class="col-md-8">
			<c:out value="${ product.name }"/><br/>
		</div>
		<div class="col-md-4">
			<c:choose>
				<c:when test="${ user.role == 'admin' }">
					<fmt:message key="sellingprice"/>
					<fmt:formatNumber value="${ price.sellingPrice/100 }" type="currency"/><br>
					<fmt:message key="purchaseprice"/> 
					<fmt:formatNumber value="${ price.purchasePrice/100 }" type="currency"/><br>
				</c:when>
				<c:otherwise>
					<fmt:message key="price"/>
					<fmt:formatNumber value="${ price.sellingPrice/100 }" type="currency"/><br>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${ product.quantity > 0 }">
					<fmt:message key="available"/> 
					<c:out value="${ product.quantity }"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="unavailable"/>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<fmt:message key="description"/><br/>
	<c:out value="${ product.description }"/></br></br>
	<c:if test="${ not empty product.weight }">
		<div class="row">
			<div class="col-md-2">
				<fmt:message key="weight"/>
			</div>
			<div class="col-md-3">
				<c:out value="${ product.weight }"/>
				<fmt:message key="gramm"/>
			</div>
		</div>
	</c:if>
	<c:if test="${ not empty product.length }">
		<div class="row">
			<div class="col-md-2">
				<fmt:message key="length"/>
			</div>
			<div class="col-md-3">
				<c:out value="${ product.length }"/>
				<fmt:message key="sm"/>
			</div>
		</div>
	</c:if>
	<c:if test="${ not empty product.high }">
		<div class="row">
			<div class="col-md-2">
				<fmt:message key="high"/>
			</div>
			<div class="col-md-3">
				<c:out value="${ product.high }"/>
				<fmt:message key="sm"/>
			</div>
		</div>
	</c:if>
	<c:if test="${ not empty product.width }">
		<div class="row">
			<div class="col-md-2">
				<fmt:message key="width"/>
			</div>
			<div class="col-md-3">
				<c:out value="${ product.width }"/>
				<fmt:message key="sm"/>
			</div>
		</div>
	</c:if>
	<div class="row">
		<div class="col-md-2">
			<form name="addToCart" method="POST" action="Controller">
					<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
  					<input type="hidden" name="command" value="cart_incriment"/>
    				<button type="submit" 
    				class="btn btn-primary" 
    				name="product_id" 
    				value = ${ product.id }><fmt:message key="addtocart"/></button>
    		</form>
		</div>
	<c:if test="${ user.role == 'admin' }">
		<div class="col-md-4">
			<form name="redact" method="GET" action="Controller">
  				<input type="hidden" name="command" value="to_redact_product"/>
    			<button type="submit" 
    				class="btn btn-primary" 
    				name="product_id" 
   					value = ${ product.id }><fmt:message key="redact"/></button>
    		</form>
		</div>
	</c:if>
	</div>
</div>
<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>