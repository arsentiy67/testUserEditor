<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit</title>
<script type="text/javascript">
	var addressCount;
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
	function getAddressCount() {
		var countInput = document.getElementById("addrCount");
		var count = 0;
		if (countInput != null) {
			count = countInput.value;
		}
		addressCount = count;
		if (addressCount == 0) {
			addAddressLine();
		}
	}
	
	function delAddressLine(lineNumber) {
		if (addressCount == 1) {
			var country = document.getElementById("inputCountry0");
			country.value = "";
			var city = document.getElementById("inputCity0");
			city.value = "";
		} else {
			document.getElementById("addressLine" + lineNumber).remove();
			for (var i = lineNumber+1; i < addressCount; i++) {
				var addressDiv = document.getElementById("addressLine" + i);
				addressDiv.id = "addressLine" + (i - 1);
				var country = document.getElementById("inputCountry" + i);
				country.id = "inputCountry" + (i - 1);
				country.name = "userAddress[" + (i - 1) + "].country";
				var city = document.getElementById("inputCity" + i);
				city.id = "inputCity" + (i - 1);
				city.name = "userAddress[" + (i - 1) + "].city";
				var clear = document.getElementById("btnClear" + i);
				clear.id = "btnClear" + (i - 1);
				clear.setAttribute("onclick", "delAddressLine(" + (i - 1) + ");");
				var err = document.getElementById("errorAddress" + i);
				err.id = "errorAddress" + (i - 1);
			}
			addressCount--;
		}
	}
	
	var addressHtml = '<label>Country: </label>' +
		'<input type="text" id="inputCountryNum" name="userAddress[Num].Country">\r\n' +
		'<label>City: </label>' +
		'<input type="text" id="inputCityNum" name="userAddress[Num].City">\r\n' +
		'<input type="button" onclick="addAddressLine();" value="+">\r\n' +
		'<input type="button" id="btnClearNum" onclick="delAddressLine(Num);" value="-">\r\n' +
		'<label id="errorAddressNum" style="color: red;"></label>';
	
	function addAddressLine() {
		var newDiv = document.createElement('div');
		newDiv.id = "addressLine" + addressCount;

		newDiv.innerHTML = addressHtml.split('Num').join(addressCount);
		
		addrDiv = document.getElementById("addressDiv");
		addrDiv.appendChild(newDiv);
		
		addressCount++;
	}
	
	function validateAddressLine(lineNumber) {
		var country = document.getElementById("inputCountry" + lineNumber).value;
		var city = document.getElementById("inputCity" + lineNumber).value;
		var errElem = document.getElementById("errorAddress" + lineNumber);
		if (country == null || country == "" || city == null || city == "") {
			errElem.innerHTML = "Both fields country and city must be filled";
			return false;
		} else {
			errElem.innerHTML = "";
			return true;
		}
	}
	
	function validateAddress() {
		var result = true;
		for (var i = 0; i < addressCount; i++) {
			if (!validateAddressLine(i)) {
				result = false;
			}
		}
		return result;
	}
	
	function validate() {
		return validateAddress();		
	}
</script>
</head>
<body onload="getAddressCount()">
	<c:url value="/saveuser" var="saveuserUrl"/>
	<c:url value="/userlist" var="userlistUrl"/>
	<c:url value="/login" var="loginUrl" />
	<c:url value="/logout" var="logoutUrl" />
	<input type="hidden" id="addrCount" value="${fn:length(user.userAddress)}">
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
 
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
	<form:form action="${saveuserUrl}" method="post" onsubmit="return validate();" modelAttribute="user">
		<table>
			<tr>
				<td>Email:</td>
				<td><input type='text' name='email' value="${user.email}"></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td><input type='text' name='name' value="${user.name}"></td>
			</tr>
			<tr>
				<td>Time zone:</td>
				<td><input type='text' name='timezone' value="${user.timezone}"></td>
			</tr>
			<tr>
				<c:if test="${user.userId != null}">
					<td>Password:</td>
					<td><input type='password' name='password' pattern="(?=.*\d)(?=.*[A-Z]).{6,}" 
						title="Must contain at least one number and one uppercase letter, and at least 6 or more characters"/></td>
				</c:if>
				<c:if test="${user.userId == null}">
					<td>Password:</td>
					<td><input type='password' name='password' pattern="(?=.*\d)(?=.*[A-Z]).{6,}" 
						title="Must contain at least one number and one uppercase letter, and at least 6 or more characters" required="required"/></td>
				</c:if>				
			</tr>
			<tr>
				<td>Role:</td>
				<td>
					<c:if test="${isEditor == true}">
						<select name='roleStr'>
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
	  	</table>
	  	
		<div id="addressDiv">
			<label>Address: </label>
			<c:forEach items="${user.userAddress}" var="address" varStatus="status">
				<div id="addressLine${status.index}">
 					<label>Country: </label>
					<input type='text' id="inputCountry${status.index}" value="${address.country}" name="userAddress[${status.index}].country"/>
					<label>City: </label>
					<input type='text' id="inputCity${status.index}" value="${address.city}" name="userAddress[${status.index}].city"/>
					
					<input type="button" onclick="addAddressLine();" value="+">
					<input type="button" id="btnClear${status.index}" onclick="delAddressLine(${status.index});" value="-">
					
					<label id="errorAddress${status.index}" style="color: red;"></label> 
				</div>
			</c:forEach>
		</div>

		<input name="submit" type="submit" value="submit"/>
  	
	  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	  	<input type="hidden" name="userId" value="${user.userId}" />
	  </form:form>
</body>
</html>