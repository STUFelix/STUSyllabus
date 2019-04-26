package com.example.daidaijie.syllabusapplication.ModuleInfo.circle.postContent;

import com.example.daidaijie.syllabusapplication.ModuleInfo.api.PostBean;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.InfoDeleteReturn;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.PostContent;
import com.example.daidaijie.syllabusapplication.ModuleInfo.bean.modifyContent;
import com.example.daidaijie.syllabusapplication.bean.ThumbUpReturn;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by smallfly on 16-3-27.
 */
public interface PushPostApi {

//    # 话题, 用户自发的
//            POST_TYPE_TOPIC = 1
//
//    # 宣传活动性质的(公众号推文)
//    POST_TYPE_ACTIVITY = 2  # 如果是这种类型的话, 那么客户端处理的时候就要注意把content作为文章的URL

    public static final int POST_TYPE_TOPIC = 0;
    public static final int POST_TYPE_ACTIVITY = 1;

    @POST("interaction/api/v2/post")
    Observable<ThumbUpReturn> post(@Body PostContent postContent);

    @GET("/interaction/api/v2/post/{id}")
    Observable<PostBean> getPost(@Path("id") int id);

    @Headers("Content-type: application/json")
    @PUT("/interaction/api/v2/post")
    Observable<InfoDeleteReturn> update(@Body modifyContent modifyContent);
}
