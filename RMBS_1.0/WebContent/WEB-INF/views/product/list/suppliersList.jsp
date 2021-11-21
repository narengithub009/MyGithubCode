
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
<%-- <script type="text/javascript" charset="utf-8" src="js/jquery1.7.2.js"></script> --%>
<script type="text/javascript" charset="utf-8"
	src="media/js/dataTables.js"></script>

<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">

<script type="text/javascript" charset="utf-8">
           $(document).ready( function () {
                $('#example').dataTable( {
                    "bJQueryUI": true,                     
                 "sPaginationType": "full_numbers",   
             	   "sScrollY": "200px",             	 
                   "bPaginate": false,
            //		"sScrollYInner": "110%",
            		"bScrollCollapse": true,
   			  
             //  "sDom": '<"H"Tfr>t<"F"ip>',
             //    "iDisplayLength": 10
                } );
               
                
                $('.dataTables_filter input').change(function(){
                	//highlightSearch(this.value);
                	
                });
                
           } );
           
           
       
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
	width: 120px;
    
	
}
.page_nav{
background: white;
color: silver;

}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>

	<c:if test="${!empty suppliers}">
		<c:set value="${createdRole}" var="role"></c:set>
		<c:set value="${productCreatedBy}" var="cby"></c:set>
		<div id="editform"></div>

		<h2 align="center" class="listcaption">Suppliers List</h2>		
		<table id="example" class="display"
			style="text-align: center; border-color: grey;" border="1">
			<thead>
				<tr>
					<th>S.No</th>					
					<th>Supplier</th>
					<th>Address</th>
					<th>Products(Available Quantity)</th>				
					

				</tr>

			</thead>
			<tbody>
			
				

				<c:set value="${sessionScope.user.id}" var="id" />

				<c:forEach items="${suppliers}" var="supplier" varStatus="counter">
			

					<tr id="<c:out value="${supplier.id}"/>1">
						<td>${counter.count + subtractor}</td>
						
						<td><c:out value="${supplier.supplierName}" /></td>
						
						<td>
						<c:out value="${supplier.addressBean.address}" /><br>
						<c:out value="${supplier.addressBean.mobile}" /><br>
						<c:out value="${supplier.addressBean.email}" />
						</td>
						<td>
						<c:forEach items="${supplier.productInventoryBeans}" var="productInventoryBean">
						<c:out value="${productInventoryBean.productBean.name}" />(<c:out value="${productInventoryBean.quantity}" />),<br> 
						</c:forEach>
						</td>
						
	
					</tr>
					

				
				</c:forEach>
			</tbody>
			<tfoot class="listfooter"><tr><td colspan="9" style="text-align: left;" background="images/green.png">
				<c:if test="${not empty suppliers}">
                   <span class="page_nav">
                         ${pageNav}
                   </span>
                </c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/suppliersList.html?pg='+$(this).text());
        });
    });
</script>
</td>
</tr>
</tfoot>
		</table>
		

	</c:if>

<c:if test="${empty suppliers}"> Data is not available</c:if>

</body>
</html>
