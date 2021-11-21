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
       
        <title>Success Message</title>
        
         <script>
  
</script>

  
<style>   

.SuccessMessage{
   position: relative;
    background-color: #EFF0F0;
    border: 1px solid  #3C873C;
    width: 500px;
    padding: 5px;
    font-family:calibri;
    font-size: 15px;
    color: #736F69;
    border-radius: 3px;
	float: right;
}
#msg{
color: #D8000C;
background-color:green;
background:url(images/valid.png) no-repeat 0 50%;

}


</style>
    </head>
    <body>
     <div class="SuccessMessage">   
       
       
  
           <div id="msg" align="center">${message}</div>
      
          
        
          </div>
    </body>
</html>
