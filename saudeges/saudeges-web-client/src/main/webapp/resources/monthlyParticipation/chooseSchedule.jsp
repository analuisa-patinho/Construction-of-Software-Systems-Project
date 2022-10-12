<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"
	pageEncoding="ISO-8859-15"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.BuyMonthlyParticipationModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">

</head>
<body>

	<h2>Buy Monthly Participation</h2>

	<form action="chooseSchedule" method="get">
		<label for="schedules">Available Schedules:</label>

		<ul>
			<c:forEach var="schedule" items="${model.schedules}">
				<li>${schedule}</li>
			</c:forEach>
		</ul>

		<div class="mandatory_field">
			<label for="skd">Choose Schedule:</label> <select
				name="skd">
				<c:forEach var="schedule" items="${model.schedules}">
					<c:choose>
						<c:when test="${model.skd == schedule.id}">
							<option selected="selected" value="${schedule.id}">${schedule.id}</option>
						</c:when>
						<c:otherwise>
							<option value="${schedule.id}">${schedule.id}</option>
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
		<p>Messages</p>
		<ul>
			<c:forEach var="message" items="${model.messages}">
				<li>${message}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>