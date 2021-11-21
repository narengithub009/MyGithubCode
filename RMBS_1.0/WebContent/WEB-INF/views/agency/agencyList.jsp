
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

<link href="css/responsiveTable.css" rel="stylesheet" />

<script type="text/javascript" charset="utf-8">
           $(document).ready( function () {
                $('#example').dataTable( {
                    "bJQueryUI": true,
                    "sScrollY": "250px",
              //	   "sScrollX": "100%",
              	//	 "sScrollXInner": "100%",
            		"bScrollCollapse": true,
              	   "bPaginate": false,
                //    "sPaginationType": "full_numbers",
                    //"sDom": '<"H"Tfr>t<"F"ip>',
                //    "iDisplayLength": 5,
                } );
           } );
           
           
           
function editAgency(val){
	
	//alert(val);
	var data=val;
	$.post('editAgency.html?id='+val, data, function(result){
//	alert(result);
		 $(".agencyEditForm").html(result);
	});
}
function updateAgencyStatus(val1,val2,val3){
	
	//alert(val1,val2);
	var data=val1;
	$.post('changeAgencyStatus.html?id='+val1+'&flag='+val2+'&uid='+val3, data, function(result){
	//	alert(result);
		$(".col2").html(result);
	});
}
function updateAgencies(val1,val2){
	
	//alert(val1,val2);
	var data=val1;
	$.post('updateAgencies.html?uid='+val1+'&flag='+val2, data, function(result){
	//	alert(result);
		$(".col2").html(result);
	});
}

function getAgenciesListByCategory(val1,val2){
	
	//alert(val1,val2);
	var data=val1;
	$.post('getAgenciesByCategory.html?uid='+val1+'&flag='+val2, data, function(result){
	//	alert(result);
		$(".col2").html(result);
	});
}
</script>
<style type="text/css">
html{
font-family:calibri;
    color: #736F69;
}
.listcaption {
	background-image: url(images/green.png);
	color: #FFF;
	width: 100%;
	font-weight: bold;
	float: left;
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


table {
	word-wrap: break-word;
}

.page_nav{
background: white;
color: silver;
}

.listfooter input{
width: 100px;
}
</style>

</head>
<body>
	<jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>
	<div class="agencyEditForm"></div>
	<div id="list">

		<c:if test="${!empty agencies}">
			<c:set value="${sessionScope.user.id}" var="id" />
			<c:set value="${role}" var="role1" />
			<%
				int count = 0;
					int d = 0;
					int e = 0;
			%>


			<h2 align="center" class="listcaption">Agencies List</h2>
			<table id="example" class="display"
				style="text-align: center; border-color: grey;" border="1">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Name</th>
						<th>CST-NO</th>
						<th>DL-NO.1</th>
						<th>DL-NO.2</th>
						<th>Contact No</th>
						<th>Address</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<%-- <%	int i = 1;%>  --%>
					<c:forEach items="${agencies}" var="agency" varStatus="counter">
						<c:set value="${Delete}" var="Del" />

						<c:if test="${agency.resourceBean.accountStatusBean.id!=Del}">

							<tr id="<c:out value="${agency.id}"/>1">
								<%--<td><%=i%></td> --%>
								<td>${counter.count + subtractor}</td>
								<td><c:out value="${agency.agencyName}" /></td>
								<td><c:out value="${agency.cstNo}" /></td>
								<td><c:out value="${agency.dlNo1}" /></td>
								<td><c:out value="${agency.dlNo2}" /></td>
								<td><c:out
										value="${agency.resourceBean.addressBean.mobile}" /></td>
								<td><c:out
										value="${agency.resourceBean.addressBean.address}" /></td>
								<td><c:if test="${agency.resourceBean.createdBy== id && agency.resourceBean.creatorRoleBean.id == role1}">
										<c:set value="${Enable}" var="E" />
										<c:if test="${agency.resourceBean.accountStatusBean.id==E}">
											<a href="#" title="Edit Agency"
												onclick="editAgency(<c:out value="${agency.id}"/>);"><img
												src="images/edit.png" alt="edit" height="20px" width="20px" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"  title="Disable Agency"
												onclick="updateAgencyStatus(<c:out value="${agency.id}"/>,0,<c:out value="${agency.resourceBean.createdBy}"/>);"><img
												src="images/Delete.png" alt="delete" height="20px"
												width="20px" /></a>
											<%
												e++;
											%>
										</c:if>
										<c:set value="${Disable}" var="D" />
										<c:if test="${agency.resourceBean.accountStatusBean.id==D}">
											<a href="#" title="Enable Agency"
												onclick="updateAgencyStatus(<c:out value="${agency.id}"/>,2,<c:out value="${agency.resourceBean.createdBy}"/>);"><img
												src="images/correct.png" alt="edit" height="20px"
												width="20px" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" title="Delete Agency"
												onclick="updateAgencyStatus(<c:out value="${agency.id}"/>,1,<c:out value="${agency.resourceBean.createdBy}"/>);"><img
												src="images/pdelete1.png" alt="delete" height="30px"
												width="30px" /></a>
											<%
												d++;
											%>
										</c:if>
									</c:if> <c:if test="${agency.resourceBean.createdBy!= id || agency.resourceBean.creatorRoleBean.id != role1}">No Actions</c:if>
								</td>
							</tr>
							<%
								count++;
							%>
						<%-- <% i++; %>--%>	

						</c:if>
					</c:forEach>
				</tbody>
				<tfoot class="listfooter">
				
				
				<tr><td colspan="8">
				<c:if test="${not empty agencies}">
<span class="page_nav">
    ${pageNav}
</span>
</c:if>

<script type="text/JavaScript">
    $(function(){
        $('.page_nav a').click(function(e) {
         alert($(this).text());
         location.replace('<%=request.getContextPath()%>/agenciesList.html?pg='+$(this).text());
        });
    });
</script>
&nbsp;
				<%
					if (e != 0) {
				%>
				<input type="button" value="Activated <%=e %>"
					onclick="getAgenciesListByCategory(<c:out value="${sessionScope.user.id}"/>,'0');" />
				<%
					}
						if (d != 0) {
				%>
				<input type="button" value="Disabled <%=d %>"
					onclick="getAgenciesListByCategory(<c:out value="${sessionScope.user.id}"/>,'1');" />
				<%
					}
						if (count != 0) {
				%>
				<input type="button" value="Total"
					onclick="getAgenciesListByCategory(<c:out value="${sessionScope.user.id}"/>,'T');" />
				<%
					}
				%>
				Displayed&nbsp;<%=count%>
				<span> <%
 	if (d > 0) {
 %>Enable All&nbsp;<a href="#"  title="Enable all agencies"
					onclick="updateAgencies(<c:out value="${sessionScope.user.id}"/>,0);"><img
						src="images/correct.png" alt="edit" height="20px" width="20px"
						align="middle" /></a>&nbsp;&nbsp;<%
 	}
 %> <%
 	if (e > 0) {
 %>Disable All&nbsp;<a
					href="#"  title="Disable all agencies "
					onclick="updateAgencies(<c:out value="${sessionScope.user.id}"/>,1);"><img
						src="images/Delete.png" alt="edit" height="20px" width="20px"
						align="middle" /></a>&nbsp;&nbsp;<%
 	}
 %> <%
 	if (count > 0) {
 %>Delete All&nbsp;<a
					href="#"  title="Delete all agencies"
					onclick="updateAgencies(<c:out value="${sessionScope.user.id}"/>,2);"><img
						src="images/pdelete1.png" alt="edit" height="20px" width="20px"
						align="middle" /></a>&nbsp;&nbsp;<%} %>
				</span>				
</td></tr></tfoot>
			</table>
		<%--<div class="listfooter">
				&nbsp;
				<%
					if (e != 0) {
				%>
				<input type="button" value="Activated <%=e %>"
					onclick="getAgenciesListByCategory(<c:out value="${sessionScope.user.id}"/>,'0');" />
				<%
					}
						if (d != 0) {
				%>
				<input type="button" value="Disabled <%=d %>"
					onclick="getAgenciesListByCategory(<c:out value="${sessionScope.user.id}"/>,'1');" />
				<%
					}
						if (count != 0) {
				%>
				<input type="button" value="Total"
					onclick="getAgenciesListByCategory(<c:out value="${sessionScope.user.id}"/>,'T');" />
				<%
					}
				%>
				Displayed&nbsp;<%=count%>
				<span> <%
 	if (d > 0) {
 %>Enable All&nbsp;<a href="#"  title="Enable all agencies"
					onclick="updateAgencies(<c:out value="${sessionScope.user.id}"/>,0);"><img
						src="images/correct.png" alt="edit" height="20px" width="20px"
						align="middle" /></a>&nbsp;&nbsp;<%
 	}
 %> <%
 	if (e > 0) {
 %>Disable All&nbsp;<a
					href="#"  title="Disable all agencies "
					onclick="updateAgencies(<c:out value="${sessionScope.user.id}"/>,1);"><img
						src="images/Delete.png" alt="edit" height="20px" width="20px"
						align="middle" /></a>&nbsp;&nbsp;<%
 	}
 %> <%
 	if (count > 0) {
 %>Delete All&nbsp;<a
					href="#"  title="Delete all agencies"
					onclick="updateAgencies(<c:out value="${sessionScope.user.id}"/>,2);"><img
						src="images/pdelete1.png" alt="edit" height="20px" width="20px"
						align="middle" /></a>&nbsp;&nbsp;<%} %>
				</span>
			</div> --%>	

		</c:if>
		<c:if test="${empty agencies}"> Data is not available</c:if>
	</div>

</body>
</html>