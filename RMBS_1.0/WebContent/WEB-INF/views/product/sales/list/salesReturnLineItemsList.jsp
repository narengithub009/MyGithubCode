
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/salesReturnOrderLineitemsReports/download.html" var="downloadUrl"/>
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
<script type="text/javascript" charset="utf-8" src="js/jquery1.7.2.js"></script>
<script type="text/javascript" charset="utf-8"
	src="media/js/dataTables.js"></script>

<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example1').dataTable({
			"bJQueryUI" : true,
			"sScrollY" : "300px",
			//	   "sScrollX": "110%",
			"bScrollCollapse" : true,
			"bPaginate" : false,
		//  "sPaginationType": "full_numbers",
		//"sDom": '<"H"Tfr>t<"F"ip>',
		//      "iDisplayLength": 10,
		});
		$('#pdf').click(function() {
			download('pdf');
		});
		$('#excel').click(function() {
			download('xls');
		});
	});
	function download(type) {
    	//   alert("invoice name "+$('#prid').val());
        //	  alert("hmmmmmmm calling "+type);
       		// Retrieve download token
       		// When token is received, proceed with download
       		$.get('${downloadTokenUrl}', function(response) {
       			// Store token
       			var token = response.message[0];
       	//		alert("token value "+token);
       			// Show progress dialog
       		/*$('#msgbox').text('Processing download...');
       			$('#msgbox').dialog( 
       					{	title: 'Download',
       						modal: true,
       						buttons: {"Close": function()  {
       							$(this).dialog("close");} 
       						}
       					});*/
       			
       			// Start download
       		window.location = '${downloadUrl}'+'?token='+token+'&type='+type+'&invoice='+$('#sid').val();
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
	function backToSalesReturnOrder(type) {
		$.get('salesReturnOrderList.html?flag=' + type, function(result) {
			//alert(result);
			$('.col2').html(result);
		});
	}
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

tfoot input {
	width: 60px;
	float: right;
	margin: 0px;
	display: inline-block;
}

table {
	word-wrap: break-word;
}

html {
	font-family: calibri;
	color: #736F69;
}

.display .submit {
	float: right;
}

.display .changed_items {
	font-size: 12px;
	vertical-align: middle;
}

.display .changed {
	font-weight: bold;
}

.display .changed input {
	background-color: lightgray;
	color: #fff;
	font-weight: bold;
	text-align: center;
}
</style>
</head>
<body>
	<div class="lineitems">

		<div>
			<a href="#" onclick="backToSalesReturnOrder('task_sub')">
				ReturnOrderList</a>&gt;&gt;Line Items List
		</div>


		<c:if test="${!empty salesReturnLineItems}">

			<h2 align="center" class="listcaption">Sales Returns Line Items
				List</h2>

			<table id="example1" class="display"
				style="text-align: center; border-color: grey;" border="1">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Product</th>
						<th>Amount</th>
						<th>Unit Price</th>
						<th>Discount</th>
						<th>Vat</th>
						<th>Return Price</th>
						<th>Return Quantity</th>



					</tr>

				</thead>
				<tbody>

					<%
						int i = 1;
					%>

					<c:set value="${sessionScope.user.id}" var="id" />

					<c:forEach items="${salesReturnLineItems}"
						var="salesOrderReturnLineItems">

						<tr id="<c:out value="${salesOrderReturnLineItems.id}"/>1">
							<td><%=i%></td>
							<td><c:out
									value="${salesOrderReturnLineItems.productInventoryBean.productBean.name}" /></td>

							<td><c:out value="${salesOrderReturnLineItems.amount}" /></td>
							<td><c:out value="${salesOrderReturnLineItems.unitPrice}" /></td>
							<td><c:out value="${salesOrderReturnLineItems.discount}" /></td>
							<td><c:out value="${salesOrderReturnLineItems.vat}" /></td>
							<td><c:out value="${salesOrderReturnLineItems.netPrice}" /></td>
							<td><c:out value="${salesOrderReturnLineItems.quantity}" />
							<input type="hidden" value="${salesOrderReturnLineItems.salesReturnOrderBean1.id}" id="sid"/>
							</td>



						</tr>

						<%
							i++;
						%>


					</c:forEach>
				</tbody>
				<c:set value="${sROID}" var="srOID"></c:set>
				<tfoot class="listfooter"><tr><td colspan="8" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty salesReturnLineItems}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/getSalesReturnLineItems.html?pg='+$(this).text()+'&sOId=${srOID}');
        });
    });
</script>

			<span style="margin-left: 60%;"><input type='button' value='Pdf' id='pdf'></input>
            <input type='button' value='Excel' id='excel' ></input></span></td></tr>         
			</tfoot>
			</table>
		</c:if>

		<c:if test="${empty salesReturnLineItems}"> Data is not available</c:if>


	</div>

</body>
</html>
