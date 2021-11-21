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
   //      $("#changePw").get(0).reset();

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

function passwordStrength(password)
{
	var desc = new Array();
	desc[0] = "Very Weak";
	desc[1] = "Weak";
	desc[2] = "Better";
	desc[3] = "Medium";
	desc[4] = "Strong";
	desc[5] = "Strongest";

	var score   = 0;

	//if password bigger than 6 give 1 point
	if (password.length > 6) score++;

	//if password has both lower and uppercase characters give 1 point	
	if ( ( password.match(/[a-z]/) ) && ( password.match(/[A-Z]/) ) ) score++;

	//if password has at least one number give 1 point
	if (password.match(/\d+/)) score++;

	//if password has at least one special caracther give 1 point
	if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) )	score++;

	//if password bigger than 12 give another 1 point
	if (password.length > 12) score++;

	 document.getElementById("passwordDescription").innerHTML = desc[score];
         
	 document.getElementById("passwordDescription").className = "strength" + score;
}

function passchk()
{
    if(document.getElementById("passwordDescription").innerHTML != "Strongest")
{
    if(document.getElementById("passwordDescription").innerHTML != "Strong")
    {
    alert("Please change your password as strong");
    return false;  
}
}
return true;
}
</script>
    <style>
             #pwd{
                 color: #000;
                 
                 
             }
         </style>
         <style>
     #passwordStrength
{
	height:10px;
	display:block;
	
}
#newpassword,#passwordDescription{
display: inline-block;
}
.strength0
{
    margin-left: 5%;
/*	width:240px;
	background:#cccccc;*/
	color:red;
	
}

.strength1
{
    margin-left: 5%;
/*	width:50px;
	background:#ff0000;*/
	color:orange;
}

.strength2
{
        margin-left: 5%;    
  /*      width:100px;	
	background:#ff5f5f;*/
	color:yellow;
}

.strength3
{
    margin-left: 5%;
/*	width:150px;
	background:#56e500;*/
	color:blue;
}

.strength4
{
    margin-left: 5%;
/*	background:#4dcd00;
	width:200px;*/
	color:green
}

.strength5
{
    margin-left: 5%;
/*	background:#399800;
	width:250px;*/
	color:green;
}
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
            <form:form method="POST" action="javascript:changePassword();" commandName="command" id="changePw" onsubmit="return passchk()" >
            <%--style="border: 7px #777 solid; width: 40%;margin-left: 20%;background-image:url(images/background2.gif);" onsubmit="return passchk()" --%>
            <div id="msg" align="center">${msg}</div>
          
            <div>
              <form:label path="password">Current Password</form:label>
			  <form:input path="password" value="${Logger.password}" readonly="" id="pwd"/>
            </div>
           
          <div>
          	 <form:label path="newPassWord">New Password</form:label>
			 <form:input path="newPassWord"  id="newpassword" onkeyup="passwordStrength(this.value);"/><div id="passwordDescription" ></div> 
          </div>
                  
        
   <%-- <td>Password Strength:</td><td style="width: 300px"> <div id="passwordDescription">&nbsp;&nbsp;&nbsp;Password not entered</div>
                
			<div id="passwordStrength" class="strength0"></div>
		</td>--%> 
		
		 
                   
   
           <div><form:label path="confirmPassword">Confirm Password</form:label>
			        <form:input path="confirmPassword" name="cmpwd" id="cmpwd" onblur="validatePass(document.getElementById('newpassword'), cmpwd);"/>
            </div>
            <div>
               
                    <input type="submit" value="Submit">
              
            </div>
                
           
      
            </form:form>
            </div>   
          </div>
          </div>
    </body>
</html>
