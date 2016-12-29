<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="itemsListBean" class="iglabs.assistant.jsp.beans.ItemsListBean" scope="page" />
<c:set value="${itemsListBean.items}" var="items" scope="page" />

<div>
	<a href="<c:url value="/servlets/item-form" />" class="btn btn-primary">Add new item</a>
</div>

<div class="space-15"></div>

<div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<td class="col-md-1">Item No</td>
				<td class="col-md-3">Item Name</td>
				<td class="col-md-3">Description</td>
				<td class="col-md-1">Unit</td>
				<td class="col-md-1">Price</td>
				<td class="col-md-1">In stock</td>
				<td class="col-md-2"></td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${items.size() == 0}">
			<tr>
				<td colspan="7">
					No items found.
				</td>
			</tr>
			</c:if>
			
			<c:if test="${items.size() > 0}">
			<c:forEach var="item" items="${items}">
			<tr>
				<td>${item.itemNo}</td>
				<td>${item.name}</td>
				<td>${item.description}</td>
				<td>${item.unit}</td>
				<td>${item.price}</td>
				<td>${item.inStock}</td>
				<td class="col-buttons-2">
					<a href="<c:url value="/servlets/item-form" />?id=${item.id}" class="btn btn-default btn-xs">Edit</a>
					<a href="<c:url value="/servlets/item-remove" />?id=${item.id}" class="btn btn-danger btn-xs">Delete</a>
				</td>
			</tr>
			</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>
