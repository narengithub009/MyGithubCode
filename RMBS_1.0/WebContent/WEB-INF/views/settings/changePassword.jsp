<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
   <script type="text/javascript" language="javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/jquery.js"></script>
  <script type="text/javascript" src="http://www.technicalkeeda.com/js/javascripts/plugin/json2.js"></script>
  <script type="text/javascript" src="js/masters/doctorFormValidation.js"></script>
  <link rel="stylesheet" type="text/css" href="css/masters/productentryform.css" />
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
       
        <title>Change Password</title>
        
         <script>
         
         function changePassword()
         {
         var data= $("#changePw").serialize();
         //alert(data);
       $("#changePw").get(0).reset();

         $.post('changeToNewPassword.html', data, function(result){
         
         	$(".col2").html(result);
         	clearVals();
         });
         }
         
         function clearVals(){
        		 $('#cmpwd').val('');
        		$('#newpassword').val('');
        		$('#pwd').val('');
        				
        	
        }

         function validatePass(password, cmpwd) {
    //alert("Cs")
    if (password.value != cmpwd.value || password.value == '' || cmpwd.value == '') {
        //cmpwd.setCustomValidity('Password incorrect');
        alert("Password not matched");
        cmpwd.setCustomValidity('Password incorrect');
    } else {
        cmpwd.setCustomValidity('');
    }
}


$(document).ready(function() {
length=true;
letter=true;
capital=true;
number=true;
	$('#newpassword').keyup(function() {
		var pswd = $(this).val();
		

		if ( pswd.length < 8 ) {
			$('#length').removeClass('valid').addClass('invalid');
			length=false;
		} else {
			$('#length').removeClass('invalid').addClass('valid');
			length=true;
		}		

		if ( pswd.match(/[A-z]/) ) {
			$('#letter').removeClass('invalid').addClass('valid');
			letter=true;
		} else {
			$('#letter').removeClass('valid').addClass('invalid');
			letter=false;
		}

		//validate Special character
		if ( pswd.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/)) {
			$('#symbol').removeClass('invalid').addClass('valid');
			capital=true;
		} else {
			$('#symbol').removeClass('valid').addClass('invalid');
			capital=false;
		}

		//validate number
		if ( pswd.match(/\d/) ) {
			$('#number').removeClass('invalid').addClass('valid');
			number=true;
		} else {
			$('#number').removeClass('valid').addClass('invalid');
			number=false;
		}
		
	}).focus(function() {
		$('#pswd_info').show();
	}).blur(function() {
		$('#pswd_info').hide();
		if(!(length && letter && capital && number)){
			alert("Follow Password Instruction");
			$('#newpassword').focus();
		}
     });
	
});

</script>
<style>
input {
	padding:10px 10px;
	
}

input:focus {
	border:1px solid #b9d4e9;
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
#button{
width:70px;
padding:2px;
float:right;
color:white;
background-image:url(images/green.png);
font-weight: bold;
}
</style>
    <style>
             #pwd{
                 color: #000;
                 
                 
             }
         </style>
         <style>
    
#pwdheader{
color: white;
background-color: #777;
margin-top: 0px;
font-weight: bold;
font-size: 20px;

}
#pwd h3{
color: green;

}    

.ChangePassword{

}
.ChangePassword{
   position: relative;
    background-color: #EFF0F0;
    border: 1px solid  #3C873C;
    width: 400px;
    padding: 5px;
    font-family:calibri;
    font-size: 15px;
    color: #736F69;
    border-radius: 3px;
	float: right;
}
@media only screen   
and (max-width:750px) {
.ChangePassword{
   width: 90%;
}
}


.pac-container{
/*width: 152px; position: absolute; left: 1036px; top: 474px; display: none;*/
background-image:url(images/registeragency1.jpg);
background-position: center;
color: white;
margin-top: -20px;
}


.actionlink a{
	text-decoration: none;
	background-color: lightblue;
	text-decoration: blink;
	padding: 5px;
	color: white;
	font-weight: bold;
	margin: 10px;
	float: right;
	}   
	a{
	text-decoration: none;
	}  
	#userdetails{
	background-image:url(images/listbackground1.jpg);
	}
	.error {
	color: #ff0000;
	font-style: italic;
	}


</style>
    </head>
    <body>
     <div class="ChangePassword">   
        <h3 align="center">Change Password</h3>
        <div id="">
          <div class="wrapper">
            <form:form method="POST" action="javascript:changePassword();" commandName="command" id="changePw" >
            <%--style="border: 7px #777 solid; width: 40%;margin-left: 20%;background-image:url(images/background2.gif);" onsubmit="return passchk()" --%>
            <div id="msg" align="center">${msg}</div>
          
            <div>
              <form:label path="password">Current Password</form:label>
			  <form:input type="password" path="password" value="${Logger.password}" readonly="" id="pwd"/>
            </div>
           
          <div>
          	 <form:label path="newPassWord">New Password</form:label>
			 <form:input type="password" path="newPassWord"  id="newpassword" /> 
          </div>
                  
        
   <%-- <td>Password Strength:</td><td style="width: 300px"> <div id="passwordDescription">&nbsp;&nbsp;&nbsp;Password not entered</div>
                
			<div id="passwordStrength" class="strength0"></div>
		</td>--%> 
		
		 
                   
   
           <div><form:label path="confirmPassword">Confirm Password</form:label>
			        <form:input type="password"  path="confirmPassword" name="cmpwd" id="cmpwd" onblur="validatePass(document.getElementById('newpassword'), cmpwd);"/>
            </div>
            <div>
               
                    <input type="submit" value="Submit" id="button" >
              
            </div>
                
           
      
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
          </div>
    </body>
</html>
