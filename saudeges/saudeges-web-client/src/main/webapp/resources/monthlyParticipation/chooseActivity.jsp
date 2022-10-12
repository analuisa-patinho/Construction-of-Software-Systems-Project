<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.BuyMonthlyParticipationModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">
<title>SaudeGes: Buy Monthly Participation</title>
</head>
<body>
	<h2>Buy Monthly Participation</h2>
	<form action="chooseActivity" method="get">

		<div class="mandatory_field"> 
			<label for="activityName">Choose a Regular Activity:</label> <select name="activityName">
				<c:forEach var="regularActivity" items="${model.regularActivities}">
					<c:choose>
						<c:when test="${model.activityName == regularActivity.name}">
							<option selected="selected" value="${regularActivity.name}">${regularActivity.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${regularActivity.name}">${regularActivity.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>

		<div class="button" align="right">
			<input type="submit" value="Next">
		</div>
	</form>
	<c:if test="${model.hasMessages}">
		<p>Message</p>
		<ul>
			<c:forEach var="message" items="${model.messages}">
				<li>${message}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>