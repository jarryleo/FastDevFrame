package cn.leo.fastframe.test;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by JarryLeo on 2017/4/14.
 */

public interface PresenterAPI {
    @GET(NetConfig.BASE_URL)
    Observable<TestBean> requestNewsPicData(@Query("param") int param);

    @FormUrlEncoded
    @POST(NetConfig.BASE_URL)
    Observable<ResponseBody> Test1(@Field("param") String param);

    @FormUrlEncoded
    @POST(NetConfig.BASE_URL)
    Observable<ResponseBody> Test2(@FieldMap HashMap<String, String> params);
}
