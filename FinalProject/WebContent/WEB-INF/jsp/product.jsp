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
	<h1><c:out value="${ product.name }"/><br/></h1>
	<div class="row">
		<div class="col-md-6">
			<img src="images${ product.photoPath }" class="img-fluid" alt="product photo">
		</div>
		<div class="col-md-6">
			<c:choose>
				<c:when test="${ user.role == 'admin' }">
					<fmt:message key="sellingprice"/>
					<fmt:formatNumber value="${ price.sellingPrice/100 }" 
						type="currency" currencyCode ="BYR"/><br>
					<fmt:message key="purchaseprice"/> 
					<fmt:formatNumber value="${ price.purchasePrice/100 }" 
						type="currency" currencyCode ="BYR"/><br>
				</c:when>
				<c:otherwise>
					<fmt:message key="price"/>
					<fmt:formatNumber value="${ price.sellingPrice/100 }" 
						type="currency" currencyCode ="BYR"/><br>
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
			</br>
			</br>
			<fmt:message key="description"/><br/>
			<c:out value="${ product.description }"/></br></br>
			<c:if test="${ not empty product.weight }">
				<div class="row">
					<div class="col-md-4">
						<fmt:message key="weight"/>
					</div>
					<div class="col-md-4">
						<c:out value="${ product.weight }"/>
					</div>
				</div>
			</c:if>
			<c:if test="${ not empty product.length }">
				<div class="row">
					<div class="col-md-4">
						<fmt:message key="length"/>
					</div>
					<div class="col-md-4">
						<c:out value="${ product.length }"/>
					</div>
				</div>
			</c:if>
			<c:if test="${ not empty product.high }">
				<div class="row">
					<div class="col-md-4">
						<fmt:message key="high"/>
					</div>
					<div class="col-md-4">
						<c:out value="${ product.high }"/>
					</div>
				</div>
			</c:if>
			<c:if test="${ not empty product.width }">
				<div class="row">
					<div class="col-md-4">
						<fmt:message key="width"/>
					</div>
					<div class="col-md-4">
						<c:out value="${ product.width }"/>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	
	<br/>
	<br/>
	<div class="row">
		<div class="col-md-3">
			<form name="addToCart" method="POST" action="Controller">
					<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
  					<input type="hidden" name="command" value="cart_incriment"/>
    				<button type="submit" 
    				class="btn btn-primary" 
    				name="product_id" 
    				value = "${ product.id }"><fmt:message key="addtocart"/></button>
    		</form>
		</div>
	<c:if test="${ user.role == 'admin' }">
		<div class="col-md-4">
			<form name="redact" method="GET" action="Controller">
  				<input type="hidden" name="command" value="to_redact_product"/>
    			<button type="submit" 
    				class="btn btn-primary" 
    				name="product_id" 
   					value = "${ product.id }"><fmt:message key="redact"/></button>
    		</form>
		</div>
	</c:if>
	</div>
	<hr>
	
	<h3><fmt:message key ="comments"/></h3>
	<ul class="list-unstyled">
		<c:forEach var = "comment" items="${ comments }">
			<li class="media">
				<div class="media-body">
    				<h4 class="mt-0"><c:out value="${ comment.userLogin }"/>
    				<c:forEach begin = "1" end = "${ comment.rating }">
    					<img src="images/star.png" class="img-fluid" alt="${ comment.rating }">
    				</c:forEach>
    				</h4>
   	 				<c:out value="${ comment.text }"/>
  				</div>
			</li>
			<hr>
		</c:forEach>
	</ul>
	
	<c:if test="${ not empty user }">
		<form name="AddComment" method="POST" action="Controller">
			<input type = "hidden" name = "command" value = "create_comment">
			<input type = "hidden" name = "csrf_tokin" value="${ csrf_tokin }">
			<input type = "hidden" name = "product_id" value = "${ product.id }">
			<c:forEach begin = "1" end = "5" varStatus="currentRating">
				 <label class="form-check-label" for="comment_rating">
				 	<c:out value = "${ currentRating.index }"/>
				 </label>
				 <input class="form-check form-check-inline" 
				 type="radio" 
				 name="comment_rating"
				 required
				 value = "${ currentRating.index }">
			</c:forEach>
			<textarea class="form-control" name="comment_text"></textarea>
			<br>
			<button type="submit" class="btn btn-primary float-right btn-sm">
				<fmt:message key ="add"/>
			</button>
			<br>
		</form>
	</c:if>	
</div>
<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>