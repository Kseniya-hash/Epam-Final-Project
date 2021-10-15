<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<fmt:setLocale value="${ pageContext.response.locale }" scope="session"/>
<title>
	<fmt:bundle basename="resources.pagecontent" prefix="order.">
		<fmt:message key="pay"/>
	</fmt:bundle>
</title>
</head>
<body>
	<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
	<fmt:bundle basename="resources.pagecontent" prefix="order.">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<fmt:message key="ordernumber"/>
				<c:out value="${ order.id }"/><br/>
			</div>
			<div class="col-md-6">
				<fmt:message key="orderprice"/>
				<c:out value="${ price/100 }"/><br/>
			</div>
		</div>
		<div class="row">
			<fmt:message key="orderdate"/>
			<fmt:formatDate value="${ elem.date }" type="both" timeStyle="short"/><br/>
			<fmt:message key="status"/>
			<c:out value="${ order.orderStatus }"/><br/><br/>
		</div>
		<c:forEach var="sale" items="${ order.sales }">
		<div class="row">
			<c:url value ="Controller?command=show_product&product_id=${ sale.productId }" var="productUrl"/>
			<a href ='<c:out value="${ productUrl }"/>'><c:out value="${ sale.productName }"/></a><br/>
		</div>
		<div class="row">
			<div class="col-md-5">
				<fmt:message key="price"/><fmt:message key="ordered"/><c:out value="${ sale.quantity }"/>
			</div>
			<div class="col-md-5">
				<fmt:message key="price"/><fmt:message key="price"/>
				<fmt:formatNumber value="${ sale.price/100 }" type="currency"/>
			</div>
			</br>
			</br>
		</div>
		</c:forEach>
			
			<div class="row">
				<div class="col-md-12">
					<form name="pay_fo_order" method="POST" action="Controller">
  						<input type="hidden" name="command" value="pay_fo_order"/>
  						<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
  						<fmt:message key="cardnumber"/></br>
  						<input type="number" class="form-control" name="card">
    					<button type="submit" 
    						class="btn btn-primary" 
    						name="order_id" 
   							value = ${ order.id }><fmt:message key="pay"/></button>
    				</form>
				</div>
			</div>
	</div>
	<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>