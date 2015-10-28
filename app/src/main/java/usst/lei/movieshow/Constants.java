package usst.lei.movieshow;

import android.os.Build;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lei on 2015/10/28.
 */
public class Constants {


    static final String SearchFormat="https://api.themoviedb.org/3/discover/movie?primary_release_year=%d&api_key=%s&Language=zh";
    static final String SearchImageFormat="http://image.tmdb.org/t/p/w184%s";
    /**
     *
     * @param year 查询的年限
     * @return 查询的url链接
     */
    public static URL getSearchUrl(int year) throws MalformedURLException {
        // BuildConfig.APIKEY 是在api.themoviedb.org申请的key，请自行申请
        String s=String.format(SearchFormat,year, BuildConfig.APIKEY);
        Log.e("Task",s);
        return new URL(s);
    }

    /**
     *
     * @param img api返回的图像地址数据
     * @return 完整的图像数据
     */
    public static String getImageUrlString(String img)
    {
        return String.format(SearchImageFormat,img);
    }


}
