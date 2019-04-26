package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation.bean;

/**
 * Created by 16zhchen on 2018/9/22.
 */




public class PostEvalBean {
    private int uid;
    private int score;
    private String eval;
    private String token;
    private long ID;

    public PostEvalBean(int uid,int score,String eval,String token,long id){
        this.uid = uid;
        this.score = score;
        this.eval = eval;
        this.token = token;
        this.ID = id;
    }

}
