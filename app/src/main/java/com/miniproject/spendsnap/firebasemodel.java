package com.miniproject.spendsnap;

public class firebasemodel {
    private String title;
    private String content;
    public firebasemodel()
    {

    }
    public firebasemodel(String title,String content)
    {
        this.title =title;
        this.content =content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {

        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

