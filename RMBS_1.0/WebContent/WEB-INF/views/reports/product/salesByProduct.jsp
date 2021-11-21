
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
<script type="text/javascript" charset="utf-8" src="js/jquery1.7.2.js"></script>
<script type="text/javascript" charset="utf-8"
	src="media/js/dataTables.js"></script>

<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">

<script type="text/javascript" charset="utf-8">
           $(document).ready( function () {
                $('#example1').dataTable( {
                    "bJQueryUI": true,    
                    "sScrollY": "300px",
             //	   "sScrollX": "110%",
             	   "bScrollCollapse": true,
             	  "bPaginate": false,
                  //  "sPaginationType": "full_numbers",
                    //"sDom": '<"H"Tfr>t<"F"ip>',
              //      "iDisplayLength": 10,
                } );
           });
                
       
         	 
           
           
           </script>
<style>
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

.display{
word-wrap: break-word;
}
.display th{
background-image: url(images/green.png);
color: #fff;
text-align: center;
font-weight: bold;
}
  
.listcaption{
background-image: url(images/green.png);
color: #FFF;
width:100%;
font-weight: bold;
float: left;
}
table{
word-wrap: break-word;
}
html{
font-family:calibri;
    color: #736F69;
}
.display .submit{
float: right;
}
.display .changed_items{
font-size: 12px;
vertical-align: middle;
}
.display .changed{
font-weight: bold;
}
.display .changed input{
background-color: lightgray;
color: #fff;
font-weight: bold;
text-align: center;
}
</style>
</head>
<body>
<div class="lineitems">

	<c:if test="${!empty salesLineItems}">
	
		<div id="editform"></div>

		<h2 align="center" class="listcaption">
		
		</h2>		
		
		<table id="example1" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>
					<th>OrderId</th>
					<th>Product</th>
					<th>Quantity</th>
					<th>Amount</th>
					<th>ExpiryDate</th>
					<th>Unit Price</th>
					<th>Discount</th>
					<th>Vat</th>		
					<th>Net Price</th>
					
					
					
						

				</tr>

			</thead>
			<tbody>
			
				<% int i=1; %>

				<c:set value="${sessionScope.user.id}" var="id" />

				<c:forEach items="${salesLineItems}" var="salesOrderLineItems">			
                
                 <tr id="<c:out value="${salesOrderLineItems.id}"/>1">
						<td><%=i%></td>
						<td><c:out value="${salesOrderLineItems.salesOrderBean1.orderIdByDate}" /></td>
						<td><c:out value="${salesOrderLineItems.productInventoryBean.productBean.name}" /></td>
						<td><c:out value="${salesOrderLineItems.quantity}" /></td>
						
						<td><c:out value="${salesOrderLineItems.amount}" /></td>
						<td><c:out value="${salesOrderLineItems.productInventoryBean.expiryDate}" /></td>
						
						<td><c:out value="${salesOrderLineItems.unitPrice}" /></td>
						<td><c:out value="${salesOrderLineItems.discount}" /></td>
						<td><c:out value="${salesOrderLineItems.vat}" /></td>
						<td><c:out value="${salesOrderLineItems.netPrice}" /></td>
					
						
					<%--	<td><a href="#" onclick="createReturnOrder(<c:out value="${salesOrderLineItems.id}"/>,<c:out value="${salesOrderLineItems.quantity}" />)">Update</a></td>
					<td><c:out value="${salesOrderLineItems.payAmount}" /></td>	 --%>						
				
			
					</tr>
                
					
					<% i++; %>

				
				</c:forEach>
			</tbody>
			
		</table>
		

	</c:if>
<c:if test="${empty salesLineItems}"> Data is not available</c:if>

	<div id='msgbox' title='' ></div>
</div>

</body>
</html>
