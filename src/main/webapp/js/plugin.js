//list选择效果
$(function() {
    var arr = [],
        content = $('.list-content > .tab-content'),
        input = $('#city'),
        btn = $('#hide-list'),
        item = content.find('li'),
        values = "";

    function choose() {

        var value = $(this).find('span.name').html() || $(this).html();
        values = "";

        if ($(this).hasClass('active')) {
            $(this).removeClass('active');
            arr.splice($.inArray(value, arr), 1);
        } else {
            if (arr.length < 3) {
                $(this).addClass('active');
                arr.push(value);
                //checkArr();
            }
        }

        values = arr.join(', ');

        input.val(values);
    }

    item.click(choose);
    item.click(function() {
        if (arr.length >= 3) {
            $('.select-list').hide();
            input.focus();
        };
    });

    input.add(btn).click(function() {
        $('.select-list').toggle();
        input.focus();
        return false;
    });

});

//禁用按钮
function addDefault(element) {
    var _e = $(element);
    if (_e.hasClass('btn')) {
        _e.click(function() {
            $(this).addClass('btn-disable').attr('disable')
        })
    }
}

//关闭弹窗
function close(win) {
    var _w = $(win),
        closeBtn = $('.close');

    closeBtn.click(function() {
        _w.hide().find('.message').removeClass('bounceIn');
    });
}

//下拉菜单
$.fn.selectBox = function(options) {
    var defaults = {
        activeClass: "active",
        input: ".select-input",
        list: ".select-input-list"
    };

    var option = $.extend(defaults, options);
    var _c = $(this).find(option.input);

    _c.each(function() {
        var input = $(this);

        $(this).click(function() {
            $(option.list).not($(this).siblings(option.list)).removeClass('active');
            $(this).siblings(option.list).toggleClass('active');
        });

        $(this).siblings(option.list).find('li').click(function() {
            $(this).addClass(option.activeClass).siblings().removeClass(option.activeClass);
            input.val($(this).html());
            $(this).parents(option.list).removeClass('active');
            input.focus();
        })

    });

};
//验证码计时
var wait = 60;

function time(o) {    
    if (wait == 0) {      
        o.attr("disabled", false);           
        o.html("免费获取验证码");      
        wait = 60;    
    } else {      
        o.attr("disabled", true);      
        o.html(wait + '秒后可重新获取');
        wait--;      
        setTimeout(function() {        
            time(o)      
        }, 1000)    
    }  
}

//validate
jQuery.validator.addMethod(
    "phone",
    function(value, element) {
        var tel = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
        return this.optional(element) || (tel.test(value));
    }, "请填写有效手机号码"
);
jQuery.validator.addMethod(
    "carNum",
    function(value, element) {
        var num = /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/;
        return this.optional(element) || (num.test(value));
    }, "请填写有效的车牌号码"
);


//Installation
$(function() {
    var btn = $('.subscibe > div > .btn'),
        msg = $('.alert');

    btn.click(function() {
        msg.fadeIn(200).find('.message').addClass('bounceIn');
    });

    $('#sent-msg').click(function() {
        var t = $(this);
        time(t);
    });

    addDefault('#subscribe');
    close('.alert');

    //tab效果
    $('#listTab a:first').tab('show');

    $('.select-content').selectBox();

    $('send-msg').click(function() {
        var num = num
    });

    $.validator.setDefaults({
        submitHandler: function(form) {
            // alert('Submitted!')   
            form.submit();
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
            month: "",
            code: ""
        },
        errorClass: 'has-error',
        errorElement: "span",
    });
});
