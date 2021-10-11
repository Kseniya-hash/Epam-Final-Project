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
	<fmt:bundle basename="resources.pagecontent" prefix="cart.">
		<fmt:message key="cart"/>
	</fmt:bundle>
</title>
</head>
<body>
	<fmt:bundle basename="resources.pagecontent" prefix="cart.">
	<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
	<div class="container">
		<c:forEach var="elem" items="${ products }">
			<div class="row">
				<div class="col-md-9">
					<c:url value ="Controller?command=show_product&product_id=${ elem.id }" var="productUrl"/>
					<a href ='<c:out value="${ productUrl }"/>'><c:out value="${ elem.name }"/></a><br/>
					<br/>
					<c:out value="${ elem.productCategory }"/><br/>
						<fmt:message key="rating"/>
					<c:out value="${ elem.rating }"/><br/>
						<fmt:message key="price"/>
					<c:out value="${ elem.sellingPrice / 100 }"/><br/>
					<hr/>
				</div>
				<div class="col-md-3">
					<form name="ProductSort" method="POST" action="Controller">
						<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
						<div class="row" align="center">
  							<input type="hidden" name="product_id" value="${ elem.id }"/>
  							<div class="col-md-12">
  								<fmt:message key="incart"/><br/>
  							</div>
  						</div>
  						<div class="row" align="center">
  							<div class="col-md-4">
    							<button type="submit" 
    								class="btn btn-outline-info" 
    								name="command" 
    								value="cart_decriment">-</button>
    						</div>
    						<div class="col-md-4">
    							<c:out value="${ elem.quantity }"/>
    						</div>
    						<div class="col-md-4">
    							<button type="submit" 
    								class="btn btn-outline-info" 
    								name="command" 
    								value="cart_incriment">+</button>
    						</div>
    					</div>
    					<div class="row" align="center">
    						<div class="col-md-12">
    							<br/>
    							<button type="submit" 
    							class="btn btn-outline-danger btn-sm" 
    							name="command" 
    							value = "cart_remove"><fmt:message key="removefromcart"/></button>
    						</div>
    					</div>
    				</form>
				</div>
			</div>
		</c:forEach>
		<c:if test="${ not empty products }">
			<div class="row" align="center">
				<div class="col-md-12">
					<form name="MakeOrder" method="POST" action="Controller">
						<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
						<button type="submit" 
    					class="btn btn-outline-primary btn-block" 
    					name="command" 
    					value = "make_order"><fmt:message key="makeorder"/></button>
    				</form>
				</div>
			</div>
		</c:if>
	</div>
	<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
	</fmt:bundle>
</body>
</html>