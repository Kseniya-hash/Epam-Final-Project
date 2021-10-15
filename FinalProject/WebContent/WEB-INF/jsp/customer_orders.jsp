<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<fmt:setLocale value="${ pageContext.response.locale }" scope="session"/>
<title>
	<fmt:bundle basename="resources.pagecontent" prefix="header.">
		<fmt:message key="customerorders"/>
	</fmt:bundle>
</title>
</head>
<body>
	<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<fmt:bundle basename="resources.pagecontent" prefix="order.">
	<div class="container">
		
		<c:forEach var="elem" items="${ orders }">
			<div class="row">
				<div class="col-md-8">
				
					<fmt:message key="ordernumber"/>
					<c:out value="${ elem.id }"/><br/>
					<fmt:message key="orderdate"/>
					<fmt:formatDate value="${ elem.date }" type="both" timeStyle="short"/><br/>
					<c:out value="${ elem.userLogin }"/><br/>
					<c:out value="${ elem.userPhone }"/><br/>
					<p class="text-danger font-weight-bold">
						<fmt:message key="status"/> <c:out value="${ elem.orderStatus }"/>
					</p><br/>
					<c:forEach var="sale" items="${ elem.sales }">
						<c:url value ="Controller?command=show_product&product_id=${ sale.productId }" var="productUrl"/>
						<a href ='<c:out value="${ productUrl }"/>'><c:out value="${ sale.productName }"/></a><br/>
						<div class="row">
							<div class="col-md-5">
								<fmt:message key="ordered"/> <c:out value="${ sale.quantity }"/>
							</div>
							<div class="col-md-5">
								<fmt:message key="price"/>
								<fmt:formatNumber value="${ sale.price/100 }" type="currency"/>
							</div>
							</br>
							</br>
						</div>
					</c:forEach>
				</div>
				<div class="col-md-4">
					<c:if test="${ elem.orderStatus eq 'оплачен' }">
						<form name="Deliver" method="POST" action="Controller">
  							<input type="hidden" name="command" value="deliver_order"/>
  							<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
    						<button type="submit" 
    							class="btn btn-primary" 
    							name="order_id" 
   								value = "${ elem.id }"><fmt:message key="deliver"/></button>
    					</form>
					</c:if>
					</br>
					<c:choose>
						<c:when test="${ elem.isBlacklisted eq false }">
							<form name="Blacklist" method="POST" action="Controller">
  								<input type="hidden" name="command" value="blacklist_user"/>
  								<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
    							<button type="submit" 
    								class="btn btn-primary" 
    								name="user_id" 
   									value = "${ elem.userId }">
   									<fmt:message key="blacklist"/>
   								</button>
    						</form>
    					</c:when>
    					<c:otherwise>
    						<fmt:message key="blacklisted"/>
    					</c:otherwise>
    				</c:choose>
				</div>
			</div>
			<hr/>
		</c:forEach>
		
		<div class="row" align="center">
			<div class="col">
			<form name="Page" method="GET" action="Controller">
				<input type="hidden" name="command" value="customer_orders"/>
				
				<ctg:page-button 
			 	currentPage="${ page_number }" 
			 	lastPage="${ last_page }" 
			 	offset="2"
			 	buttonClass="btn btn-outline-primary"/>
			</form>
			</div>
		</div>
	</div>
	<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>