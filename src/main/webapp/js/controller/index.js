$(function() {
    	var myDate = new Date();
    	var currYear = myDate.getFullYear();
    	var array_li = [];
    	for(var i = 1993; i <= currYear; i++ ) {
    		array_li.push("<li>" + i + "年</li>");
    	}
    	
    	//初始化城市
    	initCitys() ;
    	
    	var reqParam = parseUrlParam(false);
    	if(reqParam) {
    		initQueryData(reqParam);
    	}
    	
    	//设置购车时间年份 从1993年开始
    	$("#buyyear_li").html(array_li.join(""));
    	$('.select-content').selectBox();
    	
    	$("#form").submit(function() {
    		if(checkInput()) {
    			var param = "";
        		$("input[lang=param]").each(function() {
        			if($(this).val() != "") {
        				if(param != "") {
            				param += ";";
            			}
            			param = param + $(this).attr("name") + "," + $(this).val();
        			}
        		});
        		window.location = "view/result.html?" + serializeMetaInfo(param);
    		}
    	});
    });
    
	//初始化历史记录
   function  initQueryData(reqParam){
	    var city  = reqParam.city;
    	$("#city").val(city);
    	$("#city_text").val($("#home ul").find("li[code="+ city +"]").html());
    	
    	$("#carno").val(reqParam.carno);
    	
    	var cartype = reqParam.cartype;
    	$("#cartype").val(cartype);
    	$("#cartype_text").val($("#cartype_"+cartype).html());
    	
    	$("#engineno").val(reqParam.engineno);
    	$("#classno").val(reqParam.classno);
    	$("#mobile").val(reqParam.mobile)
    }
    
    //检查输入
    function checkInput() {
    	
    	if($("#city_text").val() == "") {
			$("#city_text").focus();
			return false;
		}
		
		if(trim($("#carno").val()) == "") {
			$("#carno").focus();
			return false;
		}
		
		if($("#cartype").val() == "") {
			$("#cartype_text").focus();
			return false;
		}

		if(trim($("#engineno").val()) == "") {
			$("#engineno").focus();
			return false;
		}
		
		if(trim($("#classno").val()) == "") {
			$("#classno").focus();
			return false;
		}
		
		if(trim($("#mobile").val()) == "") {
			$("#mobile").focus();
			return false;
		}
		return true;
    }
    
    //初始化城市信息
    function initCitys() {
    	syncData("citylist","GET",null,function(success,data){
	        if(success == true){
	        	if(data != null) {
	        		$("#ABCD ul").html($("#list_template").render(data.abcd));
	        		$("#EFGH ul").html($("#list_template").render(data.efgh));
	        		$("#IJKLM ul").html($("#list_template").render(data.ijklm));
	        		$("#NOPQR ul").html($("#list_template").render(data.nopqr));
	        		$("#STUVW ul").html($("#list_template").render(data.stuvw));
	        		$("#XYZ ul").html($("#list_template").render(data.xyz));
	        		
	        		var arr = [],
	                arr_abbr = [],
	        		arr_code = [],
	                content = $('.list-content > .tab-content'),
	                input = $('#city_text'),
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
						$("#city").val(codes);
						
						if(arr_abbr.length > 0) {
							var carno = $("#carno").val();
							if(trim(carno) == "" || carno.length <= 2) {
								$("#carno").val(arr_abbr[0]);
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
    function selectCatType(cartype) {
    	$("#cartype").val(cartype);
    }