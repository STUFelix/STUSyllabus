package com.example.daidaijie.syllabusapplication.mystu;

public class DiscussionBean {

    /**
     {
     "content": "请同学们打印承诺表并按要求填好，下周上课记得把表带过来提交。请留意并相互转告，谢谢！",
     "course_files": {
     "course_files_details":
     [
     {
     "file_name": "1RM与训练负荷预估表.xlsx",
     "file_url": "https://my.stu.edu.cn/v3/services/openapi/v1/file/show/217B32D03FEA11E9B7D3AA5D0CE31C25/1RM%E4%B8%8E%E8%AE%AD%E7%BB%83%E8%B4%9F%E8%8D%B7%E9%A2%84%E4%BC%B0%E8%A1%A8.xlsx"
     },
     {
     "file_name": "学生学习目标承诺表.xlsx",
     "file_url": "https://my.stu.edu.cn/v3/services/openapi/v1/file/show/2B2E1BD03FEA11E9B7D3AA5D0CE31C25/%E5%AD%A6%E7%94%9F%E5%AD%A6%E4%B9%A0%E7%9B%AE%E6%A0%87%E6%89%BF%E8%AF%BA%E8%A1%A8.xlsx"
     }
     ],
     "length": 2
     },
     "course_name": "体适能基础理论与健跑培养计划",
     "teacher_name": "曲小锋"
     }*/
    private String content;

    public CourseFilesBean getCourse_files() {
        return course_files;
    }

    public void setCourse_files(CourseFilesBean course_files) {
        this.course_files = course_files;
    }

    private CourseFilesBean course_files;
    private String course_name;
    private String teacher_name;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }


}