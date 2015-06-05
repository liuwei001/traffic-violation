/* 
 * Copyright (c) 2015, QUANRONG E-COMMDERCE LTD. All rights reserved.
 */
package com.traffic.common.page;

/**
 * 描述：分页导航工具类
 * 
 * <pre>HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2015-5-20      vmp         Create
 * ****************************************************************************
 * </pre>
 * @author vmp
 * @since 1.0
 */
public class NavUtils {
	private static final int show_ = 10;  
	  
    private static final int sep_ = show_ / 2;  
  
    private static final int split_ = sep_ / 2;  
  
    public static String build(int offset, int limit, int total) {  
  
        int count = total % limit == 0 ? total / limit : total / limit + 1;  
  
        StringBuffer buffer = new StringBuffer();  
  
//        buffer.append("共 " + count + " 页 " + total + " 条记录 ");  
  
        // 判断是否显示上页  
        if (offset > 1) {  
            int prev = offset - 1;  
            buffer.append("<a href='javascript:void(0);' class='guidePrev'").append("onclick='filterData(").append(prev).append(  
                    ");'").append("'>").append("上一页").append("</a>");    
        }  
  
        // 页数不超过限制的情况  
        if (count <= show_)  
            buffer.append(getPart(1, count, offset, limit));  
  
        // 页数大于限制的情况  
  
        if (count > show_) {  
            if (offset <= sep_) {  
                buffer.append(getPart(1, sep_ + split_, offset, limit));  
                buffer.append(getEllipsis("...")).append(getNormalPart(String.valueOf(count), count, limit));  
            } else if (offset > (count - sep_)) {  
                buffer.append(getNormalPart(String.valueOf(1), 1, limit)).append(getEllipsis("..."));  
                buffer.append(getPart(count - sep_ - 1, count, offset, limit));  
            } else {  
                buffer.append(getNormalPart(String.valueOf(1), 1, limit)).append(getEllipsis("..."));  
                buffer.append(getPart(offset - split_ - 1, offset + split_ + 1, offset, limit));  
                buffer.append(getEllipsis("...")).append(getNormalPart(String.valueOf(count), count, limit));  
            }  
        }  
  
        // 判断是否显示下页  
        if (offset < count) {  
            int next = offset + 1;  
            buffer.append("<a href='javascript:void(0);' class='guideNext'").append("onclick='filterData(").append(next).append(  
                  ");'").append("'>").append("下一页").append("</a>");  
        }  
  
        return buffer.toString();  
  
    }  
  
    // 一般按钮  
    private static StringBuffer getNormalPart(String content, int offset, int limit) {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("<a href='javascript:void(0);' ").append("onclick='filterData(").append(offset).append(  
                ");'").append("'>").append(content).append("</a>");  
        return buffer;  
    }  
  
    // 拼接中间部分  
    private static StringBuffer getPart(int begin, int end, int offset, int limit) {  
        StringBuffer buffer = new StringBuffer();  
        for (int i = begin; i <= end; i++) {  
            if (offset == i)  
                buffer.append(getSelectedPart(String.valueOf(i), i));  
            else  
                buffer.append(getNormalPart(String.valueOf(i), i, limit));  
        }  
        return buffer;  
    }  
  
    // 选中按钮  
    private static StringBuffer getSelectedPart(String content, Integer value) {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("<a class='act'> ").append(content).append("</a> ");  
        return buffer;  
    }  
  
    // 省略部分  
    private static StringBuffer getEllipsis(String content) {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append(" ").append(content).append(" ");  
        return buffer;  
    }  
}
