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
<!-- float-right -->
<c:import url="fragment\header.jsp" charEncoding="utf-8"/>
<div class="container">
	<form name="ShowProductForm" method="POST" action="Controller">
		<input type="hidden" name="csrf_tokin" value="${ csrf_tokin }">
		<input type="hidden" name="command" value="redact_product"/>
		<input type="hidden" name="product_id" value="${ product.id }"/>
		<div class="form-group">
			<input type="text" 
				class="form-control" 
				id="product_name" 
				name="product_name" 
				required 
				value="${ product.name }"/><br/>
			<div class="row">
				<div class="col-md-6">
					Цена продажи:
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
					Цена закупки:
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
					Количество на складе:
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
			Описание:
			<textarea class="form-control" 
				id="product_description" 
				name="product_description" 
				maxlength="1000" 
				rows="10">
${ product.description }
			</textarea><br/>
			<div class="row">
				<div class="col-md-2">
					Вес, г:
				</div>
				<div class="col-md-10">
					<input type="number" 
						class="form-control" 
						id="product_weight" 
						name="product_weight" 
						min="0" 
						step="1" 
						value="${ product.weight }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					Длина, см:
				</div>
				<div class="col-md-10">
					<input type="number" 
						class="form-control" 
						id="product_length" 
						name="product_length" 
						min="0" 
						step="1" 
						value="${ product.length }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					Высота, см:
				</div>
				<div class="col-md-10">
					<input type="number" 
						class="form-control" 
						id="product_high" 
						name="product_high" 
						min="0" 
						step="1" 
						value="${ product.high }"/><br/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
					Ширина, см:
				</div>
				<div class="col-md-10">
					<input type="number" 
						class="form-control" 
						id="product_width" 
						name="product_width" 
						min="0" 
						step="1" 
						value="${ product.width }"/><br/>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Сохранить</button>
		</div>
	</form>
</div>
<c:import url="fragment\footer.jsp" charEncoding="utf-8"/>
</body>
</html>