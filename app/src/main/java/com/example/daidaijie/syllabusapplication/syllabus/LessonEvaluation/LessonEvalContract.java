package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import com.example.daidaijie.syllabusapplication.base.BasePresenter;
import com.example.daidaijie.syllabusapplication.base.BaseView;
import com.example.daidaijie.syllabusapplication.bean.Lesson;

/**
 * Created by 16zhchen on 2018/9/22.
 */

public class LessonEvalContract {
    interface presenter  extends BasePresenter{
        void postEval(int score, String eval);
        void deleteEval();
    }
    interface view extends BaseView<presenter>{
        void showMessage(String msg);
        void closePage();
        void setRating(float score);
        void setText(String text);
        void showData(Lesson lesson);
    }
}
