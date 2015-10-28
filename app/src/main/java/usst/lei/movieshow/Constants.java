package usst.lei.movieshow;

import android.os.Build;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lei on 2015/10/28.
 */
public class Constants {


    static final String SearchFormat="https://api.themoviedb.org/3/discover/movie?primary_release_year=%d&%s&Language=zh";

    /**
     *
     * @param year 查询的年限
     * @return 查询的url链接
     */
    public static URL getSearchUrl(int year) throws MalformedURLException {
        // BuildConfig.APIKEY 是在api.themoviedb.org申请的key，请自行申请
        return new URL(String.format(SearchFormat,year, BuildConfig.APIKEY));
    }


}
