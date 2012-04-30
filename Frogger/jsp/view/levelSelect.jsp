<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Level Select</title>
	</head>
	
	<body>
		<form action="${pageContext.servletContext.contextPath}/levelSelect" method="post">
			<table>
				<tr>
					<td><input name="Level1Button" type="submit" value="Level 1"/></td>
					<td><c:if test="${maxLevel < 2 }">
							<input name="Level2Button" disabled="disabled" type="submit" value="Level 2"/>
						</c:if>
						<c:if test="${maxLevel >= 2 }">
							<input name="Level2Button" type="submit" value="Level 2"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 3 }">
							<input name="Level3Button" disabled="disabled" type="submit" value="Level 3"/>
						</c:if>
						<c:if test="${maxLevel >= 3 }">
							<input name="Level3Button" type="submit" value="Level 3"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 4 }">
							<input name="Level4Button" disabled="disabled" type="submit" value="Level 4"/>
						</c:if>
						<c:if test="${maxLevel >= 4 }">
							<input name="Level4Button" type="submit" value="Level 4"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 5 }">
							<input name="Level2Button" disabled="disabled" type="submit" value="Level 5"/>
						</c:if>
						<c:if test="${maxLevel >= 5 }">
							<input name="Level5Button" type="submit" value="Level 5"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td><c:if test="${maxLevel < 6 }">
							<input name="Level6Button" disabled="disabled" type="submit" value="Level 6"/>
						</c:if>
						<c:if test="${maxLevel >= 6 }">
							<input name="Level6Button" type="submit" value="Level 6"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 7 }">
							<input name="Level7Button" disabled="disabled" type="submit" value="Level 7"/>
						</c:if>
						<c:if test="${maxLevel >= 7 }">
							<input name="Level7Button" type="submit" value="Level 7"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 8 }">
							<input name="Level8Button" disabled="disabled" type="submit" value="Level 8"/>
						</c:if>
						<c:if test="${maxLevel >= 8 }">
							<input name="Level8Button" type="submit" value="Level 8"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 9 }">
							<input name="Level9Button" disabled="disabled" type="submit" value="Level 9"/>
						</c:if>
						<c:if test="${maxLevel >= 9 }">
							<input name="Level9Button" type="submit" value="Level 9"/>
						</c:if>
					</td>
					<td><c:if test="${maxLevel < 10 }">
							<input name="Level10Button" disabled="disabled" type="submit" value="Level 10"/>
						</c:if>
						<c:if test="${maxLevel >= 10 }">
							<input name="Level10Button" type="submit" value="Level 10"/>
						</c:if>
					</td>
				</tr>
			</table>
		</form>
	</body>	
</html>