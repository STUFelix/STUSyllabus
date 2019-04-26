package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.circleDetail;

import com.example.daidaijie.syllabusapplication.bean.CommentInfo;
import com.example.daidaijie.syllabusapplication.bean.PostCommentBean;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 2016/8/10.
 */
public interface CircleCommentsApi {

    @GET("interaction/api/v2/post_comments?field=post_id")
    Observable<CommentInfo> getComments(@Query("value") int post_id);

    @POST("/interaction/api/v2/comment")
    Observable<ThumbUpReturn> sendComment(@Body PostCommentBean postCommentBean);
}
