<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="ordersListBean" class="iglabs.assistant.jsp.beans.OrdersListBean" scope="page" />
<c:set value="${ordersListBean.orders}" var="orders" scope="page" />

<div>
	<a href="<c:url value="/pages/order-form.jsp" />" class="btn btn-primary">Add new order</a>
</div>

<div class="space-15"></div>

<div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<td class="col-md-10">Name</td>
				<td class="col-md-2"></td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${orders.size() == 0}">
			<tr>
				<td colspan="2">
					No orders found.
				</td>
			</tr>
			</c:if>
			
			<c:if test="${orders.size() > 0}">
			<c:forEach var="order" items="${orders}">
			<tr>
				<td>${order.name}</td>
				<td class="col-buttons-2">
					<a href="<c:url value="/pages/order-details.jsp" />?id=${order.id}" class="btn btn-default btn-xs">View</a>
					<a href="<c:url value="/pages/order-remove.jsp" />?id=${order.id}" class="btn btn-danger btn-xs">Delete</a>
				</td>
			</tr>
			</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>