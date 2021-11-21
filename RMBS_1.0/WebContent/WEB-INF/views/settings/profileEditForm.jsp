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
   <link rel="stylesheet" type="text/css" href="css/masters/productentryform.css" />
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
position: relative;
/*    background-color: #EFF0F0;
    border: 1px solid  #3C873C;
    width: 350px;
    padding: 5px;
    font-family:calibri;
    font-size: 15px;
    color: #736F69;
    border-radius: 3px;
	float: right;*/

}

.registerform{
    background-color: #EFF0F0;
    border: 1px solid  #3C873C;
    width: 350px;
    padding: 5px;
    font-family:calibri;
    font-size: 15px;
    color: #736F69;
    border-radius: 3px;
	float: right;

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
				enctype="multipart/form-data" action="updateOrganization.html"
				name="frm" class="registrationcontentpage" id="registerform1"
				onSubmit="return Validate();">
				
	 <div id="msg" align="center">${message}</div>
      <input type="hidden" id="addr" value="${org.resourceBean.addressBean.address }"/>
       <input type="hidden" id="images" value="${org.imagePath}"/>
   
   <div><form:input type="hidden"  path="id" id="id" value="${org.id }" readonly="true" /></div>			

	
	<div>
		<label><form:label path="initial">Initial</form:label></label>		
		    <form:select path="initial" id="init" >
                    <form:options items="${initials}" />
                </form:select>

                </div> 

	<div>
		<label><form:label path="firstName" >First Name</form:label></label>
		<form:input path="firstName" class="names" id="fname" value="${org.firstName}" onfocus="disableerror('fname');"/>
	</div>
	
    <div>
		<label><form:label path="lastName" >Last Name</form:label></label>
		<form:input path="lastName" class="names" id="lname"  value="${org.lastName}" onfocus="disableerror('lname');"/>
	</div>
	<div>
		<label><form:label path="userName" >User Name</form:label></label>
		<form:input path="userName" id="uname" value="${org.userName}" readonly="true" onblur="return userid_validation(this,5,12);" onfocus="disableerror('uname');"/>
	</div>
	<div  style="display: none;">
		<label><form:label path="gender" >Gender</form:label></label>
		<form:input path="gender" id="sex" value="${org.gender}" hidden="true"/>
	</div>
	<%--<div>
		<label><form:label path="password" >Password</form:label></label>
		<form:input path="password" id="pwd" value="${org.password}" />
	</div>
	<div>
		<label><form:label path="confirmPassword" >Confirm Password</form:label></label>
		<form:input path="confirmPassword" id="cpwd" onfocus="disableerror('cpwd');"/>
	</div> --%> 
	
	<div>
		<label><form:label path="dob" >Date of Birth</form:label></label>
		<form:input path="dob" id="dob" value="${org.dob}" placeholder="Select" maxlength="0"/>
	</div>
	<div>
		<label><form:label path="resourceBean.addressBean.mobile" >Mobile</form:label></label>
	    <form:input path="resourceBean.addressBean.mobile" class="phno" id="phno1" value="${org.resourceBean.addressBean.mobile}" maxlength="15"/>
	</div>
		<div>
		<label> <form:label path="resourceBean.addressBean.email" >Email Id</form:label></label>
		<form:input path="resourceBean.addressBean.email" id="pmail" class="email" placeholder="abcd@gmial.com" value="${org.resourceBean.addressBean.email}" onfocus="disableerror('pmail');"/>
	</div>
	<div id="toggle"><a href="#" id="shopdetails">*&nbsp;click to enter shop details</a></div>
	<div class="sname">
		<label><form:label path="name" >Shop Name</form:label></label>
		<form:input path="name" class="shopnames" id="sname" value="${org.name}" onfocus="disableerror('sname');"/>
	</div>
    <div  class="tin">
		<label><form:label path="tinNo" >Tin No</form:label></label>
		<form:input path="tinNo" id="tno" value="${org.tinNo}"/>
	</div>
	  <div  class="sadd">
		<label><form:label path="resourceBean.addressBean.address" >Address</form:label></label>
		<form:textarea path="resourceBean.addressBean.address" id="add" rows="3"/>
	</div>
	         <div>
					<label><form:label for="imageData" path="imageData">File</form:label></label><br />
					<form:input path="imageData" id="image" type="file"	onmouseout="imageDisplay();" />
				</div>
			<div id='imageFile'></div>
			
	<div>


			<input type="submit" value="Update" id="submit"/>
		
	</div>


	<!-- <div id="map-canvas" style="height:100px;"></div> -->	
</form:form>
<script>
var address=$('#addr').val();
//alert(address);
$('#add').val(address);

var imagePath=$('#images').val();
$('#image').val(imagePath);
</script>
</div>

</div>
<dir>

</dir>
</body>
</html>