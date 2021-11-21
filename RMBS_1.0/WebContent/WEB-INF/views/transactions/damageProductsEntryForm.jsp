<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>

<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<script type="text/javascript" src="js/tableSearch.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
 <script type="text/javascript" src="js/json2.js"></script> 
<link rel="stylesheet" type="text/css"
	href="css/masters/purchaseForm.css" />
<style type="text/css">
html {
	font-family: calibri;
	color: #736F69;
}

.valueBoxes div {
	padding-top: 4px;
	display: inline-block;
}

.valueBoxes label {
	min-width: 90px;
	padding-left: 0px;
	text-align: right;
}

.valueBoxes select {
	width: 155px;
}

.valueBoxes input {
	padding-right: 30px;
}

.valueBoxes {
	border: #3C873C 0.5px solid;
}


.submit_button {
	background: url("images/green.png") repeat scroll 0 0 transparent;
	float: right;
	color: white;
	font-size: 12px;
	font-weight: bold;
	margin-right: 10px;
}

.saveagency {
	display: none;
}

.remove {
	/*background: url("images/green.png") repeat scroll 0 0 transparent;*/
	margin: auto;
}

.wrapper {
	font-family: calibri;
	font-size: 15px;
	color: #736F69;
}

.wrapper input[type=text] {
	width: 140px;
}

.wrapper input[type=password] {
	width: 140px;
}

.wrapper label {
	width: 80px;
	display: inline-block;
}

.wrapper input[type=submit] {
	float: right;
	background: url("images/green.png") repeat scroll 0 0 transparent;
	border: none;
	color: #fff;
	font-weight: bold;
}

@media only screen and (max-width:750px) {
	.wrapper {
		width: 97%;
	}
}

@media only screen and (min-width:750px) and (max-width: 980px) {
	.wrapper {
		width: 90%;
		margin: auto;
	}
	.wrapper input[type=text] {
		width: 120px;
	}
}


.valueBoxes {
	margin-top: 10px;
}

.valueBoxes div {
	display: inline-block;
}

.valueBoxes label {
	min-width: 90px;
	padding-right: 0px;
	text-align: right;
}

.valueBoxes select {
	width: 155px;
}
#error{
	color:red;
	font-style: italic;
}
#info{
	color:green;
	font-style: italic;
}
#msg {
	float: right;
	font-weight: bold;
}

.valueBoxes input {
	padding-right: 30px;
}

.wrapper input[type="submit"] {
	float: right;
	margin: 10px;
}

/*.content-wrapper .col1 {
	display: none;
}

.content-wrapper .col2 {
	width: 100%;
}
*/

</style>
<script type="text/javascript">


function saveDamageProducts(){
	var data= $("#damageProductForm").serialize();
//	alert(data);

$.ajax({
		
		type:"POST",
		url:'saveDamageProducts.html',
		data:data,
		dataType:'application/json',
		success:function(response){
			var obj = JSON.parse(response);
				console.log(obj);
	  			if(obj.flag=="FAIL"){
	  				errorInfo="";
	  				for(i=0;i<obj.result.length;i++){
	  					errorInfo+="<br>"+(i+1)+". "+obj.result[i].code;
	  				}
	  				$('#error').html(errorInfo);
	  				$('#error').show().css({"border":"solid red 1px","margin":"5px"});
	  				$('#info').hide();
	  			}else{
	  				$('#info').html(obj.result);
	  				$('#error').hide();
	  				$('#info').show().css({"border":"solid green 1px","margin":"5px"});
	  				 clearFields(); 
	  			}
		},
		error:function(error){
			alert('error'+error);
		}
	});
}


function clearFields(){
	$(".valueBoxes input[type=text]").val("");
	$('#batNo option').remove();
 //   $('#brId').removeAttr("disabled");
	$("#catId").removeAttr("disabled");
	$("#piId").val("");
	$('#qntty').val("");			
//	$("#brId").focus();
	$("#reason").val("");
}




	
	var divClone;	
	$(document).ready(function() {
		 clearFields();	
/* 		 
	 $('#submit').click(function(){
		if($('#brId').val()==""){alert('Please fill the branch');$('#brId').focus();return false;}
		if($('#catId').val()==""){alert('Please fill the category');$('#catId').focus();return false;}
		if($('#pName').val()==""){alert('Please fill the product name');$('#pName').focus();return false;}
		if($('#batNo').val()==""){alert('Please fill the batch no');$('#batNo').focus();return false;}
		if($('#qntty').val()==""){alert('Please fill the quantity');$('#qntty').focus();return false;}
		if($('#expD').val()==""){alert('Please fill the expirydate');$('#expD').focus();return false;}
		if($('#reason').val()==""){alert('Please fill the reason');$('#reason').focus();return false;}
		
		 });
		  */
	/*	 $('#batNo').change(function(){
			 alert(this.value);
		 });*/
		 
		 $(".branch").html($("#brId :selected").text());
			$('#brId').change(function() {
				$(".branch").html($("#brId :selected").text());
			});
		 
		 
		      $('#pName').autocomplete({
					source: function (request, response) {
						var val=request.term;
				        $.getJSON('${pageContext. request. contextPath}/get_products_list.html', {
				            term: val,
				            type:$('#catId').val(),
				            bId:$("#brId").val(),
				            flag:"PI"
				        }, response);
				    },      
		   select: function( event, ui ) {
		            $( "#tags" ).val( ui.item.label );
		            $( "#pName" ).val( ui.item.value );
		            myArray= ui.item.value.split("_");
		            $( "#pId" ).val( myArray[2] );
		            $( "#pName" ).val( myArray[0] );
		            return false;
		        },
		     focus: function( event, ui ) {
		            $( "#tags" ).val( ui.item.label );
		            return false;
		        },
		     change: function(event, ui) {
		        if (!ui.item) {
     		       	 $("#tags").val("");
		            $("#pId").val("");
		            if($("#pDId").val()=="" || $("#pDId").val()==null){
		         //   clearFields();
		            }
		         }
		        else{
		       	 getProductDetails($.trim(myArray[2]));
		       
		            
		        }
		    }
		        
		     
		    });
		    
		function getProductDetails(val)
		 {
		 var brId=$("#brId").val();		
		 if(brId!=""){
		 var msg={"displaymsg":"Gathering Product Batches Information. Please Wait..."};

		
		  data={pid:val,bId:brId,Qty:0}; 
		 $.post('getBatches.html', data, function(result){
			 
		 	if(!jQuery.isEmptyObject(result)){
		 		
		 		$('#batNo option').remove();
		 		var o = new Option("--Select Batch--", "0");
		 		$(o).html("--Select Batch--");
		 		$("#batNo").append(o);	
		 		$.each(result, function(obj) {   
		 			var o = new Option("Batch No: "+result[obj].batchNo+" (Availability: "+result[obj].quantity+"pcs)", result[obj].id);
			 		$(o).html("Batch No: "+result[obj].batchNo+" (Availability: "+result[obj].quantity+"pcs)");
			 		$("#batNo").append(o);	
		 		});
		 	
		 	}

		     });
		 }else{
			 
			 $('#pName').val("");	
				$("#pId").val("");
			if(brId==0) {alert("Please Select The Branch"); $('#brId').focus();return false;}	
	
			
		   }
		 
		 }  
	
	});
     
	
	$(function() {
		$( "#expD" ).datepicker({
			dateFormat: 'yy-mm-dd',
			changeMonth: true,
			changeYear: true
		});
	});

	$(document).ready(function () {
		 
		 /*  $(".number").keypress(function (e) {
		//	  var myArray= $(this).attr('name').split(".");
	 //	$(".errmsg").html("");
		
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		     //   $("#"+myArray.pop()).html("Digits Only");
		        $(this).css("background-color", "rgb(245, 203, 203)");
	          $(this).css("border", "1px solid #FF0000");    
	          $(this).attr("placeholder", "Enter only numbers");
		               return false;
		    }else{
		    	 $(this).css("background-color", "#FFF");
		            $(this).css("border", "1px solid gray");    
		            $(this).removeAttr( "placeholder");
		    }
		   }); */
	});



</script>
</head>
<body>
		<div class="damageEntryForm">
			<h3>Damage Products Entry Form</h3>
			<div class="BranchDetails">
				&nbsp;Organization :${organization},&nbsp;&nbsp;&nbsp; Branch :<span
					class="branch"></span>
			</div>
			<div class="damageProduct">
				<div class="wrapper">
					<form:form method="POST" action="javascript:saveDamageProducts();" commandName="command" id="damageProductForm">
						<%-- <div id="msg" align="center">${message}</div> --%>
						<div class="valueBoxes">

							<div>
								<form:label path="branchBean.id">Branch</form:label>
								<form:select path="branchBean.id" id="brId">
									<form:option value="${branchId}">${branch}</form:option>
									<form:options items="${branches}" />
								</form:select>
							</div>
							<div>
								<form:label
									path="productInventoryBean.productBean.categoryBean.id">Category</form:label>
								<form:select
									path="productInventoryBean.productBean.categoryBean.id"
									id="catId">
									<form:option value="7">MEDICAL</form:option>
									<form:option value="8">GENERAL</form:option>
								</form:select>
							</div>
							
							<div>
								<form:label path="productInventoryBean.productBean.name">Product Name</form:label>
								<form:input path="productInventoryBean.productBean.name"
									id="pName" />
							</div>
							<div>
								<form:label path="productInventoryBean.id">Batch No</form:label>
								<form:select path="productInventoryBean.id" id="batNo">
									<form:option value="0">--Select--</form:option>
								</form:select>
							</div>

							<div>
								<form:label path="quantity">Quantity</form:label>
								<form:input path="quantity" id="qntty" cssClass="number"/>
							</div>
							

							<div>
								<form:label path="damagedDate">Damaged Date</form:label>
								<form:input path="damagedDate" id="expD" />
							</div>	
							<div>
								<form:label path="reasonBean.reason">Reason</form:label>
								<form:textarea path="reasonBean.reason" id="reason" />
							</div>			
							<div>
						<input type="submit" value="submit" id='submit' />
						
					</div>
						<div id="error"></div>
						<div id="info"></div>
						</div>

					</form:form>

					

				</div>
			</div>
		</div>
	
</body>
</html>