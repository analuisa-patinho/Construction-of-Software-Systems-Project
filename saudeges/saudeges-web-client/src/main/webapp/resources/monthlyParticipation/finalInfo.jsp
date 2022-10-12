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
	<c:choose>
		<c:when test="${empty model.cost && empty model.reference && empty model.entity && empty model.payDay}">
	
			<h2>Buy Monthly Participation</h2>
			<form action="finalizeBuying" method="get">
				<label>Choose Starting Date:</label>
					<div class="mandatory_field">
						<label for="startDate">Start Date:</label> 
                        <input type="date" name="startDate" required pattern="\d{4}-\d{2}-\d{2}"/>
                         <span class ="validity"></span>
                    </div> 	
					<div class="mandatory_field">
						<label for="email">User Email:</label> <input type="text" name="email"
						value="${model.email}" />
					</div>
		
				<div class="button" align="right">
					<input type="submit" value="Finalize">
				</div>
			</form>
		</c:when>

		<c:when test="${!empty model.cost && !empty model.entity && !empty model.reference && !empty model.payDay}">
			<h2>Participation Bought Successfully</h2>
				Total: ${model.cost} 
				<li>Reference: ${model.reference}</li>
				<li>Entity: ${model.entity}</li>
				<li>Payment Deadline: ${model.payDay}</li>
			
			<a href="<c:url value="/index.html" />">Main Menu</a>
		</c:when>
		<c:otherwise>
			Error Buying Participation!
			<a href="<c:url value="/index.html" />">Main Menu</a>
		</c:otherwise>
	</c:choose>
	<c:if test="${model.hasMessages}">
		<p>Messages</p>
		<ul>
			<c:forEach var="message" items="${model.messages}">
				<li>${mesage}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>