<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
<fmt:setLocale value="${ pageContext.response.locale }" scope="session"/>
<title>
	<fmt:bundle basename="resources.pagecontent" prefix="header.">
		<fmt:message key="myorders"/>
	</fmt:bundle>
</title>
</head>
<body>
	<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
	<fmt:bundle basename="resources.pagecontent" prefix="order.">
	<div class="container">
		
		<c:forEach var="elem" items="${ orders }">
			<div class="row">
				<div class="col-md-10">
				
					<fmt:message key="ordernumber"/>
					<c:out value="${ elem.id }"/><br/>
					<fmt:message key="orderdate"/>
					<fmt:parseDate value="${ elem.date }" 
							pattern="yyyy-MM-dd'T'HH:mm" 
							var="parsedDate" type="both"/>
					<fmt:formatDate pattern="dd.MM.yyyy HH:mm" 
							value="${ parsedDate }"/><br/>
					<p class="text-danger font-weight-bold">
						<fmt:message key="status"/>
						<c:out value="${ elem.orderStatus }"/>
					</p><br/>
					<c:forEach var="sale" items="${ elem.sales }">
						<c:url value ="Controller?command=show_product&product_id=${ sale.productId }" 
								var="productUrl"/>
						<a href ='<c:out value="${ productUrl }"/>'>
							<c:out value="${ sale.productName }"/>
						</a><br/>
						<div class="row">
							<div class="col-md-5">
								<fmt:message key="ordered"/> 
								<c:out value="${ sale.quantity }"/>
							</div>
							<div class="col-md-5">
								<fmt:message key="price"/>
								<fmt:formatNumber value="${ sale.price/100 }" 
									type="currency" currencyCode ="BYR"/>
							</div>
							</br>
							</br>
						</div>
					</c:forEach>
				</div>
				<div class="col-md-2">
					<c:if test="${ elem.orderStatus eq '????????????????' }">
						<form name="pay_fo_order" method="GET" action="Controller">
  							<input type="hidden" name="command" value="to_pay_fo_order"/>
    						<button type="submit" 
    							class="btn btn-primary" 
    							name="order_id" 
   								value = ${ elem.id }><fmt:message key="pay"/></button>
    						</form>
					</c:if>
				</div>
			</div>
			<hr/>
		</c:forEach>
	</div>
	<c:import url="fragment\footer.jsp" charEncoding="utf-8"/> 
</fmt:bundle>
</body>
</html>