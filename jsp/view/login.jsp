<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Login</title>
		
		<style type="text/css">
		.error {
			color: red;
		}

		</style>
	</head>

	<body>
		<c:if test="${!empty errorMessage }">
			<div class="error">${errorMessage }</div>
		</c:if>
		<form action="${pageContext.servletContext.contextPath}/login" method="post">
			<table>
			<tr>
			<td>Username</td>
			<td><input type="text" name="usernameBox" size="12" value="${username}" /></td>
			</tr>
			
			<tr>
			<td>Password</td>
			<td><input type="password" name="passwordBox" size="12" value="${password}" /></td>
			</tr>
			
			<tr>
			<td><input name="loginButton" type="submit" value="Login" /></td>
			<td><input name="createButton" type="submit" value="Create Account" /></td>
			</tr>
			
			<tr>
			<td class="error">${game.message}</td>
			</tr>
			</table>
	
		</form>
	</body>
</html>
