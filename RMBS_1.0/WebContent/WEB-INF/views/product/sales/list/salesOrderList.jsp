
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/salesOrderListReports/download.html" var="downloadUrl"/>
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
                var sales = 'sales';
                var preSales = 'preSales';
                $('#pdf').click(function(){download("pdf",sales);});
                $('#excel').click(function(){download("xls",sales);});
                
                $('#pdf1').click(function(){download("pdf",preSales);});
                $('#excel1').click(function(){download("xls",preSales);});
           });  
           
           function download(type,salesType){
        	//   alert('hi '+type);
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
          			//var salesType = "sales";
          			// Start download
          		window.location = '${downloadUrl}'+'?token='+token+'&type='+type+'&task_type='+salesType;
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
           function salesOrderIdBySalesLineItems(val1,val2){
        	   var data = {sOId:val1,flag:val2,task:"sub"};
        	   $.get('getSalesLineItems.html', data, function(result){
        	 //    alert(result);
        		   $('.col2').html(result);
        	   });
           }
           
           var divClone;
           function getInvoice(val){
        	   divClone = $(".orderLIst").clone(); 
        	   var data = {sOId:val};
        	   $.post('loadInvoiceBySales.html', data, function(result){
        	  //   showInvoice(result);
        	  $(".orderLIst").html(result); 
			  });
           }
           
          function loadPreSalesIntoSalesForm(val){
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
tfoot input{
 
 float:right;
 width:70px;
margin: 0px;

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
	<c:forEach items="${salesOrders}" var="salesOrder">	
<c:set var="ctype" value='${salesOrder.categoryBean}' scope="page"></c:set>
</c:forEach>

	<c:if test="${!empty salesOrders}">
		<c:set value='${sale}' var = 'sales'></c:set>
		<c:set value='${preSale}' var = 'preSales'></c:set>
		<c:set value='${outlet}' var = 'outlets'></c:set>
		
	
		<c:if test="${ctype.id == sales}">
		<h2 align="center" class="listcaption">Sales Order List</h2>
		</c:if>
		<c:if test="${ctype.id == preSales}">
		<h2 align="center" class="listcaption">Pre Sales Order List</h2>
		</c:if>
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
					<th>Net Amount</th>	
					<th>Customer Ph.No</th>					
					 <th>Action</th>


				</tr>

			</thead>
			<tbody>

				<c:set value="${sessionScope.user.id}" var="id" />

				<c:forEach items="${salesOrders}" var="salesOrder" varStatus="counter">
				<c:set value="${salesOrder}" var="salesOrder" scope="page"/>
                
					<tr id="<c:out value="${salesOrder.id}"/>1">
						<td>${counter.count + subtractor}</td>
						<td><c:out value="${salesOrder.branchBean.name}" /></td>
						<td><c:out value="${salesOrder.billingDateAndTime}" /></td>						
						<td><c:out value="${salesOrder.orderIdByDate}" /></td>
						<td><c:out value="${salesOrder.amount}" /></td>
						<td><c:out value="${salesOrder.discountPrice}" /></td>
						<td><c:out value="${salesOrder.totalVAT}" /></td>
						<td><c:out value="${salesOrder.payAmount}" /></td>
						<td><c:if test="${salesOrder.customerBean.addressBean.mobile!=0}">
						<c:out value="${salesOrder.customerBean.addressBean.mobile}" />
						</c:if>
						<c:if test="${salesOrder.customerBean.addressBean.mobile==0}">
							Contact No. is not available
						</c:if>
						</td>
						

					<td align="center"><a href="#" title="View Line Items"
							onclick="salesOrderIdBySalesLineItems(<c:out value="${salesOrder.id}"/>,0);"><img
								src="images/open1.jpg" alt="open" height="20px" width="20px" /></a>
								&nbsp;&nbsp;<a
							href="#" title="View Sales Order Invoice Print Copy"
							onclick="getInvoice(<c:out value="${salesOrder.id}"/>);"><img
								src="images/print2.png" alt="print" height="20px" width="20px" /></a>
								<c:if test="${salesOrder.categoryBean.id==preSales && sessionScope.user.resourceBean.roleBean.id==outlets}">
								&nbsp;&nbsp;<a
							href="#" title="Open Sales Form"
							onclick="loadPreSalesIntoSalesForm(<c:out value="${salesOrder.id}"/>);"><img
								src="images/open.png" alt="print" height="20px" width="20px" /></a>
							</c:if>
								<c:if test="${salesOrder.categoryBean.id==sales && sessionScope.user.resourceBean.roleBean.id==outlets}">
								&nbsp;&nbsp;<a
							href="#" title="View Sales Return Line Items List"
							onclick="salesOrderIdBySalesLineItems(<c:out value="${salesOrder.id}"/>,1);"><img
								src="images/SRO.png" alt="print" height="25px" width="25px" /></a>
							</c:if>
						</td>

					</tr>
				

				</c:forEach>
			</tbody>
			<c:if test="${ctype.id == sales}">
		<%-- <tfoot>
				<tr><td colspan="9">
				<input type="button" value="Pdf" id="pdf"/></td>
			<td>	<input type="button" value="Excel" id="excel"/>
				</td></tr>
			</tfoot>--%>	
			<tfoot class="listfooter"><tr><td colspan="8" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty salesOrders}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/salesOrderList.html?pg='+$(this).text()+'&flag=task_main'+'&task_type=sales');
        });
    });
</script>

			<span style="margin-left: 60%;"><input type='button' value='Pdf' id='pdf'></input>
            <input type='button' value='Excel' id='excel' ></input></span></td></tr>     
			</tfoot>
			</c:if>
			
			<c:if test="${ctype.id == preSales}">
	<%--<tfoot>
				<tr><td colspan="9">
				<input type="button" value="Pdf" id="pdf1"/></td>
			<td>	<input type="button" value="Excel" id="excel1"/>
				</td></tr>
			</tfoot> --%>		
			<tfoot class="listfooter"><tr><td colspan="8" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty salesOrders}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/salesOrderList.html?pg='+$(this).text()+'&flag=task_main'+'&task_type=preSales');
        });
    });
</script>

			<span style="margin-left: 60%;"><input type='button' value='Pdf' id='pdf'></input>
            <input type='button' value='Excel' id='excel' ></input></span></td></tr>        
			</tfoot>
			</c:if>
		</table>


	</c:if>

	<c:if test="${empty salesOrders}"> Data is not available</c:if>
	<div id='msgbox' title='' ></div>
	
</div>
</body>
</html>
