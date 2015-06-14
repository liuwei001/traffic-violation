$(function() {
	    	
    	//初始化城市
    	initCitys() ;
    	
    	var reqParam = parseUrlParam(false);
    	if(reqParam) {
    		initQueryData(reqParam);
    	}
    	
    	/*var myDate = new Date();
    	var currYear = myDate.getFullYear();
    	var array_li = [];
    	for(var i = 1993; i <= currYear; i++ ) {
    		array_li.push("<li>" + i + "年</li>");
    	}*/
    	//设置购车时间年份 从1993年开始
    	/*$("#buyyear_li").html(array_li.join(""));
    	$('.select-content').selectBox();*/
    	
	 var btn = $('.subscibe > div > .btn'),
     msg = $('.alert');

     btn.click(function() {
         msg.fadeIn(200).find('.message').addClass('bounceIn');
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
             		var param = [];
                		var city = "city," + $("#cityText").val();
                		var carType = "carType,02";
                		var carno = "carno," + $("#num").val();
                		var engineno = "engineno," + $("#engin").val();
                		var classno = "classno," + $("#frame").val();
                		var mobile = "mobile," + $("#mobile").val();
                		
                		param.push(city);
                		param.push(carType);
                		param.push(carno);
                		param.push(engineno);
                		param.push(classno);
                		param.push(mobile);
                		
                		window.location = "view/result.html?" + serializeMetaInfo(param.join(";"));
             	}else {
             		if(data.resultCode == "1100001") {
             			$("#code").addClass("has-error").after("<span class=\"has-error\">验证码失效</span>")
             		} else if(data.resultCode == "1100002") {
             			$("#code").addClass("has-error").after("<span class=\"has-error\">验证码错误</span>")
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
    
/************* functions *********************/
//初始化历史记录
function  initQueryData(reqParam){
	    var city  = reqParam.city;
    	$("#cityText").val(city);
    	$("#city").val($("#home ul").find("li[code="+ city +"]").html());
    	
    	$("#num").val(reqParam.carno);
    	
    	/*var cartype = reqParam.cartype;
    	$("#cartype").val(cartype);
    	$("#cartype_text").val($("#cartype_"+cartype).html());*/
    	
    	$("#engin").val(reqParam.engineno);
    	$("#frame").val(reqParam.classno);
    	$("#mobile").val(reqParam.mobile)
    }
    
    //初始化城市信息
    function initCitys() {
    	syncData("citylist","GET",null,function(success,data){
	        if(success == true){
	        	if(data != null) {
	        		
	        		var abcd = [];
	        		var efgh = [];
	        		var ijklm = [];
	        		var nopqr = [];
	        		var stuvw = [];
	        		var xyz = [];
	        		var str = "ABCDEFGHIJKLMOPQRSTUVWXYZ";
	        		for(var i = 0;i < str.length; i++) {
	        			var prefix = str.charAt(i);
	        			if(data[prefix] == null) continue;
	        			var obj = {
	        					prefix:prefix,
	        					citylist:data[prefix]
	        			}
	        			if("ABCD".indexOf(prefix) > -1) {
	        				abcd.push(obj);
	        			}
	        			if("EFGH".indexOf(prefix) > -1) {
	        				efgh.push(obj);
	        			}
	        			if("IJKLM".indexOf(prefix) > -1) {
	        				ijklm.push(obj);
	        			}
	        			if("NOPQR".indexOf(prefix) > -1) {
	        				nopqr.push(obj);
	        			}
	        			if("STUVW".indexOf(prefix) > -1) {
	        				stuvw.push(obj);
	        			}
	        			if("XYZ".indexOf(prefix) > -1) {
	        				xyz.push(obj);
	        			}
	        		}
	        		
	        		$("#ABCD").html($("#list_template").render(abcd));
	        		$("#EFGH").html($("#list_template").render(efgh));
	        		$("#IJKLM").html($("#list_template").render(ijklm));
	        		$("#NOPQR").html($("#list_template").render(nopqr));
	        		$("#STUVW").html($("#list_template").render(stuvw));
	        		$("#XYZ").html($("#list_template").render(xyz));
	        		
	        		var arr = [],
	                arr_abbr = [],
	        		arr_code = [],
	                content = $('.list-content > .tab-content'),
	                input = $('#city'),
	                btn = $('#hide-list'),
	                item = content.find('li'),
	                values = "",
	                city_codes = "";
	            	item.click(choose);
	            	
	            	function choose() {
	                    var value = $(this).html();
	                    var code = $(this).attr("code");
	                    values = "";
	                    codes = "";
	                    var	abbr = $(this).attr("abbr");
	                    
	                    if ($(this).hasClass('active')) {
	                        $(this).removeClass('active');
	                        arr.splice($.inArray(value, arr), 1);
	                        arr_code.splice($.inArray(code, arr_code), 1);
	                        arr_abbr.splice($.inArray(abbr, arr_abbr), 1);
	                    } else {
	                        if (arr.length < 3) {
	                            $(this).addClass('active');
	                            arr.push(value);
	                            arr_code.push(code);
	                            arr_abbr.push(abbr);
	                        }
	                    }

	                    values = arr.join(', ');
						codes = arr_code.join('、');
	                    input.val(values);
						$("#cityText").val(codes);
						
						if(arr_abbr.length > 0) {
							var carno = $("#num").val();
							if(trim(carno) == "" || carno.length <= 2) {
								$("#num").val(arr_abbr[0]);
							}
						}
	                }
	            	
	            	 input.add(btn).click(function() {
	            	        $('.select-list').toggle();

	            	        return false;
	            	    });
	        	} 	            
	        }
	    });
    }
    
    //选择车辆类型
   /* function selectCatType(cartype) {
    	$("#cartype").val(cartype);
    }*/