<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>Sample Form</title>
   	<script src="resources/js/jquery.min.js"></script>
    <style>
    	body { background-color: #eee; font: helvetica; }
    	#container { width: 500px; background-color: #fff; margin: 30px auto; padding: 30px; border-radius: 5px; }
    	.green { font-weight: bold; color: green; }
    	.message { margin-bottom: 10px; }
    	label {width:70px; display:inline-block;}
    	input { display:inline-block; margin-right: 10px; }
    	form {line-height: 160%; }
    	.hide { display: none; }
    	.error { color: red; font-size: 0.9em; font-weight: bold; }
    </style>
  </head>
  <body>
	
	<div id="container">
	
		<h2>spring项目验证设计!</h2>
		<c:if test="${not empty message}"><div class="message green">${message}</div></c:if>
		
		<form:form action="" modelAttribute="userBean">
			<label for="nameInput">Name: </label>
			<form:input path="name" id="nameInput" />
			<form:errors path="name" cssClass="error" />
			<br/>
			
			<label for="ageInput">Age: </label>
			<form:input path="age" id="ageInput" />
			<form:errors path="age" cssClass="error" />
			<br/>
		
			
			<label for="emailInput">Email: </label>
			<form:input path="email" id="emailInput" />
			<form:errors path="email" cssClass="error" />
			<br/>
			<input type="submit" value="Submit" />
		</form:form>
	</div>
	
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			toggleFrequencySelectBox(); // show/hide box on page load
			
			$('#newsletterCheckbox').change(function() {
				toggleFrequencySelectBox();
			})
			
		});
		
		function toggleFrequencySelectBox() {
			if(!$('#newsletterCheckbox').is(':checked')) {
				$('#frequencySelect').val('');
				$('#frequencySelect').prop('disabled', true);
			} else {
				$('#frequencySelect').prop('disabled', false);
			}
		}
	
	</script>
	
  </body>
</html>
