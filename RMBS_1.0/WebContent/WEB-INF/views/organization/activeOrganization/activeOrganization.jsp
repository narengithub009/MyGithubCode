<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery1.7.2.js"></script>
<script type="text/javascript" language="javascript">
$(document).ready(function() {
     noBack();
});

function noBack() {
    window.history.forward(1);
    }
    

	
/*$(function() {	
	 $.get("expiredProducts.html?task_list_type=sub", function(result){
  		  if(result!=null){
  			$('.col1').html(result);
  		  
  		  }
  	  });

   	
	});*/
</script>
<style type="text/css">



</style>
</head>
<body>


<jsp:include page="/WEB-INF/views/notifications/notifications.jsp"></jsp:include>

</body>
</html>