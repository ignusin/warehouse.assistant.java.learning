<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="itemsBean" class="iglabs.assistant.jsp.beans.ItemsBean" scope="page" />
<c:set value="${itemsBean.items}" var="items" scope="page" />

<div>
	<a href="<c:url value="/pages/items_form.jsp" />" class="btn btn-primary">Add new item</a>
</div>

<div class="space15"></div>

<div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<td class="col-md-1">Item No</td>
				<td class="col-md-3">Item Name</td>
				<td class="col-md-4">Description</td>
				<td class="col-md-1">Unit</td>
				<td class="col-md-1">In stock</td>
				<td class="col-md-2"></td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${items.size() == 0}">
			<tr>
				<td colspan="6">
					No items found.
				</td>
			</tr>
			</c:if>
		</tbody>
	</table>
</div>
