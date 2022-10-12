<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.ScheduleOccasionalActivityModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">
<title>SaudeGes: Schedule Occasional Activity</title>
</head>
<body>
	<h2>Schedule Occasional Activity</h2>
	<form action="chooseSpecialty" method="get">

		<div class="mandatory_field"> 
			<label for="specialty">Choose a Specialty:</label> <select name="specialty">
				<c:forEach var="specialtySelected" items="${model.specialties}">
					<c:choose>
						<c:when test="${model.specialty == specialtySelected.designation}">
							<option selected="selected" value="${specialtySelected.designation}">${specialtySelected.designation}</option>
						</c:when>
						<c:otherwise>
							<option value="${specialtySelected.designation}">${specialtySelected.designation}</option>
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