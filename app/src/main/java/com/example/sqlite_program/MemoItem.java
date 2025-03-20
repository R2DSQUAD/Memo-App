package com.example.sqlite_program;

import java.io.Serializable;

public class MemoItem implements Serializable
{
    private int id = 0;            // 메모의 고유 ID
    private String title;      // 메모 제목
    private String content;    // 메모 내용
    private String writeDate;  // 작성 날짜


    public MemoItem()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }


}
