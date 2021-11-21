<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/masters/productentryform.css" />
<title>Mail Sending Form</title>

<style type="text/css">
.valueBoxes {
	margin-top: 10px;
}

.valueBoxes label {
	min-width: 120px;
	padding-right: 12px;
	text-align: justify;
	color: gray;
}

.valueBoxes input {
	padding-right: 20px;
}

.wrapper input[type="submit"] {
	float: right;
	font-weight: bold;
}

.wrapper input[type="text"],input [type="select"] {
	min-width: 120px;
	width: 180px;
	margin: 0px;
}

.wrapper {
	margin: 5px;
	padding: 10px;
	border: green solid 1px;
	float: right;
}

</style>

<script type="text/javascript">

	
	
	function formSubmitting(){
		
		var data = $('.sendClass').serialize();
		alert('data '+$('#pword').val());
		$.ajax({
			
			type:"POST",
			url:'sendingMail.html',
			data:data,
			dataType:'application/json',
			success:function(response){
				alert(response);
				$('#info').html(response);
				$('#info').css({"color":"green","border": "green solid 1px","background-color": "aqua","margin":"15px"});
				$('.sendClass')[0].reset();
			},
			error:function(error){
				$('#error').html(error);
				$('#error').css({"color":"red","background-color":"aqua","border":"red solid 1px"});
			}
		});
		
	}

		
		
	
	$(document).ready(function() {
		
		
		$('#adid').attr('checked',false);
		$('#bid').attr('checked',true);
		$('#outletid').attr('checked',false);
		$('#orgid').attr('checked',false);
		$('#outletId').hide();
		$('#olol').hide();
		$('#bid').click(function(){
			$('#adid').attr('checked',false);
			$('#mail').val("");
			$('#branchId').show();
			$('#blbl').show();
			
		});
		$('#adid').click(function(){
			$('#bid').attr('checked',false);
			$('#branchId').hide();
			$('#blbl').hide();
			$('#mail').val("");
			var data = {orgId:'${orgId}'};
			
			$.ajax({
				
				type:"POST",
				url:'getAdminMailId.html',
				data:data,
				dataType:'application/json',
				success:function(response){
					alert(response);
					$('#mail').val(response);
				},
				error:function(error){
					alert('error '+error);
				}
			});
			
		});
		
		$('#branchId').blur(function(){
			var data = {orgId:'${orgId}',branchId:$('#branchId').val()};
			alert('data '+'${orgId}');
			if($('#branchId').val()!=0){
			$.ajax({
				
				type:"POST",
				url:'getBranchMailId.html',
				data:data,
				dataType:'application/json',
				success:function(response){
					$('#mail').val(response);
				},
				error:function(error){
					alert('error '+error);
				}
			});
			}else{
				alert('please select mail id');
			}
			});
		$('#orgAdminId').blur(function() {
			
			data = {orgId:$('#orgAdminId').val()};
			alert('data '+data);
			$.ajax({
				
				type:"POST",
				url:'getOrgEmailId.html',
				data:data,
				dataType:'apllication/json',
				success:function(response){
					$('#mail').val($.trim(response));
					$('#mail').attr('readonly',true);
				},
				error:function(error){
					alert('error '+error);
				}
			});
			
		});
	$('.bidn').click(function(){
		alert('calling');
		$('#mail').val('${email}');
	});
		$('#outletid').click(function() {
			//var branchId = '${bId}'; 
			$('#orgid').attr('checked',false);
			$('#outletId').show();
			$('#olol').show();
			
			
		});
		
		$('#orgid').click(function(){
			$('#mail').val("");
			$('#outletid').attr('checked',false);
			$('#outletId').hide();
			$('#olol').hide();
			data={orgId:'${orgId}'};
			alert('orgid '+data);
			$.ajax({
				
				type:"POST",
				url:'getOrgEmailId.html',
				data:data,
				dataType:'application/json',
				success:function(response){
				
					$('#mail').val(response);
				},
				error:function(error){
					alert('error '+error);
				}
			});
		});
		
		$('#outletId').blur(function(){
			$('#mail').val("");
			var data = {branchId:'${branchId}',outletId:$('#outletId').val()};
			alert('calling'+'${branchId}');
			$.ajax({
				
				type:"POST",
				url:'getOutletEmailId.html',
				data:data,
				dataType:'application/json',
				success:function(response){
					
					$('#mail').val(response);
				},
				error:function(error){
					alert('error '+error);
				}
			});
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<form:form method="POST" action="javascript:formSubmitting();" class='sendClass' id='sendId'>
		<div id="info"></div>
		<div id="error"></div>
		<c:set var="orgId" value='${orgId}'></c:set>
		<c:set var="bId" value='${branchId}'></c:set>
			<h3>Mail Sending Form</h3>
			<div class="valueBoxes">
				<div>
					<form:label path="username">Username</form:label>
					<form:input path="username" id='uid'/>
				</div>
				<div>
					<form:label path="password">Password</form:label>
					<form:input path="password" type="password" id="pword"/>
				</div>
				<div>
				<c:if test="${mode==4}">
					<form:radiobutton path="branchBean.id" value="${branch}" id="bid" class="bidn"/>
					<form:label path="branchBean.id">Branch</form:label>
				</c:if>
				</div>
				<div>
					<c:if test="${mode==3}">
					<form:radiobutton path="orgId" value="${orgId}" id="orgid" />
					<form:label path="orgId">Organization</form:label>
					<form:radiobutton path="outletId" value="${outletId}" id="outletid" />
					<form:label path="outletId">Outlet</form:label>
					<div>
					<form:label path="outletBean.id" id='olol'>Outlets</form:label>
					<form:select path="outletBean.id" id="outletId" >
						<form:option value="${outletId}">${outlet}</form:option>
						<form:options items="${outletIds}" />
					</form:select>
					</div>
					</c:if>
					<c:if test="${mode==2 }">
							
					<form:radiobutton path="branchBean.organizationBean.id" value="${adminId}" id="adid"  />
					<form:label path="branchBean.organizationBean.id">Admin</form:label>
					<form:radiobutton path="branchBean.id" value="${branch}" id="bid" />
					<form:label path="branchBean.id">Branch</form:label>
					
					<div>
					<form:label path="branchBean.id" id='blbl'>Branch</form:label>
					<form:select path="branchBean.id" id="branchId" >
						<form:option value="${branchId}">${branch}</form:option>
						<form:options items="${branches}" />
					</form:select>
					</div>
					</c:if>
					
				
					<c:if test="${mode==1 }">
					<form:label path="branchBean.organizationBean.id">Organization</form:label>
					<form:select path="branchBean.organizationBean.id" id="orgAdminId">
						<form:option value="${adminId}">${admin}</form:option>
						<form:options items="${orgs}" />
					</form:select>
					</c:if>
				</div>
				<div>
					<form:label path="mailTo">MailId</form:label>
					<form:input path="mailTo" id="mail"></form:input>
				</div>

				<div>
					<form:label path="body">Message</form:label>
					<form:textarea path="body" />
					<br>
				</div>
				<div>
					<input type="submit" value="Send" />
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>