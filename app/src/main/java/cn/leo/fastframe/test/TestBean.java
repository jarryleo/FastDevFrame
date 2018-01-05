package cn.leo.fastframe.test;

import java.util.List;

/**
 * Created by Leo on 2017/5/18.
 */

public class TestBean {

    /**
     * newslist : [{"id":26754,"author":"test33","pubDate":"2013-09-17 16:49:50.0","title":"asdfa","authorid":253469,"commentCount":0,"type":4}]
     * notice : {"replyCount":0,"msgCount":0,"fansCount":0,"referCount":0}
     */

    public NoticeBean notice;
    public List<NewslistBean> newslist;

    public static class NoticeBean {
        /**
         * replyCount : 0
         * msgCount : 0
         * fansCount : 0
         * referCount : 0
         */

        public int replyCount;
        public int msgCount;
        public int fansCount;
        public int referCount;
    }

    public static class NewslistBean {
        /**
         * id : 26754
         * author : test33
         * pubDate : 2013-09-17 16:49:50.0
         * title : asdfa
         * authorid : 253469
         * commentCount : 0
         * type : 4
         */

        public int id;
        public String author;
        public String pubDate;
        public String title;
        public int authorid;
        public int commentCount;
        public int type;
    }
}
