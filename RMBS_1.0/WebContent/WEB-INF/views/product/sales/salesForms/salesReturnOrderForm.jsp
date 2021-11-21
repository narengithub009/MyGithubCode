<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:url value="/salesReturnLineItemsByOrderId.html" var="salesReturnLineItems"/>
<!DOCTYPE html>
<html>
<head>

 
<%--<link rel="stylesheet" type="text/css" href="css/masters/productentryform.css" />  --%>

<script type='text/javascript' src='<c:url value="/js/jquery-ui.min.js"/>'></script>
 <link href="media/themes/smoothness/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" /> 
<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
<style type="text/css">
.container {
	border: 1px solid green;
	border-radius: 5px 5px 5px 5px;
	background-color: #EFF0F0;
	padding: 5px;
	font-family: calibri;
	font-size: 15px;
	color: #736F69;
}

#block {
	display: inline-block;
	padding: 5px;
}

button {
	font-size: 17px;
	font-weight: bold;
	background: url("../../images/green.png") repeat scroll 0 0 transparent;
	color: #FFF;
	height: 20px;
	padding-left: 10px;
	border: 0.5px #fff solid;
}

label {
	display: inline-block;
	
}
</style>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		
		
				$('#submit').click(function(){
					if($('#oId').val()==""){
						alert('Please fill the Order Id');
						$('#oId').focus();return false;
						}
					
				//	alert("hi");
					var formData = $("#salesReturn").serialize();
				//	alert('form data ' + formData);
					$.get('${salesReturnLineItems}', formData, function(result) {
					//	alert(result);
						$(".reports_container").html(result);
				
					});
				
			});
				
				
			});
			
</script>
<style>
.container form div {
	display: inline-block;
	padding: 5px;
}

.container form,.reports_container {
	margin: 10px;
	padding: 5px;
	border: 1px solid green;
	border-radius: 5px 5px 5px 5px;
}
.reports_container {
	min-height: 200px;
}
.listcaption {
	background-image: url(images/green.png);
	color: #FFF;
	width: 100%;
	font-weight: bold;
	float: left;
}
h3{
	font-size: 17px;
	font-weight: bold;
	background: url("images/green.png") repeat scroll 0 0 transparent;
	color: #FFF;
	height: 20px;
	padding-left: 10px;
	
}
.wrapper input[type=text] {
    border: 1px solid #A0A0A0;
    width: 140px;
    padding: 0 8px 0 6px;
    height:20px;
    font-family:calibri;
    font-size: 15px;
    border-radius: 5px 5px 5px 5px;
}
.wrapper select{
  border: 1px solid #A0A0A0;
  border-radius: 5px 5px 5px 5px;
}

.ui-state-default, .ui-widget-content .ui-state-default {
    background: url("images/green.png") repeat-x scroll 50% 50% #E6E6E6;
    border: 1px solid #D3D3D3;
    color: #FFFFFF;
    font-weight: bold;
    outline: medium none;
}
.content-wrapper .col1 {
	display: none;
}

.content-wrapper .col2 {
	width: 100%;
}

</style>
</head>
<body>
	<div class="container">
		<div class="listCaption" align="center">
			<h3>Sales Returns Form</h3>
		</div>
		<form:form method="Get" class="wrapper" id="salesReturn">
			<div>
				<form:label path="orderIdByDate">Order Id</form:label>
				<form:input path="orderIdByDate" id="oId" />					
			</div>
		
			<div>
					<input type="button" value="Get Details" id='submit'/>
			</div>
		</form:form>
		

		<div class="reports_container"></div>
	</div>
</body>

</html>