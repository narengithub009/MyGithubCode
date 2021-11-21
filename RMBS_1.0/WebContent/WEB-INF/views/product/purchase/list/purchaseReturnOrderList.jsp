
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/purchaseReturnOrderListReports/download.html" var="downloadUrl1"/>
<c:url value="/download/token.html" var="downloadTokenUrl"/>
<c:url value="/download/progress.html" var="downloadProgressUrl"/>
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
                footerHTML="<input type='button' value='Pdf' id='pdf'></input>",
                footerHTML+="<input type='button' value='Excel' id='excel' ></input>";
                 $('.dataTables_info').append(footerHTML);
                   
                   
                 $('#pdf').click(function() {download('pdf');});
                 $('#excel').click(function() {download('xls');});
           } );
           
           function download(type) {
          //  	  alert("hmmmmmmm calling "+type);
           		// Retrieve download token
           		// When token is received, proceed with download
           		$.get('${downloadTokenUrl}', function(response) {
           			// Store token
           			var token = response.message[0];
           		//	alert("token value "+token);
           			// Show progress dialog
           		$('#msgbox').text('Processing download...');
           			$('#msgbox').dialog( 
           					{	title: 'Download',
           						modal: true,
           						buttons: {"Close": function()  {
           							$(this).dialog("close");} 
           						}
           					});
           			
           			// Start download
           		window.location = '${downloadUrl1}'+'?token='+token+'&type='+type;
           		//	alert("frequency"+token+" "+type);
           			// Check periodically if download has started
           			var frequency = 1000;
           			
           			var timer = setInterval(function() {
           				$.get('${downloadProgressUrl}', {token: token}, 
           						function(response) {
           							// If token is not returned, download has started
           							// Close progress dialog if started
           							if (response.message[0] != token) {
           								$('#msgbox').dialog('close');
           								clearInterval(timer);
           							}
           					});
           			}, frequency);
           		});
           	}
           function purchaseOrderIdByPurchaseLineItems(val){
        	//   alert('calling'+val);
        	   var data = {id:val};
        	   $.post('getPurchaseReturnLineItems.html',data, function(result){
        	//     alert(result);
        		   $('.col2').html(result);
        	   });
           }//
           
           var divClone;
           function getInvoice(val){
        	   divClone = $(".orderLIst").clone(); 
        	   var data = val;
        	   $.post('loadInvoiceByPurchaseReturn.html?id='+val, data, function(result){
        	  //   showInvoice(result);
        	  $(".orderLIst").html(result); 
			  });
           }
           
       
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

.content-wrapper .col1 {
	display: none;
}

.content-wrapper .col2 {
	width: 100%;
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



</style>
</head>
<body>
<div class="orderLIst">
	<jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>

	<c:if test="${!empty purchaseReturnOrders}">
		<c:set value="${createdRole}" var="role"></c:set>
		<c:set value="${purchaseCreatedBy}" var="cby"></c:set>
		<div id="editform"></div>

		<h2 align="center" class="listcaption">Purchase Return Order List</h2>
		<table id="example" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>
					<th>Agency Name</th>
					<th>Branch</th>
					<th>Billing Date</th>
					<th>Delivery Date</th>
					<th>Order No.</th>
					<th>Amount</th>
					<th>Discount Price</th>
					<th>Vat</th>
					<th>Net Amount</th>
					
					<th>Action</th>


				</tr>

			</thead>
			<tbody>

				
				<c:set value="${sessionScope.user.id}" var="id" />

				<c:forEach items="${purchaseReturnOrders}" var="purchaseReturnOrder" varStatus="counter">


					<tr id="<c:out value="${purchaseReturnOrder.id}"/>1">
						<td>${counter.count + subtractor}</td>
						<td><c:out value="${purchaseReturnOrder.agencyBean.agencyName}" /></td>
						<td><c:out value="${purchaseReturnOrder.branchBean.name}" /></td>
						<td><c:out value="${purchaseReturnOrder.billingDateAndTime}" /></td>
						<td><c:out value="${purchaseReturnOrder.deliveryDate}" /></td>
						<td><c:out value="${purchaseReturnOrder.orderIdByDate}" /></td>
						<td><c:out value="${purchaseReturnOrder.amount}" /></td>
						<td><c:out value="${purchaseReturnOrder.discountPrice}" /></td>
						<td><c:out value="${purchaseReturnOrder.totalVAT}" /></td>
						<td><c:out value="${purchaseReturnOrder.payAmount}" /></td>

						<td align="center"><a href="#" title="View Purchase Return Line Items"
							onclick="purchaseOrderIdByPurchaseLineItems(<c:out value="${purchaseReturnOrder.id}"/>,0);"><img
								src="images/open1.jpg" alt="open" height="20px" width="20px" /></a>&nbsp;&nbsp;<a
							href="#" title="View Purchase Return Order Invoice Print Copy"
							onclick="getInvoice(<c:out value="${purchaseReturnOrder.id}"/>);"><img
								src="images/print2.png" alt="print" height="20px" width="20px" /></a>
								
								
								</td>




						<%--			<c:if test='${product.createdBy == cby && product.creatorRoleBean.id == role}'>
 					
						</c:if>
						 <c:if test='${product.createdBy != cby || product.creatorRoleBean.id != role}'>No Actions</c:if>
						</td> --%>
					</tr>
					


				</c:forEach>
			</tbody>
			<tfoot class="listfooter"><tr><td colspan="9" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty purchaseReturnOrders}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/purchaseReturnOrderList.html?pg='+$(this).text()+'&flag=task_main');  
         
        });
    });
</script>
</td>
</tr>
</tfoot>
		</table>


	</c:if>

	<c:if test="${empty purchaseReturnOrders}"> Data is not available</c:if>
	<div id='msgbox' title='' ></div>
	
</div>
</body>
</html>
