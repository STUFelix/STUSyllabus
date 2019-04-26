package com.example.daidaijie.syllabusapplication.Modulefindlost.FindALL.postContent;

import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.FindBean;
import com.example.daidaijie.syllabusapplication.Modulefindlost.bean.LostAddBean;
import com.example.daidaijie.syllabusapplication.todo.bean.HttpBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

//    @FormUrlEncoded
//    @POST("/extension/api/v2/findlost")
//    Observable<LostAddBean> post(@Body postBean postContent);
    @GET("/extension/api/v2/findlost/{id}")
    Observable<FindBean> getPost(@Path("id") int id);

    @FormUrlEncoded
    @POST("/extension/api/v2/findlost")
    Observable<LostAddBean> post(@Field("uid") int uid,
                                 @Field("token") String token,
                                 @Field("kind") int kind,
                                 @Field("title") String title,
                                 @Field("description") String description,
                                 @Field("location") String location,
                                 @Field("contact") String contact,
                                 @Field("img_link") String imgLink);

    @FormUrlEncoded
    @PUT("/extension/api/v2/findlost")
    Observable<HttpBean> modify(@Field("uid") int uid,
                                @Field("token") String token,
                                @Field("kind") int kind,
                                @Field("title") String title,
                                @Field("description") String description,
                                @Field("location") String location,
                                @Field("contact") String contact,
                                @Field("img_link") String imgLink,
                                @Field("findlost_id")int findlost_id);

}
