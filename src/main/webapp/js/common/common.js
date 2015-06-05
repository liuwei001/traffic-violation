/**
 * 解析url后附加的数据
 * @param  {Boolean} isHash 为true时解析location.hash, false则解析location.search
 * @return {object}         返回键值对
 */
function parseUrlParam (isHash) {
  var query,
    data = {};
  if (isHash) {
    query = window.location.hash;
    query = query.replace(/^\#/,'');
  } else {
    query = window.location.search;
    query = query.replace(/^\?/,'');
  }
  if (!query) return false;
  query = query.split('&');
  query.forEach(function (ele) {
    var tmp = ele.split('=');
    data[ decodeURIComponent( tmp[0] ) ] = decodeURIComponent( tmp[1] === undefined ? '' : tmp[1] );
  });
  return data;
}
/**
 * 将商品信息序列化为query string
 * @param  {string} info 商品信息字符串, 格式为 Name,鸡翅;Id,2323
 * @return {string}      序列化后的字符串, 形如 Name=%E9%B8%A1%E7%BF%85&Id=2323
 */
function serializeMetaInfo (info) {
  var pairs = (info || '').replace(/^;|;$/g,'').replace(/^\s+|\s+$/g, ''),
    arr = [];
  if (pairs === '') return '';
  pairs = pairs.split(';');
  pairs.forEach(function (pair) {
    pair = pair.split(',');
    arr.push( encodeURIComponent($.trim(pair[0] )) + '=' + encodeURIComponent( $.trim( pair[1] ) ) );
  });
  return arr.join('&');
}
/**
 * 模板函数
 * @param  {string} tpl   模板, 括号中的东西会被替换掉(包括括号),依据括号内的名
 *                        称来寻找对象中对应的值
 *                        格式为 商品名称:{Name}, 商品ID: {Id}
 * @param  {array of object or object} value 用来生成模板的值, 可以是包含对象的
 *                        数组或者是对象
 * @return {string}
 */
function applyTpl (tpl, value) {
  var str = '',
    values = Array.isArray(value) ? value : [value];
  values.forEach(function (el) {
    str += tpl.replace(/{([^}]+)}/g, function ($0, $1) {
      if ($1 === 'RAW_DATA') {
        return encodeURIComponent( JSON.stringify(el) );
      } else {
        return el[$1] == null ? '': el[$1];
      }
    });
  });
  return str;
}
/**
 * 将对象序列化为query string
 * @param  {object} data 要序列化的对象, 如 {Name:"鸡翅",Id:2323}
 * @return {string}      序列化之后的结果,形如 Name=%E9%B8%A1%E7%BF%85&Id=2323
 */
function serialize (data) {
  var res = [],key;
  for (key in data) {
    if (data.hasOwnProperty(key)) {
      res.push( encodeURIComponent( key ) + '=' + encodeURIComponent( data[key] ));
    }
  }
  return res.join('&');
}
/**
 * 判断对象是否为空
 * @param  {object}  obj
 * @return {Boolean}     为空则返回true, 否则false
 */
function isEmptyObj (obj) {
  var key;
  for (key in obj) {
    if (obj.hasOwnProperty(key)) {
      return false;
    }
  }
  return true;
}
/**
 * 为带有类 .btn或者 .touch的元素添加 pressing 类, 若要修改对应元素按下的效果,
 *   修改pressing类的样式即可
 */
$(function () {
  $(document.body).on('touchstart','.btn,.touch',function () {
    if(this.hasAttribute('disabled') || this.classList.contains('disabled')) return false;
    this.classList.add('pressing');
  });
  $(document.body).on('touchend touchmove','.btn,.touch',function () {
    this.classList.remove('pressing');
  });
});

function toObj( query ){
  var data = {};
  query = query.split('&');
    query.forEach(function (ele) {
    var tmp = ele.split('=');
      data[ decodeURIComponent( tmp[0] ) ] = decodeURIComponent( tmp[1] === undefined ? '' : tmp[1] );
      });
  return data;
}