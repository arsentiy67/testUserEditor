<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit</title>
</head>
<body>
	<c:url value="/userlist" var="userlistUrl"/>
	<c:url value="/login" var="loginUrl" />
	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
 
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<center>
			<h2>
				Edit user : ${pageContext.request.userPrincipal.name} | <a
					href="${userlistUrl}">All users</a> | <a
					href="javascript:formSubmit()"> Logout</a>
			</h2>
		</center>
	</c:if>
	<table>
		<tr>
			<td>Email:</td>
			<td><input type='text' name='user.email' value="${user.email}"></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type='password' name='user.password' pattern="(?=.*\d)(?=.*[A-Z]).{6,}" 
				title="Must contain at least one number and one uppercase letter, and at least 6 or more characters"/></td>
		</tr>
		<tr>
			<td>Role:</td>
			<td>
				<select name='user.roleStr'>
					<option selected="selected" style="display: none;">${user.roleStr}</option>
					<option>USER</option>
					<option>EDITOR</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Create date:</td>
			<td><input type='text' disabled="disabled" readonly="readonly" value="${user.createDateStr}"></td>
		</tr>
		<tr>
			<td>Last update date:</td>
			<td><input type='text' disabled="disabled" readonly="readonly" value="${user.updateDateStr}"></td>
		</tr>

		<tr>
			<td colspan='2'><input name="submit" type="submit" value="submit" /></td>
		</tr>
  	</table>
</body>
</html>