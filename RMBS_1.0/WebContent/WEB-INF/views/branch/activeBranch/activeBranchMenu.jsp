<!--  
<h2>Menu</h2>
1. <a href="employees.html">List of Employees</a><br/>
 2. <a href="add.html">Add Employee</a> -->
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${sessionScope.user.resourceBean.roleBean.id}" var="id"/>
 <!DOCTYPE html>
<html>
<head>
			<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	
	<style>
	
@media only screen and (min-width:980px) and (max-width: 1500px) {
.Transactions a{
width:100px;

}
.Masters a{
width:100px;
}
.Reports a{
width:100px;
}
.Lists a{
width:100px;
}
.Settings a{
width:80px;
}
.DataProcessing a{
width:100px;
}
.Help a{
width:50px;
}
}
</style>		
	
<link rel="stylesheet" type="text/css" href="css/menu/reset.css">
    <link rel="stylesheet" type="text/css" href="css/menu/slimmenu.css">
	<script src="js/menu/jquery.slimmenu.js"></script>
	<script type="text/javascript">
	/* $(document).ready( function () {
		
			 $.post('loadMenu.html',{flag:1}, function(result){
        		  if(result!=null){
        			//  alert(result);
        			createMenu(result,0);
        		//	alert(menu);
        		//	$('.slimmenu').html(menu);
        		  }
		
			 });
		
	 });*/
	 var menuHtml='<li ><a href="home.html?aid=${sessionScope.user.id}" id="user" style="font-weight: bolder;background: url("images/home1.png") no-repeat scroll 90px -2px transparent;background-size:30px 30px;border: none;"><span>&nbsp;Hi&nbsp;${sessionScope.user.firstName}-${Logger}</span></a></li>';
	// var c=0;
      function createMenu(result,val){
    	//  c++;
             var i=val;
			 var j=i;
			 if(i<result.length){
				  parentItem=result[i].parentMenu.parentmenucontent;
				  menuHtml+='<li class="'+parentItem+'"><a href="#">'+parentItem+'</a><ul>';
				  do{
					  j=i;
					  if(parentItem == result[j].parentMenu.parentmenucontent){
							 menuHtml+='<li><a href="#">'+result[j].childmenucontent+'</a></li>';
							 i++;
					   }else{
							 menuHtml+='</ul></li>';
						      break;    
						 }
					}while (i < result.length);
				  if(i < result.length){
			             createMenu(result,i);
				       }
			 }
			 $('.slimmenu').html(menuHtml);
	 }
	 
	function loadAgencyForm(val){
		alert(val);
		//alert( $("#lastname").val());
		$.ajax({
			type: "post",
			url: "http://localhost:8084/MedProj/agencyForm.html?id="+val,
			cache: false,				
			//data:'id=' +val,
			
			success: function(data){
				alert(data);
				$('#regform').html(data);
				//$('#list').html(data);
				
				
				//var val1=data.model.createdby;
				//document.getElementById("createdby").value=val1;
				
				// $(".registerform").html(" vxcvxcv");
				//document.getElementById('replace').innerHTML = '/wesitemenu.jsp'; 
				//var obj = JSON.parse(user);
				//$('#websitemenu').html("First Name:- " + obj.firstname +"</br>Last Name:- " + obj.lastname  + "</br>Email:- " + obj.email);
			},
			error: function(){						
				alert('Error while request..');
			}
		});
		
	}
	
	
	</script>
</head>
<body>
<c:set var="contains" value="false" />
<c:forEach var="item" items="${myList}">
  <c:if test="${item eq myValue}">
    <c:set var="contains" value="true" />
  </c:if>
</c:forEach>

 <script src="js/menu/jquery.slimmenu.js"></script>
<script src="js/menu/easing.min.js"></script>

  <ul class="slimmenu">
 <li ><a href='userHome.html' id="user" style="font-weight: bolder;background: url('images/home1.png') no-repeat scroll 120px -2px transparent;background-size:30px 30px;border: none;"><span>&nbsp;Hi&nbsp;${sessionScope.user.firstName}-${sessionScope.user.lastName}</span></a></li>
 <c:if test='${user.id=="1"}'>
  </c:if>
   <li class="Transactions" ><a href='#'>Transactions</a>
   <ul>       
         <li><a href='openPurchaseEntryForm.html?task_type=main&task_form_type=medical'>Purchases Entry</a></li>
  			<li><a href='openPurchaseReturnsForm.html'>Purchase Returns</a></li>
          <li><a href='openDamagedForm.html?flag=0'>Damage Products Entry</a></li>          
      </ul>
   </li>
  
   <li class="Masters"><a href='#'>Masters</a>
      <ul>
         <li><a href='productEntryForm.html?task_type=main&task_form_type=medical'> Product Stock Entry </a></li>
         <li><a href='productsUpload.html?'> Products Upload </a></li>
         <li><a href='openRevenueAccounts.html'> Revenue Accounts </a></li>
         <li><a href='openOutletForm.html'> Register Outlet </a></li>
         <li><a href='doctorForm.html'> Register Doctor </a></li>
         <li class='last'><a href='agencyForm.html'> Register Agency </a></li>
        
      </ul>
   </li>
   <li class="Reports"><a href='#'>Reports</a>
      <ul>
         <li><a href='openPurchaseReportForm.html?type=salesOrder'> Sales </a></li>
         <li><a href='openPurchaseReportForm.html?type=salesReturn'> Sales Returns </a></li>
			<li><a href='openPurchaseReportForm.html?type=purchase'> Purchase </a></li>
         <li><a href='openPurchaseReportForm.html?type=purchaseReturns'> Purchase Returns </a></li>
         <li><a href='openProductSalesReportForm.html'> Product Sales </a></li>
         <li><a href='openPurchaseReportForm.html?type=PreSales'> Pre Sales </a></li>
      </ul>
   </li>
    <li class="Lists"><a href='#'>Lists</a>
        <ul>
         <li class="Product"><a href='#'>Product</a>
        	<ul>
           		<li><a href='productStockList.html'> Product Stock List </a></li>         	
         		<li><a href='suppliersList.html'> Suppliers List </a></li>
         		<li><a href='masterProductsList.html'> Master Products List </a></li>
         		<li><a href='damagedProducts.html'>Damage Products List</a></li>
         		<li><a href='expiredProducts.html'>Expired Products</a></li>
         	</ul>
        </li> 
         <li class="Sales"><a href='#'>Sales</a>
        	<ul>
           		<li><a href='salesOrderList.html?flag=task_main&task_type=sales'> Sales Order List </a></li>
         		<li><a href='salesOrderList.html?flag=task_main&task_type=preSales'> Pre Sales Order List</a></li>
         		<li><a href='salesReturnOrderList.html?flag=task_main'> Sales Return Order List </a></li>  
         	</ul>
        </li> 
         <li class="Purchase"><a href='#'>Purchase</a>
        	<ul>
           		<li><a href='purchaseOrderList.html?flag=task_main'> Purchase Order List </a></li>
        		 <li><a href='purchaseReturnOrderList.html?flag=task_main'> Purchase Return Order List</a></li>
         	</ul>
        </li>         
         <li><a href='agenciesList.html?'> Agencies List </a></li>
         <li><a href='doctorsList.html?'> Doctors List </a></li>
      <!-- <li><a href='branchesList.html'> Branches List </a></li> -->   
         <li><a href='branchesList.html'> Outlets List </a></li>
      </ul>
   </li>
  <li class="Settings"><a href='#'>Settings</a>
        <ul>
         <li><a href='profileEditFormOfBranch.html'>Edit profile</a></li>
         <li><a href='changePassword.html'> Change Password </a></li>
        
      </ul>
   </li>
     <li class="Help"><a href='#'>Help</a>
     <ul>
         <li><a href='getComposeMailForm.html'> Mail </a></li>
         <li><a href='#'> Messages </a></li>
         <li><a href='#'> Help Content </a></li>
         
      </ul>
   </li>
   
<!-- <li><img src="images/settings1.png" alt="loading" height="35px"/></li> -->
<li style="float:right;"><a href='logout.html'> Logout </a></li>

</ul>
<script>
$('ul.slimmenu').slimmenu(
{
    resizeWidth: '800',
    collapserTitle: 'Rely Services',
    easingEffect:'easeInOutQuint',
    animSpeed:'medium',
    indentChildren: true,
    childrenIndenter: '&raquo;'
});


</script>

</body>
</html>

 