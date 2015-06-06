//list选择效果
$(function() {

	/*var arr = [],
        content = $('.list-content > .tab-content'),
        input = $('#city'),
        btn = $('#hide-list'),
        item = content.find('li'),
        values = "";

    function choose() {
        var value = $(this).html();
        values = "";
        alert(value)
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
    });*/
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

/* ========================================================================
 * Bootstrap: tab.js v3.3.4
 * http://getbootstrap.com/javascript/#tabs
 * ========================================================================
 * Copyright 2011-2015 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */


+function ($) {
  'use strict';

  // TAB CLASS DEFINITION
  // ====================

  var Tab = function (element) {
    this.element = $(element)
  }

  Tab.VERSION = '3.3.4'

  Tab.TRANSITION_DURATION = 150

  Tab.prototype.show = function () {
    var $this    = this.element
    var $ul      = $this.closest('ul:not(.dropdown-menu)')
    var selector = $this.data('target')

    if (!selector) {
      selector = $this.attr('href')
      selector = selector && selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
    }

    if ($this.parent('li').hasClass('active')) return

    var $previous = $ul.find('.active:last a')
    var hideEvent = $.Event('hide.bs.tab', {
      relatedTarget: $this[0]
    })
    var showEvent = $.Event('show.bs.tab', {
      relatedTarget: $previous[0]
    })

    $previous.trigger(hideEvent)
    $this.trigger(showEvent)

    if (showEvent.isDefaultPrevented() || hideEvent.isDefaultPrevented()) return

    var $target = $(selector)

    this.activate($this.closest('li'), $ul)
    this.activate($target, $target.parent(), function () {
      $previous.trigger({
        type: 'hidden.bs.tab',
        relatedTarget: $this[0]
      })
      $this.trigger({
        type: 'shown.bs.tab',
        relatedTarget: $previous[0]
      })
    })
  }

  Tab.prototype.activate = function (element, container, callback) {
    var $active    = container.find('> .active')
    var transition = callback
      && $.support.transition
      && (($active.length && $active.hasClass('fade')) || !!container.find('> .fade').length)

    function next() {
      $active
        .removeClass('active')
        .find('> .dropdown-menu > .active')
          .removeClass('active')
        .end()
        .find('[data-toggle="tab"]')
          .attr('aria-expanded', false)

      element
        .addClass('active')
        .find('[data-toggle="tab"]')
          .attr('aria-expanded', true)

      if (transition) {
        element[0].offsetWidth // reflow for transition
        element.addClass('in')
      } else {
        element.removeClass('fade')
      }

      if (element.parent('.dropdown-menu').length) {
        element
          .closest('li.dropdown')
            .addClass('active')
          .end()
          .find('[data-toggle="tab"]')
            .attr('aria-expanded', true)
      }

      callback && callback()
    }

    $active.length && transition ?
      $active
        .one('bsTransitionEnd', next)
        .emulateTransitionEnd(Tab.TRANSITION_DURATION) :
      next()

    $active.removeClass('in')
  }


  // TAB PLUGIN DEFINITION
  // =====================

  function Plugin(option) {
    return this.each(function () {
      var $this = $(this)
      var data  = $this.data('bs.tab')

      if (!data) $this.data('bs.tab', (data = new Tab(this)))
      if (typeof option == 'string') data[option]()
    })
  }

  var old = $.fn.tab

  $.fn.tab             = Plugin
  $.fn.tab.Constructor = Tab


  // TAB NO CONFLICT
  // ===============

  $.fn.tab.noConflict = function () {
    $.fn.tab = old
    return this
  }


  // TAB DATA-API
  // ============

  var clickHandler = function (e) {
    e.preventDefault()
    Plugin.call($(this), 'show')
  }

  $(document)
    .on('click.bs.tab.data-api', '[data-toggle="tab"]', clickHandler)
    .on('click.bs.tab.data-api', '[data-toggle="pill"]', clickHandler)

}(jQuery);
