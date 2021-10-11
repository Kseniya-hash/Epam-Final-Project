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
	<fmt:bundle basename="resources.pagecontent" prefix="list.">
		<fmt:message key="products"/>
	</fmt:bundle>
</title>
</head>
<body>
	<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<fmt:bundle basename="resources.pagecontent" prefix="list.">
	<div class="container">
		<div class="dropdown">
			<button class="btn btn-secondary dropdown-toggle" type="button" 
			id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    			<fmt:message key="sortby"/>
  			</button>
			<div class="dropdown-menu">
  				<form name="ProductSort" method="GET" action="Controller">
  				<input type="hidden" name="command" value="product_sort"/>
    				<button class="dropdown-item" type="submit" name="sort" value="rating">
    					<fmt:message key="byrating"/>
    				</button>
	    			<button class="dropdown-item" type="submit" name="sort" value="comment_count">
	    				<fmt:message key="bycommentcount"/>
	    			</button>
   					<button class="dropdown-item" type="submit" name="sort" value="price_inc">
   						<fmt:message key="bypriceincreasing"/>
   					</button>
 	  				<button class="dropdown-item" type="submit" name="sort" value="price_desc">
 	  					<fmt:message key="bypricedecreasing"/>
 	  				</button>
    			</form>
			</div>
		</div>
		
		<c:forEach var="elem" items="${ products }">
			<div class="row">
				<div class="col-md-10">
				
					<c:url value ="Controller?command=show_product&product_id=${ elem.id }" var="productUrl"/>
					<a href ='<c:out value="${ productUrl }"/>'><c:out value="${ elem.name }"/></a><br/>
					<c:out value="${ elem.productCategory }"/><br/>
					<c:out value="${ elem.description }"/><br/>
						<fmt:message key="comments"/>
					<c:out value="${ elem.commentCount }"/><br/>
						<fmt:message key="rating"/>
					<c:out value="${ elem.rating }"/><br/>
						<fmt:message key="price"/>
					<c:out value="${ elem.sellingPrice / 100 }"/><br/>
					<hr/>
				</div>
				<div class="col-md-2">
    				</br>
    				<form name="addToCart" method="POST" action="Controller">
    					<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
  						<input type="hidden" name="command" value="cart_incriment"/>
    					<button type="submit" class="btn btn-primary" name="product_id" value = ${ elem.id }>
    						<fmt:message key="addtocart"/>
    					</button>
    				</form>
				</div>
			</div>
		</c:forEach>
	</div>
	<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>