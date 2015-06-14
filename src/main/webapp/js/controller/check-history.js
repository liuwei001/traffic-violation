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
         		
         		window.location = "history.html?mobile=" + $("#mobile").val();
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