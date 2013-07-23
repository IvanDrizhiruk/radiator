<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List customers</title>
</head>
<body>
<div>List of Customers<br>
<br>
</div>
<table border="1">
	<tr>
		<th>Name</th>
		<th>Email</th>
		<th>Phone</th>
	</tr>
	<c:if test="${customers!=null}">
	<c:forEach items="${customers}" var="customer">
		<tr>
			<td>${customer.firstName} ${customer.lastName }</td>
			<td>${customer.emailAddress}</td>
			<td>${customer.phoneNumber}</td>
		</tr>
	</c:forEach>
	</c:if>
</table>
<div><br>
<a href='customer/create'>Create Customer</a></div>
</body>
</html>