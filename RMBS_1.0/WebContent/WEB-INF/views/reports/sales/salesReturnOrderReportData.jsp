
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>


<link href="media/dataTables/demo_table_jui.css" rel="stylesheet"
	type="text/css" />
<link href="media/themes/smoothness/jquery-ui-1.7.2.custom.css"
	rel="stylesheet" type="text/css" />
<!-- <script type="text/javascript" charset="utf-8" src="js/jquery1.7.2.js"></script> -->
<script type="text/javascript" charset="utf-8"
	src="media/js/dataTables.js"></script>
<script type='text/javascript' src='<c:url value="/js/jquery-ui.min.js"/>'></script>

<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">

<script type="text/javascript" charset="utf-8">
           $(document).ready( function () {
                $('#example').dataTable( {
                    "bJQueryUI": true,    
                    "sScrollY": "300px",
                    "bPaginate": false,
           //  	   "sScrollX": "100%",
             	   "bScrollCollapse": true,
                  //  "sPaginationType": "full_numbers",
                    //"sDom": '<"H"Tfr>t<"F"ip>',
              //      "iDisplayLength": 10,
                } );
                    
           });  
           
         
           
      
           </script>
<style>

html {
	font-family: calibri;
	color: #736F69;
}
.actionlink a {
	text-decoration: none;
	background-color: lightblue;
	text-decoration: blink;
	padding: 5px;
	color: white;
	font-weight: bold;
	margin: 10px;
	float: right;
}

a {
	text-decoration: none;
}

#userdetails {
	background-image: url(images/listbackground1.jpg);
}

.wrap .listfooter {
	width: 700px;
	height: 30px;
	background-color: #aaa;
	font-size: 15px;
	font-weight: bold;
}

.wrap .listfooter span {
	float: right;
	margin: auto;
}

.display {
	word-wrap: break-word;
}

.display th {
	background-image: url(images/green.png);
	color: #fff;
	text-align: center;
	font-weight: bold;
}



.listcaption {
	background-image: url(images/green.png);
	color: #FFF;
	width: 100%;
	font-weight: bold;
	float: left;
}

table {
	word-wrap: break-word;
}
tfoot input {
	width: 60px;
	float: right;
	margin: opx;
}


</style>
</head>
<body>
<div class="returnOrderLIst">
	
	<c:if test="${!empty salesReturnOrders}">		
	
		
		<h2 align="center" class="listcaption">Sales Return Order List</h2>
		
		<table id="example" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>					
					<th>Branch</th>					
					<th>Billing Date</th>					
					<th>Order No.</th>
					<th>Amount</th>
					<th>Discount Price</th>
					<th>Vat</th>
					<th>Return Amount</th>	
					<!-- <th>Customer Ph.No</th> -->		
					


				</tr>

			</thead>
			<tbody>

				<% int i=1; %>

				<c:set value="${sessionScope.user.id}" var="id" />

				<c:forEach items="${salesReturnOrders}" var="salesReturnOrder">
                
					<tr id="<c:out value="${salesReturnOrder.id}"/>1">
						<td><%=i%></td>
						<td><c:out value="${salesReturnOrder.branchBean.name}" /></td>
						<td><c:out value="${salesReturnOrder.billingDateAndTime}" /></td>						
						<td><c:out value="${salesReturnOrder.orderIdByDate}" /></td>
						<td><c:out value="${salesReturnOrder.amount}" /></td>
						<td><c:out value="${salesReturnOrder.discountPrice}" /></td>
						<td><c:out value="${salesReturnOrder.totalVAT}" /></td>
						<td><c:out value="${salesReturnOrder.payAmount}" /></td>
						

					</tr>
					<% i++; %>


				</c:forEach>
			</tbody>
			
		</table>


	</c:if>

	<c:if test="${empty salesReturnOrders}"> Data is not available</c:if>
	<div id='msgbox' title='' ></div>
	
</div>
</body>
</html>
