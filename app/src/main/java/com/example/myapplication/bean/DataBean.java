package com.example.myapplication.bean;


import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBean {
    public int imageId;
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;

    public DataBean(int imageId, Integer imageRes, String title, int viewType) {
        this.imageId = imageId;
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(int imageId, Integer imageRes, String imageUrl, String title, int viewType) {
        this.imageId = imageId;
        this.imageRes = imageRes;
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<DataBean> getTestData() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(1,R.drawable.jpg1, "相信自己,你努力的样子真的很美", 1));
        list.add(new DataBean(2,R.drawable.jpg1, "极致简约,梦幻小屋", 1));
        list.add(new DataBean(3,R.drawable.jpg1, "超级卖梦人", 1));
        list.add(new DataBean(4,R.drawable.jpg1, "夏季新搭配", 1));
        list.add(new DataBean(5,R.drawable.jpg1, "绝美风格搭配", 1));
        list.add(new DataBean(6,R.drawable.jpg1, "微微一笑 很倾城", 1));
        return list;
    }

    public static List<DataBean> getTestData2() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.drawable.jpg1, "听风.赏雨", 3));
        list.add(new DataBean(R.drawable.jpg1, "迪丽热巴.迪力木拉提", 1));
        list.add(new DataBean(R.drawable.jpg1, "爱美.人间有之", 3));
        list.add(new DataBean(R.drawable.jpg1, "洋洋洋.气质篇", 1));
        list.add(new DataBean(R.drawable.jpg1, "生活的态度", 3));
        return list;
    }

    /**
     * 仿淘宝商品详情第一个是视频
     * @return
     */
    public static List<DataBean> getTestDataVideo() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4", "第一个放视频", 2));
        list.add(new DataBean(R.drawable.jpg1, "听风.赏雨", 1));
        list.add(new DataBean(R.drawable.jpg1, "迪丽热巴.迪力木拉提", 1));
        list.add(new DataBean(R.drawable.jpg1, "爱美.人间有之", 1));
        list.add(new DataBean(R.drawable.jpg1, "洋洋洋.气质篇", 1));
        list.add(new DataBean(R.drawable.jpg1, "生活的态度", 1));
        return list;
    }

    public static List<DataBean> getTestData3() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(1,R.drawable.jpg1,"https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg", "ddd", 1));
        list.add(new DataBean(2,R.drawable.jpg1,"https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg", "ddd1",2 ));
        list.add(new DataBean(3,R.drawable.jpg1,"https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg", "ddd2", 2));
        list.add(new DataBean(4,R.drawable.jpg1,"https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg", "ddd3", 3));
        list.add(new DataBean(5,R.drawable.jpg1,"https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg", "ddd4", 3));
        return list;
    }

    public static List<DataBean> getVideos() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4", null, 0));
        list.add(new DataBean("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4", null, 0));
        return list;
    }


    public static List<String> getColors(int size) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            list.add(getRandColor());
        }
        return list;
    }

    /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}
