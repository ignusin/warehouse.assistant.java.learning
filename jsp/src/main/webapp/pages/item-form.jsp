<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="<c:url value="/pages/items.jsp" />" class="btn btn-primary">Return to items</a>

<c:choose>
<c:when test="${empty param.id}">
<h3>Add new item form</h3>
</c:when>
<c:otherwise>
<h3>Edit item form</h3>
</c:otherwise>
</c:choose>

<c:if test="${not empty param.id && not validation }">
	<jsp:useBean id="itemFormBean" class="iglabs.assistant.jsp.beans.ItemFormBean" scope="page">
		<jsp:setProperty name="itemFormBean" property="id" value="${param.id}" />
	</jsp:useBean>
	<c:set var="item" value="${itemFormBean.item}" scope="page" />
</c:if>

<jsp:useBean id="validation" class="iglabs.assistant.jsp.validation.ModelValidation" scope="request" />

<form method="POST" action="<c:url value="/servlets/items-form"/>">
	<input type="hidden" name="id" value="${ item.id }" />

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
	
	<div class="form-group ${ validation.hasErrors('inStock') ? 'has-error' : '' }">
		<label>In stock:</label>
		<input type="text" name="inStock" class="form-control" value="<c:out value="${ param.inStock }" default="${ item.inStock }" />" />
		<c:forEach var="error" items="${validation.getErrors('inStock')}">
		<div class="text-danger">${error}</div>
		</c:forEach>
	</div>
	
	<div class="form-group">
		<button type="submit" class="btn btn-primary">Save changes</button>
		<button type="reset" class="btn btn-default">Reset form</button>
	</div>
</form>