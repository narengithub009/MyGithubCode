
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/masters/productentryform.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">
<script src="js/tabs/easyResponsiveTabs.js"></script>
<style>

 .wrapper {
 font-family:calibri;
    font-size: 15px;
    color: #736F69;
}



.wrapper input[type=text] {
    
    width: 140px;
 }
 .wrapper input[type=password] {
    
    width: 140px;
 }
.wrapper label {
    width:80px;
    
    display: inline-block;
}
 .wrapper input[type=submit]{
 float: right;
 background: url("images/green.png") repeat scroll 0 0 transparent;
 border: none;
 color: #fff;
 font-weight: bold;
 }
@media only screen   
and (max-width:750px) {
.wrapper{
   width: 97%;
}
}
@media only screen and (min-width:750px) and (max-width: 980px) {
.wrapper{
   width: 90%;
  margin: auto;
}
.wrapper input[type=text] {
    
    width: 120px;
 }
}
</style>

<script type="text/javascript">
$(document).ready(function () {
	
	$(".subMenus li").click(function(){ 
		alert(this.id);
	
	$.get(this.id+".html?pg=1", function(result){
		  if(result!=null){
			
			  jQuery('#'+this.id).addClass('active');
		$('.productNotification').html(result);
		  
		  }
	  });
	
	});
	
	$.get("expiryProducts.html?pg=1", function(result){
		  if(result!=null){
		$('.productNotification').html(result);
		  
		  }
	  });
});


</script>
	<style>
.resp-tab-content{
border: none;
padding: 0px;	
	}
.resp-tabs-list a{
text-decoration: none;

}	
.activemode{
background-image: url("images/green.png");

}

.ProductStockEntry {
	width: 100%;
}
.valueBoxes{
margin-top: 10px;
}
.valueBoxes div {

	display: inline-block;
}

.valueBoxes label {
	min-width: 120px;
	padding-right: 0px;
	text-align: right;
}

.valueBoxes select {
	width: 155px;
}
#msg{
float:right;
font-weight: bold;
}
.valueBoxes input {
	padding-right: 30px;
}

.wrapper input[type="submit"]{
	float: right;
	margin : 10px;
}
.subMenus li{
display: inline-block;
vertical-align: top;
padding: 5px;
color: green;
font-weight: bold;
border: 1px #E0E0E0 solid;
border-bottom: none;
cursor: pointer;
margin-right: -8px ;
}
.subMenus .active{
background-image: url("images/green.png");
color: #FFFFF;
}
.replace{
border: 3px #3C873C solid;
min-height: 50px;
}

</style>
</head>
<body>

 <!--Horizontal Tab-->
        <div id="">
            <ul class="subMenus">
            <li id="expiryProducts">Going To Expire Products</li>
             <li id="lessQuantityProducts">Less Quantity Products</li>
              
               
            </ul>
            <div>
                <div class="productNotification">
                   <p>
                  
                   </p>
 				</div>
               
              
            </div>
        </div>


</body>
</html>