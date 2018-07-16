package cn.leo.frame.network;


import java.io.IOException;
import java.nio.charset.Charset;

import cn.leo.frame.utils.Logger;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Leo on 2017/8/24.
 */

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Logger.d(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        source.request(Integer.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        if (buffer.size() > 1024 * 16) { //接口数据大于16K不显示
            Logger.i(request.url() + " 接口返回数据(长度:" + buffer.size() + ")大于16K不打印,点击链接在网页查看");
            return response;
        }
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        String str = convert(bodyString);
        long t2 = System.nanoTime();
        Logger.i(String.format("Received response in %.1fms%n%s", (t2 - t1) / 1e6d, str));
        return response;
    }

    /*unicode转汉字*/
    private String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        if (pos < utfString.length())
            sb.append(utfString.substring(pos, utfString.length()));
        return sb.toString();
    }
}
