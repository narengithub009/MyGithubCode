<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/productMasterReports/download.html" var="downloadUrl"/>
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
                    "bPaginate": false,
                 //   "sPaginationType": "full_numbers",
                    //"sDom": '<"H"Tfr>t<"F"ip>',
                //  "iDisplayLength": 10,
                
                   
                } );
               // footerHTML="<input type='button' value='Pdf' id='pdf'></input>",
             //   footerHTML+="<input type='button' value='Excel' id='excel' ></input>";
              //   $('.dataTables_info').append(footerHTML);
                   
                   
                 $('#pdf').click(function() {download('pdf');});
                 $('#excel').click(function() {download('xls');});
           } );
           
           function download(type) {
         // 	  alert("hmmmmmmm calling "+type);
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
           
         function editProduct(val){
        		
        	//	alert(val);
        		var data=val;
        		$.post('getProduct.html?id='+val, data, function(result){
        		
        			 $("#editform").html(result);
        		});
        	}
           
         /*function updateDoctorStatus(val1,val2,val3){
            	
            //	alert(val1+"-"+val2+"-"+val3);
            	var data;
            	$.post('changeDoctorStatus.html?id='+val1+'&flag='+val2+'&uid='+val3, data, function(result){
            	
            		
            		$(".col2").html(result);
            	});
            }
            function updateDoctors(val1,val2){
            	
            	//alert(val1,val2);
            	var data=val1;
            	$.post('updateDoctors.html?uid='+val1+'&flag='+val2, data, function(result){
            	//	alert(result);
            		$(".col2").html(result);
            	});
            }

            function getDoctorsListByCategory(val1,val2){
            	
            	//alert(val1,val2);
            	var data=val1;
            	$.post('getDoctorssByCategory.html?uid='+val1+'&flag='+val2, data, function(result){
            	//	alert(result);
            		$(".col2").html(result);
            	});
            }*/
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
tfoot input{
	width: 100px;
	
}
.page_nav{
background: white;
}
.display th{
background-image: url(images/green.png);
color: #fff;
text-align: center;
font-weight: bold;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>

	<c:if test="${!empty products}">
		<c:set value="${createdRole}" var="role"></c:set>
		<c:set value="${productCreatedBy}" var="cby"></c:set>
		<div id="editform"></div>

		<h2 align="center" class="listcaption">Master Product List</h2>
		<table id="example" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>
					<th>Product Code</th>
					<th>Product Name</th>
					<th>Category</th>
					<th>Sub Category</th>
					<th>MFC</th>
					<th>SCH</th>
					<th>Created Date</th>
				<%--	<th>Action</th>
						<th>Email</th>	--%>

				</tr>

			</thead>
			<tbody>
				

				<c:set value="${sessionScope.user.id}" var="id" />


				<c:forEach items="${products}" var="product" varStatus="counter">

					<tr id="<c:out value="${product.id}"/>1">
						<td>${counter.count + subtractor}</td>						
						<td><c:out value="${product.code}" /></td>
						<td><c:out value="${product.name}" /></td>
						<td><c:out value="${product.categoryBean.category}" /></td>
						<td><c:out value="${product.subCategoryBean.subCategory}" /></td>
						<td><c:out value="${product.mFCompanay}" /></td>
						<td><c:out value="${product.schDrug}" /></td>
						<td><c:out value="${product.createdDate}" /></td>

							
					</tr>
					
				</c:forEach>
			</tbody>
			<tfoot class="listfooter"><tr><td colspan="8" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty products}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/masterProductsList.html?pg='+$(this).text());
        });
    });
</script>

			<span style="margin-left: 60%;"><input type='button' value='Pdf' id='pdf'></input>
            <input type='button' value='Excel' id='excel' ></input></span></td></tr>
         <%-- <tr><td><a href="">&lt;&lt;Pre</a></td><td colspan="7">1 2 3</td><td><a href="">Next&gt;&gt;</a></td></tr>--%>   	
			</tfoot>
		</table>
	
	</c:if>
<c:if test="${empty products}"> Data is not available</c:if>

<div id='msgbox' title='' ></div>
</body>
</html>
