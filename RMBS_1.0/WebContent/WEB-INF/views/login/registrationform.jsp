<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register here</title>
    
 
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
     <script type="text/javascript" src="js/registration.js"></script>
   <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css"/>
       <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
        <script type="text/javascript" src="js/googleAddress.js"></script>
         <script>
         
       //----------------------------address--------
 
          
google.maps.event.addDomListener(window, 'load', initialize);

       $(function() {
	$( "#dob" ).datepicker({
		dateFormat: 'dd-mm-yy',
		changeMonth: true,
		changeYear: true,
		autoclse: true,
		yearRange: "-100:+0"
		
	});
});

       function Validate() {

   		var image = document.getElementById("image").value;
   		if (image != '') {
   			var checkimg = image.toLowerCase();
   			if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)) {
   				alert("Please enter  Image  File Extensions .jpg,.png,.jpeg");
   				document.getElementById("image").focus();
   				return false;
   			}
   		}
   		return true;
   	}
     
    </script>
  


<style type="text/css">
input:focus {
	border:2px solid #b9d4e9;
	border-top-color:#b6d5ea;
	border-bottom-color:#b8d4ea;
	box-shadow:0 0 5px #b9d4e9;
}
.invalid {
	background:url(images/invalid.png) no-repeat 0 50%;
	padding-left:22px;
	line-height:24px;
	color:#ec3f41;
}
.valid {
	background:url(images/valid.png) no-repeat 0 50%;
	padding-left:22px;
	line-height:24px;
	color:#3a7d34;
}
#pswd_info::before {
	content: "\25B2";
	position:absolute;
	top:-12px;
	left:45%;
	font-size:14px;
	line-height:14px;
	color:#ddd;
	text-shadow:none;
	display:block;
}
#pswd_info h4 {
	margin:0 0 10px 0;
	padding:0;
	font-weight:normal;
}
#pswd_info {
	position:absolute;
	bottom:-100px;
	bottom: -115px\9; /* IE Specific */
	right:55px;
	width:250px;
	padding:15px;
	background:#fefefe;
	font-size:.875em;
	border-radius:5px;
	box-shadow:0 1px 3px #ccc;
	border:1px solid #ddd;
}
#pswd_info {
	display:none;
}
#ui-datepicker-div{
width: 13.5%;
 background-image:url(images/background1.png);
}
.ui-datepicker-year{
maxHeight: 3;
}
.ui-datepicker {
   background: #333;
   border: 1px solid #555;
   color: #EEE;
  margin-top: -10px;
   margin-left: -20px;
 }
div.ui-datepicker{ 
 font-size:12px;
 position: relative;
}
div.ui-datepicker select{

}


.registerform a{
text-decoration: none;

color: green;
font-weight: bold;
padding: 0px 5px 0px 5px;

}



.registerform .sname, .tin, .sadd{
display: none;
}
.pac-container{
/*width: 152px; position: absolute; left: 1036px; top: 474px; display: none;
background-image:url(images/background2.gif);*/
background-position: left;
font-style: oblique;
 position: relative;
margin-top: -270px;
overflow: auto;

 box-shadow: 1px 1px 10px 7px #555555 ;
 left: 20px;
}
</style>
</head>
<body>
<div class="registerform">
<h3>Register Here</h3>
<div class="wrapper">
<form:form commandName="command" method="post"
				enctype="multipart/form-data" action="validateRegistration.html"
				name="frm" class="registrationcontentpage" id="registerform1"
				onSubmit="return Validate();">

	<div><p><c:out value="${message}"/></p></div>
	<div>
		<label><form:label path="initial">Initial</form:label></label>
		
		    <form:select path="initial" id="init">
                    <form:options items="${initials}"/>
                </form:select>

                </div> 

	<div>
		<label><form:label path="firstName" >First Name</form:label></label>
		<form:input path="firstName" class="names" id="fname" />
	</div>
	
    <div>
		<label><form:label path="lastName" >Last Name</form:label></label>
		<form:input path="lastName" class="names" id="lname"  />
	</div>
	<div>
		<label><form:label path="userName" >User Name</form:label></label>
		<form:input path="userName" id="uname"  onblur="return userid_validation(this,5,12);" />
	</div>
	<div  style="display: none;">
		<label><form:label path="gender" >Gender</form:label></label>
		<form:input path="gender" id="sex" value="" hidden="true"/>
	</div>
	
	<div>
		<label><form:label path="password" >Password</form:label></label>
		<form:input type="password" path="password" id="pwd"/>
	</div>
	<div>
		<label><form:label path="confirmPassword" >Confirm Password</form:label></label>
		<form:input type="password" path="confirmPassword" id="cpwd"  onfocus="disableerror('cpwd');"/>
	</div>
	<div>
		<label><form:label path="dob" >Date of Birth</form:label></label>
		<form:input path="dob" id="dob" placeholder="Select" maxlength="0"/>
	</div>
	<div>
		<label><form:label path="addressBean.mobile" >Mobile</form:label></label>
	    <form:input path="addressBean.mobile" class="phno" id="phno1" maxlength="15"/>
	</div>
		<div>
		<label> <form:label path="addressBean.email" >Email Id</form:label></label>
		<form:input path="addressBean.email" id="pmail" class="email" placeholder="abcd@gmial.com" />
	</div>
	<div id="toggle"><a href="#" id="shopdetails">*&nbsp;click to enter shop details</a></div>
	<div class="sname">
		<label><form:label path="name" >Shop Name</form:label></label>
		<form:input path="name" class="shopnames" id="sname" />
	</div>
    <div  class="tin">
		<label><form:label path="tinNo" >Tin No</form:label></label>
		<form:input path="tinNo" id="tno"/>
	</div>
	  <div  class="sadd">
		<label><form:label path="addressBean.address" >Address</form:label></label>
		<form:textarea path="addressBean.address" id="add" rows="3"/>
	</div>
	<div>
					<label><form:label for="imageData" path="imageData">File</form:label></label><br />
					<form:input path="imageData" id="image" type="file"
						onmouseout="imageDisplay();" />
				</div>
				<div id='imageFile'></div>
	<div>

		
			<input type="submit" value="Register" id="submit"/>
		
	</div>


	<!-- <div id="map-canvas" style="height:100px;"></div> -->	
</form:form>
<div id="pswd_info">
	<h4>Password must meet the following requirements:</h4>
	<ul>
		<li id="letter" class="invalid">At least <strong>one letter</strong></li>
		<li id="symbol" class="invalid">At least <strong>one special character</strong></li>
		<li id="number" class="invalid">At least <strong>one number</strong></li>
		<li id="length" class="invalid">Be at least <strong>8 characters</strong></li>
	</ul>
</div>
</div>

</div>
<dir>



</dir>
</body>
</html>