package com.example.daidaijie.syllabusapplication.todo.Bean;

public class TodoList {

    /**
     *                  "id": 85,
     *                 "label": "日常",
     *                 "title": "学习",
     *                 "content": "数据结构与算法",
     *                 "release_time": "2019-03-06 10:18:04",
     *                 "start_time": "2019-10-10 19:19:19",
     *                 "deadline_time": "2019-10-11 12:12:12",
     *                 "priority": 1,
     *                 "status": false,
     *                 "img_link": null
     */

    int id;
    String label;
    String title;
    String content;
    String release_time;
    String start_time;
    String deadline_time;
    int priority;
    boolean status;
    String img_link;

    public TodoList(int id,
                    String label,
                    String title,
                    String content,
                    String release_time,
                    String start_time,
                    String deadline_time,
                    int priority,
                    boolean status,
                    String img_link) {
        this.id = id;
        this.label = label;
        this.title = title;
        this.content = content;
        this.release_time = release_time;
        this.start_time = start_time;
        this.deadline_time = deadline_time;
        this.priority  = priority;
        this.status = status;
        this.img_link = img_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
}