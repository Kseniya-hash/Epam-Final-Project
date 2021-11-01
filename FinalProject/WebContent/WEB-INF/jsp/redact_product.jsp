<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!--  <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><c:out value="${ product.name }"/></title>
</head>
<body>
<fmt:setLocale value="${ pageContext.response.locale }" scope="session"/>
<fmt:bundle basename="resources.pagecontent" prefix="product.">
<fmt:requestEncoding value="UTF-8"/>
<c:import url="fragment\header.jsp" charEncoding="utf-8"/>

<div class="modal fade" 
		id="add_category" 
		tabindex="-1" role="dialog" 
		aria-labelledby="exampleModalLabel" 
		aria-hidden="true">
  	<div class="modal-dialog" role="document">
    	<div class="modal-content">
    		<form name="AddCategory" method="POST" action="Controller">
      			<div class="modal-header">
        			<h3 class="modal-title" id="exampleModalLabel">
        				<fmt:message key="addcategory"/>
        			</h3>
      			</div>
      			<div class="modal-body">
					<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
					<input type="hidden" name="command" value="add_category"/>
					<input type="text" class="form-control" name="product_category">
    			</div>
    			<div class="modal-footer">
        			<button type="button" class="btn btn-secondary" data-dismiss="modal">
        				<fmt:message key="cancel"/>
        			</button>
        			<button type="submit" class="btn btn-primary">
        				<fmt:message key="add"/>
        			</button>
      			</div>
      		</form>
    	</div>
  	</div>
</div>

<div class="container">
	<form name="ShowProductForm" method="POST" action="Controller" enctype="multipart/form-data">
		<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
		<input type="hidden" name="command" value="${ command }"/>
		<input type="hidden" name="product_id" value="${ product.id }"/>
		<div class="form-group">
			<input type="text" 
				class="form-control" 
				id="product_name" 
				name="product_name" 
				required 
				value="${ product.name }"/><br/>
				
			<fmt:message key="productcategory"/>
			
			<select class="form-control" name="product_category" required>
				<c:forEach var="category" items="${ categories }" >
					<c:choose>
						<c:when test="${ category.id eq product.categoryId }">
							<option selected value="${ category.id }">
								<c:out value="${ category.name }"/>
							</option>
						</c:when>
						<c:otherwise>
							<option value="${ category.id }">
								<c:out value="${ category.name }"/>
							</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			
			<div class="row" align="right">
				<div class="col-md-12">
					<button type="button" 
						class="btn btn-secondary btm-sm" 
						data-toggle="modal" 
						data-target="#add_category">
  						<fmt:message key="add"/>
					</button>
			 	</div>
			</div>
			</br>
			<div class="row">
				<div class="col-md-6">
					<fmt:message key="sellingprice"/>
				</div>
				<div class="col-md-6">
					<input type="number" 
						class="form-control" 
						id="selling_price" 
						name="selling_price" 
						required 
						min="0.01" 
						step="0.01" 
						value="${ price.sellingPrice / 100 }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<fmt:message key="purchaseprice"/> 
				</div>
				<div class="col-md-6">
					<input type="number" 
						class="form-control"  
						id="purchase_price" 
						name="purchase_price" 
						required 
						min="0.01" 
						step="0.01" 
						value="${ price.purchasePrice / 100 }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<fmt:message key="quantity"/>
				</div>
				<div class="col-md-6">
					<input type="number" 
						class="form-control" 
						id="product_quantity" 
						name="product_quantity" 
						required 
						min="0" 
						step="1" 
						value="${ product.quantity }"/><br/>
				</div>
			</div>
			
			<div class = "row">
				<div class="col-md-6">
					<fmt:message key="chooseanotherpicture"/>
				</div>
				<div class="col-md-6">
					<input type="file" 
					id="new_photo"
					class="form-control-file" 
					name="product_photo" 
					accept=".jpg,.png,.jpeg,.gif">
				</div>
			</div>
			
			<fmt:message key="description"/>
			<textarea class="form-control" 
				id="product_description" 
				name="product_description" 
				maxlength="1000" 
				rows="10"><c:out value="${ product.description }"/></textarea><br/>
			<div class="row">
				<div class="col-md-4">
					<fmt:message key="weight"/>
				</div>
				<div class="col-md-8">
					<input type="number" 
						class="form-control" 
						id="product_weight" 
						name="product_weight" 
						min="1" 
						step="1" 
						value="${ product.weight }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<fmt:message key="length"/>
				</div>
				<div class="col-md-8">
					<input type="number" 
						class="form-control" 
						id="product_length" 
						name="product_length" 
						min="1" 
						step="1" 
						value="${ product.length }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<fmt:message key="high"/>
				</div>
				<div class="col-md-8">
					<input type="number" 
						class="form-control" 
						id="product_high" 
						name="product_high" 
						min="1" 
						step="1" 
						value="${ product.high }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<fmt:message key="width"/>
				</div>
				<div class="col-md-8">
					<input type="number" 
						class="form-control" 
						id="product_width" 
						name="product_width" 
						min="1" 
						step="1" 
						value="${ product.width }"/><br/>
				</div>
			</div>
			<button type="submit" class="btn btn-primary"><fmt:message key="save"/></button>
		</div>
	</form>
</div>
<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</fmt:bundle>
</body>
</html>