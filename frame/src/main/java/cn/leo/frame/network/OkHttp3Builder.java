package cn.leo.frame.network;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.leo.frame.BuildConfig;
import cn.leo.frame.utils.FileUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Leo on 2018/1/4.
 */

public class OkHttp3Builder {
    private int READ_TIMEOUT = 30;
    private int WRITE_TIMEOUT = 30;
    private int CONNECT_TIMEOUT = 30;
    private int cacheSize = 10 * 1024 * 1024;
    private File cacheDir = FileUtil.getCacheDir();
    private List<Interceptor> mInterceptorList;

    public OkHttp3Builder connectTimeout(int timeout) {
        CONNECT_TIMEOUT = timeout;
        return this;
    }

    public OkHttp3Builder writeTimeout(int timeout) {
        WRITE_TIMEOUT = timeout;
        return this;
    }

    public OkHttp3Builder readTimeout(int timeout) {
        READ_TIMEOUT = timeout;
        return this;
    }

    public OkHttp3Builder cache(File cacheDir, int cacheSize) {
        this.cacheDir = cacheDir;
        this.cacheSize = cacheSize;
        return this;
    }

    public OkHttp3Builder addInterceptor(Interceptor interceptor) {
        mInterceptorList.add(interceptor);
        return this;
    }

    public OkHttpClient build() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT,
                        TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT,
                        TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,
                        TimeUnit.SECONDS)
                .cache(new Cache(cacheDir,
                        cacheSize));
        for (Interceptor interceptor : mInterceptorList) {
            builder.addInterceptor(interceptor);
        }
        mInterceptorList.clear();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LoggerInterceptor());
        }
        /**
         * 支持https操作
         */
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                           String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                           String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }};
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
            hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(hostnameVerifier);
        return builder.build();
    }
}
