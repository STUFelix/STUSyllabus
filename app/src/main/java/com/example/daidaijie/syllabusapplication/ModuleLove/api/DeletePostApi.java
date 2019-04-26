package com.example.daidaijie.syllabusapplication.ModuleLove.api;

import com.example.daidaijie.syllabusapplication.bean.HttpResult;

import retrofit2.http.DELETE;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by smallfly on 16-3-28.
 */
public interface DeletePostApi {
    @DELETE("/interaction/api/v2/anonymous")
    Observable<HttpResult<Void>> deletePost(@Header("id") int post_id, @Header("uid") int uid, @Header("token") String token);
}
