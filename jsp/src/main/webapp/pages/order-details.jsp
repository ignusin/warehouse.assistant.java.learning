<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="orderDetailsBean" class="iglabs.assistant.jsp.beans.OrderDetailsBean">
	<jsp:setProperty name="orderDetailsBean" property="orderId" value="${param.id}" /> 
</jsp:useBean>

<c:set var="order" value="${orderDetailsBean.order}" />
<c:set var="orderItems" value="${orderDetailsBean.orderItems}" />

<h3>Order details form</h3>

<form>
	<div class="form-group">
		<label class="control-label">Order name:</label>
		<p class="form-control-static">${order.name}</p>
	</div>
	<div class="form-group">
		<label class="control-label">Order items:</label>
		<table class="table table-striped">
			<thead>
				<tr>
					<th class="col-md-2">Item No</th>
					<th class="col-md-2">Name</th>
					<th class="col-md-2">Unit</th>
					<th class="col-md-2">Price</th>
					<th class="col-md-2">Quantity</th>
					<th class="col-md-2">Item cost</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderItems}" var="orderItem">
				<tr>
					<td>${orderItem.itemNo}</td>
					<td>${orderItem.name}</td>
					<td>${orderItem.unit}</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${orderItem.price}" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${orderItem.quantity}" /></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${orderItem.price * orderItem.quantity}" /></td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th colspan="4"></th>
					<th>Total sum:</th>
					<th><fmt:formatNumber type="number" maxFractionDigits="2" value="${orderDetailsBean.getTotalPrice(orderItems)}" /></th>
				</tr>
			</tfoot>
		</table>
	</div>
	
	<div class="form-group">
		<a href="<c:url value="/pages/orders.jsp" />" class="btn btn-default">Return to items</a>
	</div>
</form>
