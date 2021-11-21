
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/purchaseOrderLineitemsReports/download.html" var="downloadUrl"/>
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
           $(document).ready( function () {
                $('#example').dataTable( {
                    "bJQueryUI": true,    
                    "sScrollY": "300px",
             //	   "sScrollX": "110%",
             	   "bScrollCollapse": true,
             	  "bPaginate": false,
                  //  "sPaginationType": "full_numbers",
                    //"sDom": '<"H"Tfr>t<"F"ip>',
              //      "iDisplayLength": 10,
                } );
                footerHTML="<input type='button' value='Pdf' id='pdf'></input>",
                footerHTML+="<input type='button' value='Excel' id='excel' ></input>";
                 $('.dataTables_info').append(footerHTML);
                   
                   
                 $('#pdf').click(function() {download('pdf');});
                 $('#excel').click(function() {download('xls');});
                 
                 
                 //**************************
                 
                 
                 
         	   $(".submit").click(function(){
             	   //alert("hi");
             	   if(!jQuery.isEmptyObject(map)){
        				objs=[];
        				 Object.keys(map).forEach(function (key) {
        			
        				    alert(map[key]['id']);
        				    objs.push(map[key]);
        			});
        				 var data= JSON.stringify(objs);
                    //	  alert('data '+data);

             	//   objs.push(map[key]);
             	  divClone = $(".lineitems").clone(); 
             	//  alert('divClone '+divClone);
             	   $.post('createPurchasereturnOrder.html', data, function(result){
          		$(".lineitems").html(result);
             		//   alert('result '+result);
          		  });
             	   
             	   }  
                });
                 
                 $(".qty,#id").change(function () {
                	 var qty = this.value;
                	 var id = this.id;
                	 var data = {qty:qty,id:id};
							
                	 $.ajax({
                   		type : "POST",
                   		url  : "productQuantityCheck.html",
                   		data : data,
                   		success : function(msg){
                   			var aviQty=msg[0];
                   			var id1=msg[1];
                   		//	alert("aviQty "+aviQty);
                   		//	alert("id1 "+id1);
                   		//	alert("qtttty "+qty);

                   			if(aviQty>=qty&&qty!=0){
                   				
                   			//alert(this.value);
                             	//alert(this.id);
                             	//alert($('#'+this.id).attr('name'));
                             	val2=$('#'+id1).attr('name');
                             	//alert(isInteger(this.value));
                          	 //  var flag=isInteger(this.value);
                          //	   alert('val2 *******'+val2);
                          	   if((/^[0-9]+$/.test(qty))){
                         		   if(this.value>parseInt(id1)){
                         			   console.log("Entered into this.value>parseInt(val2)");
                         			   console.log("Entered into "+qty+">"+parseInt(id1)+"");
                          			  //this.value=val2;
                         			/*   if(!jQuery.isEmptyObject(map)){
                                			var keys=Object.keys(map);
                                    		if($.inArray(this.id, keys) > -1){
                                    			
                                    			delete map[this.id];
                                    			var id=this.id+"1";
                                    			jQuery('#'+id).removeClass('changed');
                                    		}
                                    		print_changes();
                                   		 }*/
                                   		popItem(id1);
                         			   qty=0;
                         		   }else if(qty<0){
                                     	console.log("Entered into this.value<0");
                                     	qty=0;
                                     	return false;
                                     }else if(qty!=0 && qty!=""){
                                     	console.log("Entered into map creation");
                                     	//alert("Entered into map creation");
                                     	var obj = {
                                 				"id":id1,
                                 				"qty":qty
                                     	};
                                     	generate_PRO_Obj(obj,id1);
                                     	console.log(map);
                                     	if(!jQuery.isEmptyObject(map)){
                                 			var keys=Object.keys(map);
                                 			//alert("keys.length()-"+keys.length);
                                 		//alert(keys);
                                 			keys.forEach(function (key) {
                                 					// var temp=map[key]['qty'];
                                 					 //alert(temp);
                                 					});
                                 			print_changes();
                                 			}
                                     	//.changed_items Object.keys(a).length;
                                     	
                                     	return true;
                                     }else{
                                     	if(!jQuery.isEmptyObject(map)){
                                 			var keys=Object.keys(map);
                                     		if($.inArray(id1, keys) > -1){
                                     			delete map[id1];
                                     			var id=this.id+"1";
                                     			jQuery('#'+id).removeClass('changed');
                                     		}
                                     		print_changes();
                                    		 }
                                     };
                                     
                          	   }else if(qty==0 && qty!=""){
                          		 // alert("Entered 0 section");
                          		/* if(!jQuery.isEmptyObject(map)){
                          			var keys=Object.keys(map);
                              		if($.inArray(this.id, keys) > -1){
                              			
                              			delete map[this.id];
                              			var id=this.id+"1";
                              			jQuery('#'+id).removeClass('changed');
                              		}
                              		print_changes();
                              		console.log(map);
                             		 }*/
                          		  popItem(id1);
                          	   }else{
                          		  
                          		  qty=0;
                          		  return false;
                          	   }
                   			}
                   			else{
                   				alert("Quantity not available");
                   				$('#'+id1).val("You can return only "+aviQty+"prod..");
                   			//	$('#'+id1).attr("disabled","disabled");
                   			}
                   		}
                	 });
                   	});
                 	
            } );
            var map={};
            function generate_PRO_Obj(obj,key){
         	   map[key]=obj;  
         	   
         	 }
          function popItem(val){
         	 if(!jQuery.isEmptyObject(map)){
       			var keys=Object.keys(map);
           		if($.inArray(val, keys) > -1){
           			
           			delete map[val];
           			var id=val+"1";
           			jQuery('#'+id).removeClass('changed');
           		}
           		print_changes();
           		console.log(map);
           	//	alert('map '+map);
          		 }
          }
          function applayCss(){
         	 if(!jQuery.isEmptyObject(map)){
       			var keys=Object.keys(map);
       			keys.forEach(function (key) {
       				
       				var id=key+"1";
       				/*//alert(id);
       				(id).addClass("changed");*/
       				jQuery('#'+id).addClass('changed');
       			});
         	 }
          }
            
            function print_changes(){
         	   applayCss();
         	   $(".changed_items").html(Object.keys(map).length+" Itme(s) Changed.");   
            }
            
         
           
           function download(type) {
        	//   alert("invoice name "+$('#POINV').val());
           // 	  alert("hmmmmmmm calling "+type);
           		// Retrieve download token
           		// When token is received, proceed with download
           		$.get('${downloadTokenUrl}', function(response) {
           			// Store token
           			var token = response.message[0];
           		//	alert("token value "+token);
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
           		window.location = '${downloadUrl}'+'?token='+token+'&type='+type+'&invoice='+$('#POINV').val();
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
           
           function backToPurchaseOrder(flag){        	 
        	//   alert('order id '+flag);
        	
        	   $.get('purchaseOrderList.html?flag='+flag, function(result){
        	//	   alert(result);
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

.display{
word-wrap: break-word;
}
.display th{
background-image: url(images/green.png);
color: #fff;
text-align: center;
font-weight: bold;
}
   .content-wrapper .col1 {
display: none;
}
.display .submit{
float: right;
}
.content-wrapper .col2 {
width:100%;
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
<div><a href="#" title="Back to Purchase Order List" onclick="backToPurchaseOrder('task_sub')">OrderList</a>&gt;&gt;Line Items List</div>
	<jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>

	<c:if test="${!empty purchaseLineItems}">
	
	<%--<c:set value="${createdRole}" var="role"></c:set>
		<c:set value="${lineItemCreatedBy}" var="cby"></c:set> --%>	
		<c:set value="${purchaseOrderId}" var="pOID"></c:set>
		
		<div id="editform"></div>
		
		<h2 align="center" class="listcaption">
		<c:if test="${flag==0}">Purchase Line Items List</c:if>
		<c:if test="${flag==1}">Purchase Return Line items List</c:if>
		</h2>
		<table id="example" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>
					<th>Product</th>
					<th>Quantity</th>
					<th>Free Quantity</th>								
					<th>Amount</th>
					<th>Unit Price</th>
					<th>Discount</th>
					<th>Vat</th>		
					<th>Net Price</th>
					<th>Pay Amount</th>
					
					<%-- <th>Preparation Charges</th>--%>		
					<th>Expiry Date</th>
					<th>Invoice No</th>
						<c:if test="${flag==1}">
						
					<th>Returnable Qty</th>	
					<th>Rtn Qty</th>
					</c:if>

				</tr>

			</thead>
			<tbody>
			
				

				<c:set value="${sessionScope.user.id}" var="id" />
				        	  
				
				<c:forEach items="${purchaseLineItems}" var="purchaseLineItem" varStatus="counter">			
					<c:if test="${flag==0}">
					<tr id="<c:out value="${purchaseLineItem.id}"/>1">
						<td>${counter.count + subtractor}</td>
						<td><c:out value="${purchaseLineItem.productInventoryBean.productBean.name}" /></td>
						<td><c:out value="${purchaseLineItem.quantity}" /></td>
						<td><c:out value="${purchaseLineItem.freeQantity}" /></td>
						<td><c:out value="${purchaseLineItem.amount}" /></td>
						<td><c:out value="${purchaseLineItem.unitPrice}" /></td>
						<td><c:out value="${purchaseLineItem.discount}" /></td>
						<td><c:out value="${purchaseLineItem.vat}" /></td>
						<td><c:out value="${purchaseLineItem.netPrice}" /></td>
						<td><c:out value="${purchaseLineItem.payAmount}" /></td>						
					<%--	<td><c:out value="${purchaseLineItems.preparationCharges}" /></td>--%>
						<td><c:out value="${purchaseLineItem.expiryDate}" /></td>
						<td><c:out value="${purchaseLineItem.purchaseOrderBean1.invoiceNo}" />
						<input type="hidden" id="POINV" value="${purchaseLineItem.purchaseOrderBean1.invoiceNo}"/>
						</td>
			
					</tr>
					</c:if>
					<c:if test="${flag==1}">
					<tr id="<c:out value="${purchaseLineItem.id}"/>1">
						<td>${counter.count + subtractor}</td>
						<td><c:out value="${purchaseLineItem.productInventoryBean.productBean.name}" /></td>
						<td><c:out value="${purchaseLineItem.quantity}" /></td>
						<td><c:out value="${purchaseLineItem.freeQantity}" /></td>
						<td><c:out value="${purchaseLineItem.amount}" /></td>
						<td><c:out value="${purchaseLineItem.unitPrice}" /></td>
						<td><c:out value="${purchaseLineItem.discount}" /></td>
						<td><c:out value="${purchaseLineItem.vat}" /></td>
						<td><c:out value="${purchaseLineItem.netPrice}" /></td>
						<td><c:out value="${purchaseLineItem.payAmount}" /></td>						
					<%--	<td><c:out value="${purchaseLineItems.preparationCharges}" /></td>--%>
						<td><c:out value="${purchaseLineItem.expiryDate}" /></td>
						<td><c:out value="${purchaseLineItem.purchaseOrderBean1.invoiceNo}" />
						<input type="hidden" id="POINV" value="${purchaseLineItem.purchaseOrderBean1.invoiceNo}"/>
						<td><c:out value="${purchaseLineItem.deliverableQuantity}" /></td>
						<td>
						<c:choose>
						<c:when test="${purchaseLineItem.deliverableQuantity==0}">This item Qty is not available in this order to return</c:when>
						<c:otherwise><input type ="text" name="<c:out value="${purchaseLineItem.deliverableQuantity}" />" class="qty"<c:out value="${purchaseLineItem.id}"/> name="" value="0" id="<c:out value="${purchaseLineItem.id}"/>"/></c:otherwise>
						</c:choose>
						</td>
					
			
					</tr>
					</c:if>
					

				
				</c:forEach>
			</tbody>
			<c:if test="${flag==1}">
			<tfoot><tr><td colspan="2"><span class="changed_items"></span></td><td colspan="7"><button class="submit">Submit</button></td></tr></tfoot>
			</c:if>
			<c:if test="${flag==0}">
			
			<tfoot class="listfooter"><tr><td colspan="9" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty purchaseLineItems}">
				   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>
		
<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/getPurchaseLineItems.html?pg='+$(this).text()+'&flag=0'+'&id=${pOID}'+'&task=page');  
         
        });
    });
</script>
</td>
</tr>
</tfoot>
</c:if>
		</table>
		

	</c:if>
<c:if test="${empty purchaseLineItems}"> Data is not available</c:if>

<div id='msgbox' title='' ></div>
</div>
</body>
</html>
