<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>header</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<script src="js/jquery-3.2.1.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<fmt:setLocale value="${ pageContext.response.locale }" scope="session"/>
<fmt:bundle basename="resources.pagecontent" prefix="header.">

<nav class="navbar navbar-expand-md">
	<div class="container-fluid">
		<div class="navbar-header">
			SHOP
		</div>
		<div>
			<ul class="navbar-nav">
				<li class="nav-item">
					<a class="nav-link" href="index.jsp"><fmt:message key="mainpage"/></a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="Controller?command=show_cart">
						<fmt:message key="cart"/>
					</a>
				</li>
				<c:if test="${ not empty user }">
					<li class="nav-item">
						<a class="nav-link" href="Controller?command=user_orders">
							<fmt:message key="myorders"/>
						</a>
					</li>
				</c:if>
				<c:if test="${ user.role == 'admin' }">
					<li class="nav-item">
						<a class="nav-link" href="Controller?command=customer_orders">
							<fmt:message key="customerorders"/>
						</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="Controller?command=to_create_product">
							<fmt:message key="addproduct"/>
						</a>
					</li>
				</c:if>
				<li class="nav-item">
					<div class="dropdown">
			  <button class="btn btn-link dropdown-toggle" type="button" 
			id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    			<fmt:message key="language"/>
  			</button>
			<div class="dropdown-menu">
  				<form name="ProductSort" method="GET" action="Controller">
  				<input type="hidden" name="command" value="change_locale"/>
    				<button class="dropdown-item" type="submit" name="locale" value="ru-RU">
    					<fmt:message key="russian"/>
    				</button>
	    			<button class="dropdown-item" type="submit" name="locale" value="en-US">
	    				<fmt:message key="english"/>
	    			</button>
   				</form>
			</div>
		</div>
				</li>
				<c:choose>
				<c:when test="${ empty user }">
					<li class="nav-item">
						<a class="nav-link" href="Controller?command=to_login_page">
							<fmt:message key="login"/>
						</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="Controller?command=to_registration_page">
							<fmt:message key="register"/>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="nav-item">
						<a class="nav-link" href="Controller?command=logout">
							<fmt:message key="logout"/>
						</a>
					</li>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>

<c:if test="${ not empty modal_message }">
  <div class="modal fade" 
  id="modal" tabindex="-1" 
  role="dialog" 
  aria-labelledby="modalLabel" 
  aria-hidden="true">
  	<div class="modal-dialog" role="document">
  	  <div class="modal-content">
        <div class="modal-body">
          <c:out value="${ modal_message }"/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">
          	<fmt:message key="close"/>
          </button>
        </div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
	$('#modal').modal('show');
  </script>
</c:if>


</fmt:bundle>
<hr>
</body>
</html>