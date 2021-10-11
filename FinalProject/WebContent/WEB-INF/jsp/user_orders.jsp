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
	<!-- <fmt:bundle basename="resources.pagecontent" prefix="list.">
		<fmt:message key="products"/>
	</fmt:bundle> -->
	Orders
</title>
</head>
<body>
	<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<fmt:bundle basename="resources.pagecontent" prefix="list.">
	<div class="container">
		
		<c:forEach var="elem" items="${ orders }">
			<div class="row">
				<div class="col-md-10">
				
					Номер заказа:
					<c:out value="${ elem.id }"/><br/>
					Дата заказа:
					<c:out value="${ elem.date }"/><br/>
					Статус:
					<c:out value="${ elem.orderStatus }"/><br/><br/>
					<c:forEach var="sale" items="${ elem.sales }">
						<c:url value ="Controller?command=show_product&product_id=${ sale.productId }" var="productUrl"/>
						<a href ='<c:out value="${ productUrl }"/>'><c:out value="${ sale.productName }"/></a><br/>
						<div class="row">
							<div class="col-md-5">
								Заказано: <c:out value="${ sale.quantity }"/>
							</div>
							<div class="col-md-5">
								Цена:
								<fmt:formatNumber value="${ sale.price/100 }" type="currency"/>
							</div>
							</br>
							</br>
						</div>
					</c:forEach>
					<hr/>
				</div>
				<div class="col-md-2">
					<c:if test="${ elem.orderStatus eq оформлен }">
						<form name="pay_fo_order" method="POST" action="Controller">
  							<input type="hidden" name="command" value="pay_fo_order"/>
    						<button type="submit" 
    							class="btn btn-primary" 
    							name="order_id" 
   								value = ${ elem.id }><fmt:message key="pay"/></button>
    						</form>
					</c:if>
				</div>
			</div>
		</c:forEach>
	</div>
	<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>