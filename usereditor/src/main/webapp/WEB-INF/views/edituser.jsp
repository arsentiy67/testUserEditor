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
	<c:url value="/saveuser" var="saveuserUrl"/>
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
			<h3>${error}</h3>
		</center>
	</c:if>
	<form action="${saveuserUrl}" method="post">
		<table>
			<tr>
				<td>Email:</td>
				<td><input type='text' name='user.email' value="${user.email}"></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td><input type='text' name='user.name' value="${user.name}"></td>
			</tr>
			<tr>
				<td>Time zone:</td>
				<td><input type='text' name='user.timezone' value="${user.timezone}"></td>
			</tr>
			<tr>
				<c:if test="${user.userId != null}">
					<td>Password:</td>
					<td><input type='password' name='user.password' pattern="(?=.*\d)(?=.*[A-Z]).{6,}" 
						title="Must contain at least one number and one uppercase letter, and at least 6 or more characters"/></td>
				</c:if>
				<c:if test="${user.userId == null}">
					<td>Password:</td>
					<td><input type='password' name='user.password' pattern="(?=.*\d)(?=.*[A-Z]).{6,}" 
						title="Must contain at least one number and one uppercase letter, and at least 6 or more characters" required="required"/></td>
				</c:if>				
			</tr>
			<tr>
				<td>Role:</td>
				<td>
					<c:if test="${isEditor == true}">
						<select name='user.roleStr'>
							<option selected="selected" style="display: none;">${user.roleStr}</option>
							<option>USER</option>
							<option>EDITOR</option>
						</select>
					</c:if>
					<c:if test="${isEditor == null || isEditor == false}">
						<input type="text" value="${user.roleStr}" disabled="disabled" readonly="readonly">
					</c:if>
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
	  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	  	<input type="hidden" name="user.userId" value="${user.userId}" />
	  </form>
</body>
</html>