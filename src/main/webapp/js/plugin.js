//list选择效果
$(function() {
    var arr = [],
        content = $('.list-content > .tab-content'),
        input = $('#city'),
        btn = $('#hide-list'),
        item = content.find('li'),
        values = "";

    function choose() {

        var value = $(this).html();
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
        console.info(arr);
    }

    item.click(choose);

    input.add(btn).click(function() {
        $('.select-list').toggle();

        return false;
    });
});

//禁用按钮
function addDefault(element) {
    var _e = $(element);
    if (_e.hasClass('btn')) {
        _e.click(function() {
            $(this).addClass('btn-disable')
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
        	$(option.list).hide();
            $(this).next(option.list).show();
        });


        $(this).next(option.list).find('li').click(function() {
            $(this).addClass(option.activeClass).siblings().removeClass(option.activeClass);
            input.val($(this).html());
            $(this).parents(option.list).hide();
        })

    });

};


//Installation
$(function() {
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

});
