
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/salesReturnsOrderListReports/download.html" var="downloadUrl"/>
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
                $('#pdf').click(function(){download('pdf');});
                $('#excel').click(function(){download('xls');});          
           });  
           
           function download(type) {
       //    	  alert("hmmmmmmm calling "+type);
          		// Retrieve download token
          		// When token is received, proceed with download
          		$.get('${downloadTokenUrl}', function(response) {
          			// Store token
          			var token = response.message[0];
        //  			alert("token value "+token);
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
          		window.location = '${downloadUrl}'+'?token='+token+'&type='+type;
          	//		alert("frequency"+token+" "+type);
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
           
           function salesReturnOrderIdBySalesLineItems(val1){
        	   var data = {sOId:val1};
        	   $.get('getSalesReturnLineItems.html', data, function(result){
        	 //    alert(result);
        		   $('.col2').html(result);
        	   });
           }//
           var divClone;
           function getInvoice(val){
        	   divClone = $(".returnOrderLIst").clone(); 
        	   var data = {sOId:val};
        	   $.post('loadInvoiceBySalesReturn.html', data, function(result){
        	  //   showInvoice(result);
        	  $(".returnOrderLIst").html(result); 
			  });
           }
           
      /*    function loadPreSalesIntoSalesForm(val){
        	  var data = {pSOId:val};
        	  $.post('loadPreSales.html', data, function(result){
            	  //   showInvoice(result);
            	  if(result){
            		  location.assign('${pageContext. request. contextPath}/openSalesOrderEntryForm.html?task_type=main&task_form_type=medical');
            	  }else{
            		  alert("Sorry..Some problem is occured...Check if have any incompleted sales order. If you have please complete that incompleted order first.");
            	  }
            //	  $(".orderLIst").html(result); 
    			  });
          }
           */
       
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
					<th>Customer Ph.No</th>					
					 <th>Action</th>


				</tr>

			</thead>
			<tbody>

			<c:set value="${sessionScope.user.id}" var="id" />

				<c:forEach items="${salesReturnOrders}" var="salesReturnOrder" varStatus="counter">
                
					<tr id="<c:out value="${salesReturnOrder.id}"/>1">
						<td>${counter.count + subtractor}</td>
						<td><c:out value="${salesReturnOrder.branchBean.name}" /></td>
						<td><c:out value="${salesReturnOrder.billingDateAndTime}" /></td>						
						<td><c:out value="${salesReturnOrder.orderIdByDate}" /></td>
						<td><c:out value="${salesReturnOrder.amount}" /></td>
						<td><c:out value="${salesReturnOrder.discountPrice}" /></td>
						<td><c:out value="${salesReturnOrder.totalVAT}" /></td>
						<td><c:out value="${salesReturnOrder.payAmount}" /></td>
						<td><c:if test="${salesReturnOrder.customerBean.addressBean.mobile!=0}">
						<c:out value="${salesReturnOrder.customerBean.addressBean.mobile}" />
						</c:if>
						<c:if test="${salesReturnOrder.customerBean.addressBean.mobile==0}">
							Contact No. is not available
						</c:if>
						</td>
						

				<td align="center"><a href="#" title="View Line Items"
							onclick="salesReturnOrderIdBySalesLineItems(<c:out value="${salesReturnOrder.id}"/>);"><img
								src="images/open1.jpg" alt="open" height="20px" width="20px" /></a>
								&nbsp;&nbsp;<a
							href="#" title="View Sales Return Order Invoice Print Copy"
							onclick="getInvoice(<c:out value="${salesReturnOrder.id}"/>);"><img
								src="images/print2.png" alt="print" height="20px" width="20px" /></a>
							
						</td>

					</tr>
					
					
		</c:forEach>
			</tbody>
			
			<tfoot class="listfooter"><tr><td colspan="8" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty salesReturnOrders}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/salesReturnOrderList.html?pg='+$(this).text()+'&flag=task_main');
        });
    });
</script>

			<span style="margin-left: 60%;"><input type='button' value='Pdf' id='pdf'></input>
            <input type='button' value='Excel' id='excel' ></input></span></td></tr>         
			</tfoot>
		</table>


	</c:if>

	<c:if test="${empty salesReturnOrders}"> Data is not available</c:if>
	<div id='msgbox' title='' ></div>
	
</div>
</body>
</html>
