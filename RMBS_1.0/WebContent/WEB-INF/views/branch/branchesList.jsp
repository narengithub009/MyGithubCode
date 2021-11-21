
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
       
        <link href="media/dataTables/demo_table_jui.css" rel="stylesheet" type="text/css" />
        <link href="media/themes/smoothness/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" />
         <script type="text/javascript" charset="utf-8" src="js/jquery1.7.2.js"></script>
        <script type="text/javascript" charset="utf-8" src="media/js/dataTables.js"></script>
        <link href="css/responsiveTable.css" rel="stylesheet"/>
        
          <script type="text/javascript" charset="utf-8">
           $(document).ready( function () {
                $('#example').dataTable( {
                    "bJQueryUI": true, 
                    "sScrollY": "100%",
                	//   "sScrollX": "100%",
                	"bScrollCollapse": true,
                	"bPaginate": false,
                //    "sPaginationType": "full_numbers",
                    //"sDom": '<"H"Tfr>t<"F"ip>',
               //     "iDisplayLength": 5,
                } );
           } );
           
       /*    function updateOwner(val,status){
        	  $.post('updateOwnerByAdmin.html', {owner:val,flag:status}, function(users){
        		  if(users!=null){
        			  for (i in users){
        			 alert(users[i].user.username);
        			 usersTableBodyHtml='<tr><td>'+(i+1)+'</td>',
        			 usersTableBodyHtml+='<td>'+users[i].user.username+'</td>',
        			 usersTableBodyHtml+='<td>'+users[i].user.address+'</td>',
        			 usersTableBodyHtml+='<td>'+users[i].user.mobile+'</td>',
        			 usersTableBodyHtml+='<td>'+users[i].user.email+'</td>',
        			 usersTableBodyHtml+='<td>'+users[i].user.active+'</td>',
        			 usersTableBodyHtml+='<td><a href="#" class="topopup" onclick="getUser('+users[i].user.id+');">Details</a></td></tr>';
        			  }
        			  $('#userslist').html(usersTableBodyHtml);
        		  
        		  }
        	  });
           }
           */
           function updateBranch(val,status){
         	  $.post('updateBranchStatusByAdmin.html', {branch:val,flag:status}, function(result){
         		  if(result!=null){
         		  $('#active'+val+'').html(result.resourceModel.accountStatusModel.status);
         		  }
         	  });
            }
           function getBranch(val){
        	 //  var data=val;
       
        	// alert(val);
        	  $.post('getBranch.html', {branch:val}, function(result){
        		//  alert(result);
        		   ownerHtml ='<table><tr><th>Branch Owner Name:</th><td>'+result.firstName+' '+result.lastName+'</td></tr>',
           	       ownerHtml+='<tr><th>Branch Name:</th><td>'+result.name+'</td></tr>',
           	       ownerHtml+='<tr><th>Address:</th><td>'+result.resourceModel.addressModel.address+'</td></tr>',
           	       ownerHtml+='<tr><th>Tinno:</th><td>'+result.tinNo+'</td></tr>',
           	    ownerHtml+='<tr><th>Registered Date & Time:</th><td>'+result.registeredDateTime+'</td></tr></table>';
        	      
        	       
        	       if(result.resourceModel.accountStatusModel.status=='INACTIVE'){
           	    	  
           	    	link1='<a href="#" onclick="updateBranch('+val+',0)">Activate</a>';  
           	       $('.actionlink').html(link1);
           	       }else{
           	    	
           	    	link2='<a href="#" onclick="updateBranch('+val+',1)">Deactivate</a>';
           	      $('.actionlink').html(link2);
           	       }
           	      
           	       $('#userdetails').html(ownerHtml);
           	      
           	});
           }
           
           
           
 </script>
<style>
	  .listcaption{
	background-image: url(images/green.png);
	color: #FFF;
	width:100%;
	font-weight: bold;
	float: left;
	}
	.actionlink a{
	text-decoration: none;
	background-color: lightblue;
	text-decoration: blink;
	padding: 5px;
	color: white;
	font-weight: bold;
	margin: 10px;
	float: right;
	}   
	a{
	text-decoration: none;
	}  
	#userdetails{
	background-image:url(images/listbackground1.jpg);
	}
	html{
font-family:calibri;
    color: #736F69;
}

.page_nav{
background: white;
color: silver;
}

.page_nav1{
background: white;
color: silver;
}


</style>
    </head>
    <body>
    <jsp:include page="/WEB-INF/views/general/popup.jsp"></jsp:include>
    <div id="bdy">
        
     <c:if test="${!empty branches}">
  	<c:set value="${outlet1}" var="outlet2"></c:set>
   <c:set value="${branch1}" var="branch"></c:set>
   <c:set value="${org}" var="organization"></c:set>
    
  <%-- Parent menu options: ${sessionScope.user.userMenu.parentmenu}<br>
     Child menu options: ${sessionScope.user.userMenu.childmenu} --%>   

  <h3 align="center" class="listcaption"> Branches List</h3>

        <div id="container" style="margin: auto;width:100%;">
            <div class="list">
                <table  id="example" class="display" style="text-align: center;border-color: grey;" border="1">
                   <thead>
                   	<tr>
                   		<th>Sl.No</th>
                   	 	<th>Branch Owner</th>
                   	 	<th class="optional">Address</th>
       			   	 	<th>Mobile</th>
        			 	<th>Email</th>
        			<%--<c:if test="${sessionScope.user.resourceBean.roleBean.id==organization}" >  --%> 	       			
         			 	<th>Activation Status</th>         			 	
         			 	<th>Action</th>
         			<%--	</c:if> --%> 
                   	</tr>
                   	 
        		   </thead>
        		   <tbody id="userslist"> 
        		  
        		  
        		   <c:forEach items="${branches}" var="branch" varStatus="counter">
        		  
        	
                    <tr>
                       <td>${counter.count + subtractor}</td>
                   		<td title="<c:out value="${branch.userName}"/>"><c:out value="${branch.userName}"/></td>
				       <td class="optional"><c:out value="${branch.resourceBean.addressBean.address}"/></td>
				        <td title="<c:out value="${branch.resourceBean.addressBean.mobile}"/>"><c:out value="${branch.resourceBean.addressBean.mobile}"/></td>
				        <td title="<c:out value="${branch.resourceBean.addressBean.email}"/>"><c:out value="${branch.resourceBean.addressBean.email}"/></td>
				       <%--    <td title="<c:out value="${branch.resourceBean.releBean.role}"/>"><c:out value="${branch.resourceBean.role}"/></td>--%>     
				  <%-- <c:if test="${sessionScope.user.resourceBean.roleBean.id==organization}" > --%>    
				        <td id="active${branch.id}" class="active"><c:out value="${branch.resourceBean.accountStatusBean.flag}"/></td>
				       	<td><a href="#" class="topopup" onclick="getBranch(${branch.id})">Details</a></td>
                  	<%--</c:if> --%>	
                   </tr>
                  
            
                
                 </c:forEach>
                 </tbody>
                  <tfoot class="listfooter"><tr><td  colspan="8" background="images/green.png" style="text-align: left;">			
			
				<c:if test="${not empty branches}">		
				
				<c:set value="${inactive}" var="inac"/>
				<c:set value="${status}" var="acntStatus"/>
				<c:if test="${acntStatus==inac}">
				<span class="page_nav1">
 					   ${pageNav}
				</span>				
				</c:if>
			<c:if test="${acntStatus!=inac}">
				<span class="page_nav">
 					   ${pageNav}
				</span>	
				</c:if>
			</c:if>

<script type="text/JavaScript">
    $(function(){
       $('.page_nav a').click(function(e) {
    //     alert($(this).text());
        location.replace('<%=request.getContextPath()%>/branchesList.html?pg='+$(this).text());
       });
        
        $('.page_nav1 a').click(function(e) {
      //      alert($(this).text());
        <%-- location.replace('<%=request.getContextPath()%>/loadAccountReguests.html?pg='+$(this).text());--%>
            $('#accountreq').load('<%=request.getContextPath()%>/loadAccountReguests.html?pg='+$(this).text());
        });
    });
</script>
   </td>
   </tr>
   </tfoot>  
        </table>
  </div>
</div>
</c:if>
<c:if test="${empty branches}"> Data is not available</c:if>

</div>
	
 </body>
</html>