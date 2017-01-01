<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>Add new order form</h3>

<jsp:useBean id="itemsListBean" class="iglabs.assistant.jsp.beans.ItemsListBean" scope="page" />

<form method="POST" action="<c:url value="/servlets/order-form"/>">
	<div class="form-group ${ not empty orderValidation && orderValidation.hasErrors('name') ? 'has-error' : '' }">
		<label>Name:</label>
		<input type="text" name="name" class="form-control" value="<c:out value="${ orderData.name }" />" />
		<c:if test="${ not empty orderValidation }">
		<c:forEach var="error" items="${ orderValidation.getErrors('name') }">
		<div class="text-danger">${error}</div>
		</c:forEach>
		</c:if>
	</div>
	
	<div class="form-group">
		<button type="submit" class="btn btn-primary" name="addItem">Add order item</button>
	</div>

	<c:forEach items="${orderItems}" var="orderItem" varStatus="orderItemsStatus">
	<div class="padding10">
		<label>Item #${orderItemsStatus.index + 1}</label>
		<c:if test="${orderItems.size() > 1}">
		<div class="form-group">
			<button type="submit" class="btn btn-danger" name="removeItem" value="${orderItemsStatus.index}">Remove item</button>
		</div>
		</c:if>
		<div class="form-group ${ not empty orderItemValidations && orderItemValidations.get(orderItemsStatus.index).hasErrors('itemId') ? 'has-error' : '' }">
			<label>Item:</label>
			<select name="item_${orderItemsStatus.index}" class="form-control">
				<option value="">Not Selected</option>
			<c:forEach items="${itemsListBean.items}" var="item">
				<c:choose>
				<c:when test="${item.id.toString() == orderItem.itemId}">
				<option value="${item.id}" selected>${item.name}</option>
				</c:when>
				<c:otherwise>
				<option value="${item.id}">${item.name}</option>
				</c:otherwise>
				</c:choose>
			</c:forEach>
			</select>
			<c:if test="${ not empty orderItemValidations }">
			<c:forEach var="error" items="${ orderItemValidations.get(orderItemsStatus.index).getErrors('itemId') }">
			<div class="text-danger">${error}</div>
			</c:forEach>
			</c:if>
		</div>
		<div class="form-group ${ not empty orderItemValidations && orderItemValidations.get(orderItemsStatus.index).hasErrors('quantity') ? 'has-error' : '' }">
			<label>Quantity:</label>
			<input type="text" class="form-control" name="quantity_${orderItemsStatus.index}" value="${orderItem.quantity}" />
			<c:if test="${ not empty orderItemValidations }">
			<c:forEach var="error" items="${ orderItemValidations.get(orderItemsStatus.index).getErrors('quantity') }">
			<div class="text-danger">${error}</div>
			</c:forEach>
			</c:if>
		</div>
	</div>
	</c:forEach>

	<div class="form-group">
		<a href="<c:url value="/pages/orders.jsp" />" class="btn btn-default">Return to items</a>
		<span class="inline-space-15"></span>
		<button type="submit" class="btn btn-primary">Save changes</button>
		<button type="reset" class="btn btn-success">Reset form</button>
	</div>
</form>
