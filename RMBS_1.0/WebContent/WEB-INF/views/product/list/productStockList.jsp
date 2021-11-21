
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/productStockReports/download.html" var="downloadUrl"/>
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
<%-- <script type="text/javascript" charset="utf-8" src="js/jquery1.7.2.js"></script>--%>
<script type="text/javascript" charset="utf-8"
	src="media/js/dataTables.js"></script>
<script type='text/javascript' src='<c:url value="/js/jquery-ui.min.js"/>'></script>
<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">

<script type="text/javascript" charset="utf-8">
           $(document).ready( function () {
                $('#example').dataTable( {
                    "bJQueryUI": true,
                  //  "sPaginationType": "full_numbers",   
                	   "sScrollY": "250px",
                	  "sScrollX": "110%",
                      "bPaginate": false,
               //		"sScrollYInner": "110%",
               		"bScrollCollapse": true,
      			  
                //  "sDom": '<"H"Tfr>t<"F"ip>',
                 //   "iDisplayLength": 10
                    } );
               
                   
                   
                 $('#pdf').click(function() {download('pdf');});
                 $('#excel').click(function() {download('xls');});                            
                 } );
           
           function download(type) {
      //     	  alert("hmmmmmmm calling "+type);
          		// Retrieve download token
          		// When token is received, proceed with download
          		$.get('${downloadTokenUrl}', function(response) {
          			// Store token
          			var token = response.message[0];
          	//		alert("token value "+token);
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
           
           function updateProductInventoryByQuantity(val,val1){
        	//   alert($('#qty').val());
        	   var data = {id:val,bid:val1,qty:$('#qty').val()};
        	   $.post('updateProductInventoryByQuantity.html',data,function(result){
        		   $("."+val+"1").html(result);
        		 
        	   });
           }
         function editProduct(val){
        		
        	//	alert(val);
        		var data=val;
        		$.post('getProductInventory.html?id='+val, data, function(result){
        	//	alert(result.id);        		
        			   productInventory='<table><tr><th>Product Name:</th><td>'+result.productBean.name+'</td></tr>',
        			   productInventory+='<tr><th>Category:</th><td>'+result.productBean.categoryBean.category+'</td></tr>',
        			   productInventory+='<tr><th>Sub Category:</th><td>'+result.productBean.subCategoryBean.subCategory+'</td></tr>',
             	          
        			   productInventory+='<tr><th>Branch:</th><td>'+result.branchBean.name+'</td></tr>',
        			   productInventory+='<tr><th>Price:</th><td>'+result.price+'</td></tr>',
        			   productInventory+='<tr><th>BatchNo:</th><td>'+result.batchNo+'</td></tr>',
        			   productInventory+='<tr><th>Agency:</th><td>'+result.agencyBean.agencyName+'</td></tr>',
        			   productInventory+='<tr><th>Quantity:</th><td><input type="text" id="qty" value="'+result.quantity+'"/></td></tr></table>';
          	           	      
        			 
        			//   productInventory+='</div>';
             	      if(result.id!=null){
             	    	  
             	    	link1='<a href="#" onclick="updateProductInventoryByQuantity('+result.id+','+result.branchBean.id+')">Update</a>';  
             	       $('.actionlink').html(link1);
             	       }else{
             	    	
             	    //	link2='<a href="#" onclick="updateOwner('+val+',1)">Deactivate</a>';
             	   //   $('.actionlink').html(link2);
             	       }             	                 	      
             	
             		$("#userdetails").html(productInventory);             		
             	//	$("#sample").show();
             	//	$("#sample").html(productInventory+link1);
			
        			 
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

#userdetails{
	margin: auto;
	witdh: 100%;
	background-image:url(images/listbackground1.jpg);
	}

    #userdetails th{
    font-weight: bold;
   border: none;
    
    }   
      #userdetails .userdetailscol {
       width: 56%;
       
        height:350px;overflow:auto;overflow-x:hidden;
    }
    #userdetails .userdetailsheader{
     font-weight: bold;
     font-size:16px;
     background-color: transparent;
   
     margin-top: 5px;
     width: 100%;
     height: 20px;
     text-align: center;
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
.content-wrapper .col1 {
	display: none;
}

.content-wrapper .col2 {
	width: 100%;
}
tfoot input{
	 width: 100px;
	
}
.page_nav{
background: white;
color: silver;
}
</style>
</head>
<body>
<div id="sample"></div>
	<jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>

	<c:if test="${!empty products}">
		<c:set value="${createdRole}" var="role"></c:set>
		<c:set value="${productCreatedBy}" var="cby"></c:set>
		<div id="editform"></div>

		<h2 align="center" class="listcaption">Product Stock List</h2>
		<table id="example" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>
					<th>Product Name</th>
					<th>Category</th>
					<th>Sub Category</th>
					<th>MFC</th>
					<th>Branch</th>	
					<th>Agency</th>			
					<th>Batch No</th>
					<th>Quantity</th>					
					<th>Price</th>
					<th>Amount</th>		
					<th>Expiry Date</th>
				<%--<th>Created Date</th> --%>	
				<%--<th>cby-cr-u-ur</th> --%>	
					<th>Action</th>				

				</tr>

			</thead>
			<tbody>
		
				<c:set value="${sessionScope.user.id}" var="id"  />
				<c:forEach items="${products}" var="productInventory" varStatus="counter">
					<tr>
						
						<td>${counter.count + subtractor}</td>	
						<td><c:out value="${productInventory.productBean.name}" /></td>
						<td><c:out value="${productInventory.productBean.categoryBean.category}" /></td>
						<td><c:out value="${productInventory.productBean.subCategoryBean.subCategory}" /></td>
						<td><c:out value="${productInventory.productBean.mFCompanay}" /></td>	
						<td><c:out value="${productInventory.branchBean.name}" /></td>
						<td><c:out value="${productInventory.agencyBean.agencyName}" /></td>
						<td><c:out value="${productInventory.batchNo}" /></td>
						<td class="<c:out value="${productInventory.id}"/>1"><c:out value="${productInventory.quantity}" /></td>	
						<td><c:out value="${productInventory.price}" /></td>
						<td><c:out value="${productInventory.price*productInventory.quantity}" /></td>		
						<td><c:out value="${productInventory.expiryDate}" /></td>
				<%--  <td><c:out value="${product.createdDate}" /></td>
						<td>
						<c:out value="${productInventory.branchBean.id}" />-
						<c:out value="${productInventory.branchBean.resourceBean.roleBean.id}" />-
						<c:out value="${cby}" />
						<c:out value="${role}" />
						</td>
						--%>
				  <td align="center">
						<c:if test='${productInventory.branchBean.id == cby && productInventory.branchBean.resourceBean.roleBean.id == role}'>
				<a href="#" title="Edit Product stock to update Quantity" class="topopup" onclick="editProduct(<c:out value="${productInventory.id}"/>);"><img src="images/edit.png" alt="edit" height="20px" width="20px"/></a>  
		 	 			</c:if>
						 <c:if test='${productInventory.branchBean.id != cby || productInventory.branchBean.resourceBean.roleBean.id != role}'>No Actions</c:if>
						</td> 		
					</tr>
					


				</c:forEach>
			</tbody>
				<tfoot class="listfooter"><tr><td colspan="8" style="text-align: center; ;" background="images/green.png">
				<c:if test="${not empty products}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/productStockList.html?pg='+$(this).text());
        });
    });
</script>
			<span style="margin-left: 60%;"><input type='button' value='Pdf' id='pdf'></input>
            <input type='button' value='Excel' id='excel' ></input></span></td></tr>
			</tfoot>
		</table>
		
	</c:if>

<c:if test="${empty products}"> Data is not available</c:if>
<div id='msgbox' title='' ></div>

</body>
</html>
