<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
   		<link rel="stylesheet" type="text/css" href="css/masters/productentryform.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<link rel="stylesheet" href="css/tabs/easy-responsive-tabs.css">
		<script src="js/tabs/easyResponsiveTabs.js"></script>
	<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="http://mydomain.com/xxxx.js"></script>	
<style>
        .demo {
        
            margin: 0px auto;
        }
        .demo h1 {
                margin:33px 0 25px;
            }
        .demo h3 {
                margin: 10px 0;
            }
        pre {
            background: #fff;
        }
        @media only screen and (max-width: 780px) {
        .demo {
                margin: 5%;
            
         }
        .how-use {
                float: left;
                width: 300px;
                display: none;
            }
        }
        #tabInfo {
            display: none;
        }
        .content-wrapper .col1 {
display: none;
}

.content-wrapper .col2 {
width:100%;
}
    </style>
    <script type="text/javascript">
    
    function loadProductStock(){
    	alert("loadProductStock called");
    	
    	}
    	
    function loadProductLog(){ 
    //	alert("loadProductLog called");
    	msg="Please wait...  Form is loading...";
    //	loadAjaxPopup(msg);
    	$.get("productLogList.html", function(list){
    		//alert(medical_form);
    		$(".productLog").html(list);
    		});
  //  	disableAjaxPopup();
    	}
    </script>
</head>
<body>
    <div class="demo">
          
     
        
        <!--Horizontal Tab-->
        <div id="horizontalTab">
            <ul class="resp-tabs-list">
                <li onclick="loadProductStock();">Product Stock List</li>
                <li onclick="loadProductLog();">Product Log List</li>
            </ul>
            
            <div class="resp-tabs-container">
                <div class="productStock">
                    <p><jsp:include page="/WEB-INF/views/product/list/productStockList.jsp"></jsp:include></p>
                </div>
                <div class="productLog">
                    <p></p>
                </div>
            
            </div>
        </div>
        <br />

        <div id="tabInfo">
            Selected tab: <span class="tabName"></span>
        </div>

   
        </div>

</body>
<script type="text/javascript">
    $(document).ready(function () {
        $('#horizontalTab').easyResponsiveTabs({
            type: 'default', //Types: default, vertical, accordion           
            width: 'auto', //auto or any width like 600px
            fit: true,   // 100% fit in a container
            closed: 'accordion', // Start closed if in accordion view
            activate: function(event) { // Callback function if tab is switched
                var $tab = $(this);
                var $info = $('#tabInfo');
                var $name = $('span', $info);

                $name.text($tab.text());

                $info.show();
            }
        });

       
    });
</script>
</html>
