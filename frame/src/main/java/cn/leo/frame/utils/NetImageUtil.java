package cn.leo.frame.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by JarryLeo on 2017/5/14.
 */

public class NetImageUtil {
    /**
     * 图片加载框架，所有加载图片使用此方法，方便维护和更换框架
     *
     * @param url
     * @param imageView
     */
    public static void setImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    /**
     * 图片加载框架，带默认图和加载失败图
     *
     * @param url
     * @param imageView
     * @param defResId
     * @param errResId
     */

    public static void setImageWithDefault(String url, ImageView imageView, int defResId, int errResId) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(defResId)
                .error(errResId)
                .crossFade()
                .into(imageView);
    }
}
