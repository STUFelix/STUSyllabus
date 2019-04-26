package com.example.daidaijie.syllabusapplication.syllabus.LessonEvaluation;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daidaijie.syllabusapplication.base.BaseActivity;
import com.example.daidaijie.syllabusapplication.bean.Lesson;
import com.example.daidaijie.syllabusapplication.syllabus.SyllabusComponent;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.daidaijie.syllabusapplication.R;


public class LessonEvalActivity extends BaseActivity implements LessonEvalContract.view{
    @BindView(R.id.lesson_eval_submit)
    Button mSubmitButton;
    @BindView(R.id.lesson_eval_delete)
    Button mDeleteButton;
    @BindView(R.id.lesson_score)
    RatingBar mRatingBar;
    @BindView(R.id.lesson_eval)
    EditText mEditText;
    private long mID;

    @BindView(R.id.titleTextView1)
    TextView mTitleTextView1;
    @BindView(R.id.titleTextView2)
    TextView mTitleTextView2;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    LessonEvalPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupTitleBar(mToolbar);


        DaggerLessonEvalComponnent.builder()
                .syllabusComponent(SyllabusComponent.getINSTANCE())
                .lessonEvalModule(new LessonEvalModule(this))
                .build().inject(this);
        Intent intent = getIntent();
        mID = intent.getLongExtra("CLASSID",0);
        mPresenter.setmID(mID);
        mPresenter.start();
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.postEval((int)mRatingBar.getRating(),mEditText.getText().toString());
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.deleteEval();
            }
        });
        if(mPresenter.isFirst)
            mDeleteButton.setVisibility(View.GONE);
        else
            mDeleteButton.setVisibility(View.VISIBLE);
    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closePage() {
        this.finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_lesson_eval;
    }

    @Override
    public void setRating(float score) {
        mRatingBar.setRating(score);
    }

    @Override
    public void setText(String text) {
        mEditText.setText(text);
    }

    @Override
    public void showData(Lesson lesson) {
        GradientDrawable shape = (GradientDrawable) getResources().getDrawable(R.drawable.bg_show_classmate);
        shape.setColor(getResources().getColor(lesson.getBgColor()));
        mToolbar.setBackgroundColor(getResources().getColor(
                lesson.getBgColor()));
        mSubmitButton.setBackgroundDrawable(shape);
        mDeleteButton.setBackgroundDrawable(shape);
        mTitleTextView1.setText(lesson.getName());
        mTitleTextView2.setText(lesson.getTeacher());

    }
}
