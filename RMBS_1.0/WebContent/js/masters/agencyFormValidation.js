function addAgency()
{
var data= $("#RegisterAgency").serialize();
//alert(data);
$.ajax({
	type:"POST",
	url:'addAgency.html?flag=0',
	data:data,
	dataType:'application/json',
	success:function(response){
		var obj = JSON.parse(response);
		if(obj.flag=="FAIL"){
			errorInfo="";
			for(i=0;i<obj.result.length;i++){
				errorInfo+="<br>"+(i+1)+". "+obj.result[i].code;
			}
			$('#errorInfo').html(errorInfo);
			$('#errorInfo').show().css({"border":"red solid 1px","margin":"5px"});
			$('#info').hide();
		}else{
			$('#info').html(obj.result);
			$('#errorInfo').hide();
			$('#info').show().css({"border":"red solid 1px","margin":"5px"});
			clearVals();
		}
	},error:function(e){
		alert("error "+e);
	}
});
}

function clearVals(){
	 $('#aname').val('');
	$('#add').val('');
		$('#phno').val('');
		$('#cstNo').val('');
		$('#dlNo1').val('');
		$('#dlNo2').val('');
		$('#email').val('');		
	
}


$(document).ready(function() {
	
$('#RegisterAgency').submit(function(){
		if($('#aname').val()==""){alert('Please fill the agency name');$('#aname').focus();return false;}
		if($('#cstNo').val()==""){alert('Please fill the agency CSTNO');$('#cstNo').focus();return false;}
		if($('#dlNo1').val()==""){alert('Please fill the agency dlNo1');$('#dlNo1').focus();return false;}
		if($('#dlNo2').val()==""){alert('Please fill the agency dlNo2');$('#dlNo2').focus();return false;}	
		if($('#phno').val()==""){alert('Please fill the agency phone number');$('#phno').focus();return false;}
		if($('#email').val()== ""){alert("Please enter your mail id for future refference");$('#email').focus().css("borderColor","red");return false;}
		if($('#add').val()==""){alert('Please fill the agency address');$('#add').focus();return false;}
		$(this).find('input:text').each(function(){
        	
        //	alert($.trim($(this).val()));
              $(this).val($.trim($(this).val()));
        });
  });
	
$('#aname').change(function(e) {
	var aname=$('#aname').val();
	var id=$('#createdBy').val();
	var data=null;
	$.post('validateagency.html?agency='+aname+'&uid='+id, data, function(result){	
		//alert(result);
		if(result!="E"){
			$('#msg').html(""+result+" agency already existed").css("color","green").css("font-weight","bold");
			$('#aname').val("").focus();
		}else{
			
			$('#add').focus();
			$('#msg').html("");
		}
	});
	
});
	
	$('.names').keyup(function(e) {

        var letters = /^[A-Za-z0-9\s]+$/;
        var c=this.value;
         var v=c.charAt(c.length - 1);
         if(v==" " && c.length > 1 ){
              if(c.charAt(c.length - 1)==" " && c.charAt(c.length - 2)==" "){
             	 var count=0;
             //	 var formatted=c;
             	 for (var i=0;i<c.length;i++){
  	                var d1=c.charAt(c.length - i+1);
  	              //  alert("1st"+d1);
  	                var d2=c.charAt(c.length - i+2);
  	              // alert("2nd"+d2);
  	                if(d1==" " && d2==" "){
  	                //	formatted = c.substring(0,i); 
  	                count+=1;
  	                 }
  	               }//alert(count);
  	             //  alert("***"+c.substring(0, c.length - count)+"******");
  	              $(this).val(c.substring(0, c.length - count)); 
              
                 return true;
               }
          }else{
              if(v==" " && c.length == 1 ){
               c = c.substring(0, c.length - 1);
               $(this).val(c.substring(0, c.length - 1)); 
               
               return false;
                }
           }
        if(!c.match(letters)) 
       {
           if(c.length > 0){
        alert('Special characters not allowed');
           }
           
        $(this).val("");
       
         return true;  
        };
        return true;

});

var chars = /[,\/\w]/i; // all valid characters 
$('.numbers').keyup(function(e) {
var value = this.value;
var char = value[value.length-1];
if(value.length==1 && char==" "){$(this).val(value.substring(0, value.length-1));};
if (!chars.test(char)) {
	  if(value.length > 0){
        alert('Special characters not allowed');
           }
           var count1=0;
           for (var i=0;i<value.length;i++){
               var d1=value.charAt(value.length - i);
          //     alert("d1**"+d1);
               if(!d1.match(chars)){
               count1+=1;
                }
              }//alert(count1);
        
 $(this).val(value.substring(0, value.length-count1-1));
  if(value.substring(0, value.length-count1-1)==""){
 	 $(this).focus();
  }
 
}else if(value[value.length-1]==" " && value[value.length-2]==" " && value.length>1){
	  $(this).val(value.substring(0, value.length-1));
}else{
	  
}
});

/*$('.phno').change(function(e) {
	var a= this.value;
	if(isNaN(a) || a.lentgh>15 || a.length<10){
		alert("enter valid phno\n Length should be between 10-15");
	//	$(this).val("").css("background-color", "orange").focus();
		$(this).val("").focus();
	}
});*/
$('.email').change(function(e) {  
	   var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    //   var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
   if (!reg.test(this.value)) 
{
    alert('Invalid Email Address');
    var a ="";

	$('#email').val(a);
	$('#email').focus();

    return false;
}

return true;

	
});
		
$('.phno').change(function(e){
	var phno= this.value;
	if(isNaN(phno) || 15<phno.length || phno.length<10){
		
		  $(this).css("background-color", "rgb(245, 203, 203)");
          $(this).css("border", "1px solid #FF0000");    
          $(this).attr("placeholder", "Digits length b/w 10-15");
	           
			$(this).val("").focus();
			return false;
			
	}else{
		$(this).css("background-color", "#FFF");
        $(this).css("border", "1px solid gray");    
        $(this).removeAttr( "placeholder");
       
	}
});
	
	
});