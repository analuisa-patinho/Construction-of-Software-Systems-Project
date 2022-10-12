<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"
	pageEncoding="ISO-8859-15"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.ScheduleOccasionalActivityModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">

</head>
<body>

	<h2>Schedule Occasional Activity</h2>

	<form:form action="chooseDatesTimes" method="get" modelAttribute="dates">
		<label>Choose Dates:</label>

		<div class="mandatory_field">
			<c:forEach var="d" items="${model.dates}" varStatus="status">
				<%--Serve para o formato da 
				data <p>Formatted Date (7): <fmt:formatDate pattern = "dd-MM-yyyy" value = "${now}" /></p>  --%>		
			 	<label for="dates">Date:</label> 
             	<input path="d[${status.index}].date" type="datetime-local" name="date" min="2022-03-20T10:15" required/>
             	<span class ="validity"></span>
             </c:forEach> 
		</div>

		<div class="button" align="right">
			<input type="submit" value="Next">
		</div>
	</form:form>
	
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