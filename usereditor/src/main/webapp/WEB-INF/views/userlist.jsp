<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<c:url value="/login" var="loginUrl" />
	<c:url value="/logout" var="logoutUrl" />
	<c:url value="/edituser" var="addUserUrl" />
	<c:url value="/edituser?id=" var="editUserUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	<center>
		<h2>
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				Welcome : ${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()"> Logout</a>
			</c:if>
		</h2>
		<sec:authorize access="hasRole('ROLE_EDITOR')">
			<h2>
				<a href="${addUserUrl}">Add new user</a>
			</h2>
		</sec:authorize>
	</center>
	<center>
		<table border="1" width="800">
			<col width="25%">
			<col width="25%">
			<col width="25%">
			<col width="25%">
			<thead>
				<tr>
					<td align="center">Email</td>
					<td align="center">Name</td>
					<td align="center">Role</td>
					<td align="center">Edit link</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allUsers}" var="user">
					<tr>
						<td align="center">${user.email}</td>
						<td align="center">${user.name}</td>
						<td align="center">${user.roleStr}</td>
						<td align="center">
							<sec:authorize access="hasRole('ROLE_EDITOR')">
								<a href="${editUserUrl}${user.userId}">Edit</a>
							</sec:authorize>
							<c:if test="${user.roleStr == 'USER' && editorId == user.userId}">
								<a href="${editUserUrl}${user.userId}">Edit</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</center>
</body>
</html>