<%@ page pageEncoding="UTF-8"%>

<h3>Remove item</h3>

<form action="<c:url value="/servlets/item-remove" />" method="POST">
	<input type="hidden" name="id" value="${param.id}" />
	<p>Are you sure you want to remove current item ?</p>
	
	<a href="<c:url value="/pages/items.jsp" />" class="btn btn-default">Return to items</a>
	<span class="inline-space-15"></span>
	<button type="submit" class="btn btn-danger">Yes, remove</button>
</form>
