<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p><h2 style="font-family:arial;color:red;font-size:20px;font-weight:bold;">Hi&nbsp;<c:out value="${sessionScope.user.firstName}"/>&nbsp;<c:out value="${sessionScope.user.lastName}"/></h2></p><br/><br/>
<%--
<p>Email From Return Model:-----<c:out value="${Logger}"/></p>
<p>Email From Session:-----<c:out value="${sessionScope.user.resourceBean.addressBean.email}"/></p>
 --%>
<p style="font-family:arial;color:blue;font-size:15px;font-weight:bold;">Status:-----<c:out value="${sessionScope.user.resourceBean.accountStatusBean.flag}"/></p>
<p style="font-weight:bold;"><c:if test="${sessionScope.user.resourceBean.accountStatusBean.id =='1'}" >
 Your activation is in pending ....
 <a href="paymentForm">Click here</a>&nbsp;to activate your account by payment.......
</c:if> </p>

</body>
</html>