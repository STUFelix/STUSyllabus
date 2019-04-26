package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 16zhchen on 2018/10/14.
 */
@Entity
public class EvalBean {
    @Id
    Long id;
    @NotNull
    private int eva_id;
    private int class_id;
    private int teacher_id;
    private int cid;
    private String eva_content;
    private String eva_tags;
    private int eva_score;
    private boolean eva_status;
    private String eva_time;
    private String eva_years_semester;
    public String getEva_years_semester() {
        return this.eva_years_semester;
    }
    public void setEva_years_semester(String eva_years_semester) {
        this.eva_years_semester = eva_years_semester;
    }
    public String getEva_time() {
        return this.eva_time;
    }
    public void setEva_time(String eva_time) {
        this.eva_time = eva_time;
    }
    public boolean getEva_status() {
        return this.eva_status;
    }
    public void setEva_status(boolean eva_status) {
        this.eva_status = eva_status;
    }
    public int getEva_score() {
        return this.eva_score;
    }
    public void setEva_score(int eva_score) {
        this.eva_score = eva_score;
    }
    public String getEva_tags() {
        return this.eva_tags;
    }
    public void setEva_tags(String eva_tags) {
        this.eva_tags = eva_tags;
    }
    public String getEva_content() {
        return this.eva_content;
    }
    public void setEva_content(String eva_content) {
        this.eva_content = eva_content;
    }
    public int getCid() {
        return this.cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public int getTeacher_id() {
        return this.teacher_id;
    }
    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
    public int getClass_id() {
        return this.class_id;
    }
    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
    public int getEva_id() {
        return this.eva_id;
    }
    public void setEva_id(int eva_id) {
        this.eva_id = eva_id;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 8376359)
    public EvalBean(Long id, int eva_id, int class_id, int teacher_id, int cid,
            String eva_content, String eva_tags, int eva_score, boolean eva_status,
            String eva_time, String eva_years_semester) {
        this.id = id;
        this.eva_id = eva_id;
        this.class_id = class_id;
        this.teacher_id = teacher_id;
        this.cid = cid;
        this.eva_content = eva_content;
        this.eva_tags = eva_tags;
        this.eva_score = eva_score;
        this.eva_status = eva_status;
        this.eva_time = eva_time;
        this.eva_years_semester = eva_years_semester;
    }
    @Generated(hash = 1036478568)
    public EvalBean() {
    }
}
