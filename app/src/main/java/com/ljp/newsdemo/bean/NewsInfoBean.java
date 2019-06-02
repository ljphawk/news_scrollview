package com.ljp.newsdemo.bean;


/*
 *@创建者       L_jp
 *@创建时间     2019/6/1 20:37.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class NewsInfoBean {
    String title;
    String content;

    public NewsInfoBean() {
    }

    public NewsInfoBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
