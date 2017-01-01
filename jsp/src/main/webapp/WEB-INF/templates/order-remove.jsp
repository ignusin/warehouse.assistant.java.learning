<%@ page pageEncoding="UTF-8"%>

<h3>Remove order</h3>

<form action="<c:url value="/servlets/order-remove" />" method="POST">
	<input type="hidden" name="id" value="${param.id}" />
	<p>Are you sure you want to remove current order ?</p>
	
	<a href="<c:url value="/pages/orders.jsp" />" class="btn btn-default">Return to orders</a>
	<span class="inline-space-15"></span>
	<button type="submit" class="btn btn-danger">Yes, remove</button>
</form>
