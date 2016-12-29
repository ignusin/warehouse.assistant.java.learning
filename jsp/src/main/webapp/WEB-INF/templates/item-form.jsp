<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
<c:when test="${empty param.id}">
<h3>Add new item form</h3>
</c:when>
<c:otherwise>
<h3>Edit item form</h3>
</c:otherwise>
</c:choose>

<form method="POST" action="<c:url value="/servlets/item-form"/>">
	<input type="hidden" name="id" value="${ param.id }" />

	<div class="form-group ${ validation.hasErrors('itemNo') ? 'has-error' : '' }">
		<label>Item No:</label>
		<input type="text" name="itemNo" class="form-control" value="<c:out value="${ param.itemNo }" default="${ item.itemNo }" />" />
		<c:forEach var="error" items="${validation.getErrors('itemNo')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group ${ validation.hasErrors('name') ? 'has-error' : '' }">
		<label>Name:</label>
		<input type="text" name="name" class="form-control" value="<c:out value="${ param.name }" default="${ item.name }" />" />
		<c:forEach var="error" items="${validation.getErrors('name')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group ${ validation.hasErrors('description') ? 'has-error' : '' }">
		<label>Description:</label>
		<input type="text" name="description" class="form-control" value="<c:out value="${ param.description }" default="${ item.description }" />" />
		<c:forEach var="error" items="${validation.getErrors('description')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group ${ validation.hasErrors('unit') ? 'has-error' : '' }">
		<label>Unit:</label>
		<input type="text" name="unit" class="form-control" value="<c:out value="${ param.unit }" default="${ item.unit }" />" />
		<c:forEach var="error" items="${validation.getErrors('unit')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group ${ validation.hasErrors('price') ? 'has-error' : '' }">
		<label>Price:</label>
		<input type="text" name="price" class="form-control" value="<c:out value="${ param.price }" default="${ item.price }" />" />
		<c:forEach var="error" items="${validation.getErrors('price')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group ${ validation.hasErrors('inStock') ? 'has-error' : '' }">
		<label>In stock:</label>
		<input type="text" name="inStock" class="form-control" value="<c:out value="${ param.inStock }" default="${ item.inStock }" />" />
		<c:forEach var="error" items="${validation.getErrors('inStock')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group">
		<a href="<c:url value="/pages/items.jsp" />" class="btn btn-default">Return to items</a>
		<span class="inline-space-15"></span>
		<button type="submit" class="btn btn-primary">Save changes</button>
		<button type="reset" class="btn btn-success">Reset form</button>
	</div>
</form>
