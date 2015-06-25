$(function(){
 var btn = $('.subscibe > div > .btn'),
 msg = $('.alert');

 btn.click(function() {
     msg.fadeIn(200).find('.message').addClass('bounceIn');
 });
 
 $("#code").click(function(){
	 $(this).removeClass("has-error");
	 if($("#code_error")) {
		 $("#code_error").remove();
	 }
});

 addDefault('#subscribe');
 close('.alert');

 //tab效果
 $('#listTab a:first').tab('show');

 $('.select-content').selectBox();

 $.validator.setDefaults({
     submitHandler: function(form) {
     	syncData("validatemsg/check/" + $("#code").val(),"GET",null,function(success,data){
         	if(success == true){
         		var reqParam = parseUrlParam(false);
         		reqParam.mobile = $("#mobile").val();
         		syncData("subscribe/save","POST",reqParam,function(success,data){
         			if(success == true){
         				$("#form_div").addClass("hide");
         		    	$("#info_div").removeClass("hide");
         		    	$("#info_div p").html("您已成功订阅平安违章查询服务，将定期将["+ reqParam.carno +"]违章信息发送至您预留的"+ reqParam.mobile +"手机中。");
         			}else{
         				$("#form_div").addClass("hide");
         		    	$("#info_div").removeClass("hide");
         		    	if(data.resultCode == "2000001") {
         		    		$("#info_div").html("<p><span style='font-size:12px;color:red'>已经订阅过，不需要重复订阅</span></p>");
         		    	} else {
         		    		$("#info_div").html("<p><span style='font-size:12px;color:red'>系统请求出错："+ data.resultMsg +"</span></p>");
         		    	}
         			}
         		});
         	}else {
         		if(data.resultCode == "1100001") {
         			$("#code").addClass("has-error").after("<span class=\"has-error\" id='code_error'>验证码失效</span>")
         		} else if(data.resultCode == "1100002") {
         			$("#code").addClass("has-error").after("<span class=\"has-error\" id='code_error'>验证码错误</span>")
         		}
         	}
         });
     	
     }
 });

 $('#main-form').validate({
     rules: {
         city: "required",
         num: {
             required: true,
             carNum: true
         },
         engin: {
             required: true,
         },
         frame: {
             required: true,
         },
         year: {
             required: true,
         },
         month: {
             required: true,
         },
         phone: {
             required: true,
             phone: true
         },
         code: {
             required: true,
         }
     },
     messages: {
         city: "",
         year: "",
         month: ""
     },
     errorClass: 'has-error',
     errorElement: "span",
 });
});