package cn.leo.fastframe.net;

import cn.leo.fastframe.test.TestBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by JarryLeo on 2017/4/14.
 */

public interface PresenterAPI {
    @GET(NetConfig.NEWS_LIST)
    Observable<TestBean> getNewsList(@Query("access_token") String access_token,
                                     @Query("catalog") int catalog,
                                     @Query("dataType") String dataType,
                                     @Query("page") int page,
                                     @Query("pageSize") int pageSize);

   /* @GET(NetConfig.BASE_URL)
    Observable<TestBean> requestNewsPicData(@Query("param") int param);

    @FormUrlEncoded
    @POST(NetConfig.BASE_URL)
    Observable<ResponseBody> Test1(@Field("param") String param);

    @FormUrlEncoded
    @POST(NetConfig.BASE_URL)
    Observable<ResponseBody> Test2(@FieldMap HashMap<String, String> params);*/
}
