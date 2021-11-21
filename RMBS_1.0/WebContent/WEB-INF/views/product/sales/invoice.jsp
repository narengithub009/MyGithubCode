<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" charset="utf-8" src="js/print.js"></script>
<title>Sale's Order Bill</title>
<style>
table {
	word-wrap: break-word;
	border-spacing: 0px;
	font-style: Calibri;
	width: 100%;
}
.so_bill_header{
margin-bottom: 10px;
}

.so_bill_header_org_name {
	font-size: 18px;
	color: #346969;
	font-weight: bold;
	padding-top: 5px;
	padding-bottom: 5px;
	
}
.so_bill_header_shop_details{
margin-bottom: 5px;
margin-top: 5px;
}
.lable {
	color: #346969;
	font-weight: bold;
	font-size: 14px;
	padding-left: 3px;
	padding-right: 3px;
}
.t_C{
color: #346969;
	
}

.bill_print_content {
	border: #333333 1.5px dotted;
	background-color: #fff;
}

.so_cust_details {
	border: #333333 1.5px dotted;
	border-top: none;
	border-right: none;
	border-left: none;
	margin-top: 5px;
margin-bottom: 5px;
}

.so_bill_items thead tr td {
	border: #333333 1.5px dotted;
	padding-bottom: 3px;
	border-top: none;
	border-right: none;
	border-left: none;
}

.so_bill_items tr td {
	/*border:#333333 1.5px dotted;
border-top:none;
border-bottom:none;
*/
	
}

.so_bill_items_calc_content {
	border: #333333 1.5px dotted;
	border-bottom: :none;
	border-right: none;
	border-left: none;
}

.printLinks {
	width: 100%;
}

.printLinks {
	text-align: right;
	background-image: url(images/green.png);
	vertical-align: middle;
}

.printLinks .close ,.printLinks .close1{
	color: #FFF;
	font-weight: bold;
	padding: 6px;
}

.print {
	padding: 3px;
}
.col2{
min-height: 700px;
}
</style>
<script type="text/javascript">
	$(function() {
		//alert("hi");
		$(".print").attr("href", "javascript:void( 0 )").click(function() {
			//alert("inside print");
			$(".printable").print();
			return (false);
		});
	});
	$(function() {
		//alert("closing");
		$(".close").attr("href", "javascript:void( 0 )").click(function() {
			$(".orderLIst").replaceWith(divClone);
			$(".purchase_container").replaceWith(divClone);lineitems
			location.reload(true);
			return (false);
		});
	});
	$(function() {
		//alert("closing");
		$(".close1").attr("href", "javascript:void( 0 )").click(function() {
		//	$(".lineitems").replaceWith(divClone);
			location.reload(true);
			return (false);
		});
	});
</script>
</head>
<body class="bill">
	<div class="printLinks">
		<a class="print" title="Prin Invoice Copy"><img
			src="images/print2.png" alt="print" height="20px" width="20px" /></a>&nbsp;
			<c:choose>
			<c:when test="${flag=='SO'}"> <a class="close" title="Back">X</a></c:when>
			<c:otherwise><a class="close1" title="Back">X</a></c:otherwise>
			</c:choose>
			
			
	</div>
	<div class="printable">
		<div class="bill_print_content">
			<%-- <a href="#" onClick="window.print();" class=".print">Print</a>--%>
			
			<c:choose>
			<c:when test="${type=='model'}">
			<c:set var="order" value="${Order}" scope="page"></c:set>
			<c:set var="branch" value="${Order.branchModel}" scope="page"></c:set>
			<c:set var="branch_address" value="${Order.branchModel.resourceModel.addressModel}" scope="page"></c:set>
			<c:set var="organization" value="${Order.branchModel.organizationModel}" scope="page"></c:set>
			<c:set var="outlet" value="${Order.outletModel}" scope="page"></c:set>
			<c:set var="customer" value="${Order.customerModel}" scope="page"></c:set>
			<c:set var="customer_address" value="${Order.customerModel.addressModel}" scope="page"></c:set>
			<c:set var="doctor" value="${Order.doctorModel}" scope="page"></c:set>
			
			<c:set var="saved_Amount" value="${Order.discountPrice}" scope="page"></c:set>
			<c:set var="grand_Total" value="${Order.amount}" scope="page"></c:set>
			<c:set var="total_Vat" value="${Order.totalVAT}" scope="page"></c:set>
			<c:set var="pay_Amount" value="${Order.payAmount}" scope="page"></c:set>
			</c:when>
			<c:otherwise>
			<c:set var="order" value="${Order}" scope="page"></c:set>
			<c:set var="branch" value="${Order.branchBean}" scope="page"></c:set>
			<c:set var="branch_address" value="${Order.branchBean.resourceBean.addressBean}" scope="page"></c:set>
			<c:set var="organization" value="${Order.branchBean.organizationBean}" scope="page"></c:set>
			<c:set var="outlet" value="${Order.outletBean}" scope="page"></c:set>
			<c:set var="customer" value="${Order.customerBean}" scope="page"></c:set>
			<c:set var="customer_address" value="${Order.customerBean.addressBean}" scope="page"></c:set>
			<c:set var="doctor" value="${Order.doctorBean}" scope="page"></c:set>
			
			<c:set var="saved_Amount" value="${Order.discountPrice}" scope="page"></c:set>
			<c:set var="grand_Total" value="${Order.amount}" scope="page"></c:set>
			<c:set var="total_Vat" value="${Order.totalVAT}" scope="page"></c:set>
			<c:set var="pay_Amount" value="${Order.payAmount}" scope="page"></c:set>
			</c:otherwise>
			</c:choose>
			
			
			
			
			
			
			<table>
				<tr>
					<td class="so_bill"><table class="so_bill_header">
							<tr>
								<th align="center" class="so_bill_header_org_name"><c:out value="${organization.name}"></c:out></th>
							</tr>
							<tr>
								<td><table class="so_bill_header_shop_details">
										<tr>
											<td align="center"><span class="lable">Shop No:</span>
											<c:out value="${branch.id}"></c:out>
											<span class="lable">Address:</span>
											<c:out value="${branch_address.address}"></c:out></td>
										</tr>
									</table></td>
							</tr>
							<tr>
								<td><table>
										<tr>
											<td><span class="lable">DL No:</span></td>
											<td><span class="lable">Store Id:</span><c:out value="${branch.id}"></c:out></td>
											<td><span class="lable">Ph No:</span><c:out value="${branch_address.mobile}"></c:out></td>
											<td><span class="lable">Mail:</span><c:out value="${branch_address.email}"></c:out></td>
											<td><span class="lable">TIN No:</span><c:out value="${branch.tinNo}"></c:out></td>
										</tr>
									</table></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td><table class="so_bill_desc">
							<tr>
								<td align="left"><span class="lable">Tax Invoice No:</span><c:out value="${order.orderIdByDate}"></c:out></td>
								<td>&nbsp;</td>
								<td align="right"><span class="lable">Date:</span><c:out value="${order.billingDateAndTime}"></c:out></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td><table class="so_cust_details">
							<tr>
								<td class="lable">Name:</td>
								<td><c:out value="${customer.name}"></c:out></td>
							<%-- td class="lable">Age:</td>
								<td><c:out value="${customer}"></c:out></td>--%>	
								<td class="lable">Gender:</td>
								<td><c:out value="${customer.gender}"></c:out></td>
								<td class="lable">Doc:</td>
								<td><c:out value="${doctor.doctorName}"></c:out></td>
								<td class="lable">Doc.Reg.No:</td>
								<td><c:out value="${doctor.id}"></c:out></td>
								<td class="lable">Cus Id:</td>
								<td><c:out value="${customer.id}"></c:out></td>
								<td class="lable">Add:</td>
								<td><c:out value="${customer_address.address}"></c:out></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td><table class="so_bill_items">
							<thead>
								<tr>
									<td align="center" class="lable">S.No</td>
									<td align="left" class="lable">Product</td>
									<td align="left" class="lable">MFG</td>
									<td align="center" class="lable">SCH</td>
									<td align="left" class="lable">Batch</td>
									<td align="center" class="lable">Exp</td>
									<td align="right" class="lable">MRP</td>
									<td align="right" class="lable">Rate</td>
									<td align="right" class="lable">Qty</td>
									<td align="right" class="lable">Amount</td>
								</tr>
							</thead>
							<c:set var="count" value="0" scope="page"></c:set>
							<c:forEach items="${Items}" var="item">
								<tr>
									<c:set var="count" value="${count+1}"></c:set>
									<td align="center"><c:out value="${count}"></c:out></td>
									
									<c:choose>
										<c:when test="${type=='model'}">
										<td><c:out
											value="${item.productInventoryModel.productModel.name}"></c:out></td>
									<td><c:out
											value="${item.productInventoryModel.productModel.mFCompanay}"></c:out></td>
									<td align="center"><c:out
											value="${item.productInventoryModel.productModel.schDrug}"></c:out></td>
									<td><c:out value="${item.productInventoryModel.batchNo}"></c:out></td>
									<td align="center"><c:out
											value="${item.productInventoryModel.expiryDate}"></c:out></td>
									<td align="right"><c:out value="${item.unitPrice}"></c:out></td>
									<td align="right"><c:out value="${item.netPrice}"></c:out></td>
									<td align="right"><c:out
											value="${item.quantity}"></c:out></td>
									<td align="right"><c:out value="${item.amount}"></c:out></td>
										</c:when>
										<c:otherwise>
										<td><c:out
											value="${item.productInventoryBean.productBean.name}"></c:out></td>
									<td><c:out
											value="${item.productInventoryBean.productBean.mFCompanay}"></c:out></td>
									<td align="center"><c:out
											value="${item.productInventoryBean.productBean.schDrug}"></c:out></td>
									<td><c:out value="${item.productInventoryBean.batchNo}"></c:out></td>
									<td align="center"><c:out
											value="${item.productInventoryBean.expiryDate}"></c:out></td>
									<td align="right"><c:out value="${item.netPrice}"></c:out></td>
									<td align="right"><c:out value="${item.unitPrice}"></c:out></td>
									<td align="right"><c:out
											value="${item.quantity}"></c:out></td>
									<td align="right"><c:out value="${item.amount}"></c:out></td>
										</c:otherwise>
									</c:choose>
									



								</tr>
							</c:forEach>


						</table></td>
				</tr>
				<tr>
					<td><table class="so_bill_items_calc_content">
							<tr>
								<td><table  align="center"
										class="so_bill_items_save">
										<tr>
											<td align="left"><span class="lable">Amount Saved:</span></td>
											<td align="left" width="60%"><c:out value="${saved_Amount}"></c:out></td>
										</tr>
									</table></td>
								<td align="right" class="so_bill_items_vat">
								
								<%-- <c:out value="${vatDetails}"></c:out>--%>
								
								<c:forEach var="vat" items="${vatDetails}">
   									<%--Key: ${result.key} --%>	
  									 <div>${vat}</div>
								</c:forEach>
								</td>
								<td><table class="so_bill_items_calc">
										<tr>
											<td width="50%" align="right" class="lable">Grand Total:</td>
											<td width="50%" align="right"><c:out
													value="${grand_Total}"></c:out></td>
										</tr>
										<tr>
											<td align="right" class="lable">
											<c:choose>
											<c:when test="${flag=='SO'}">Pay Amount:</c:when>
											<c:otherwise>Return Amount:</c:otherwise>
											</c:choose>
											</td>
											<td align="right"><c:out value="${pay_Amount}"></c:out></td>
										</tr>
									</table></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td><table>
							<tr>
								<td><table>
										<tr>
											<td align="left" class="lable">Terms & Conditions:</td>
										</tr>
										<tr>
											<td class="t_C">1.Goods once sold will not taken back or exchanged.<br>
												2.Payments to be made within 15 days from date of invoice.<br>
											</td>
										</tr>
									</table></td>
								<td align="center" class="lable">AUTHORISED<br>SIGNATURE</td>
								<td align="center" class="lable">RECIEVER'S<br>SIGNATURE</td>
							</tr>
						</table></td>
				</tr>

			</table>
		</div>

	</div>
</body>
</html>
