package usst.lei.movieshow;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lei on 2015/10/28.
 * 工具类
 */
public class Utils {

    public static class MovieObject
    {
        private String name;
        private String introduce;
        private String releaseDate;
        private double rate;
        private int votes;
        private String postImagurl;
        public MovieObject(String title,String intro,String date,double ratevalue,int votenum ,String poster)
        {
            name=title;introduce=intro;releaseDate=date;rate=ratevalue;votes=votenum;postImagurl=poster;
        }

        public String getPostImagurl() {
            return postImagurl;
        }

        public int getVotes() {
            return votes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }

    public static ArrayList<MovieObject> MoiveUrlInfoGetObjects(String s) throws JSONException {
        JSONObject moviesJson=new JSONObject(s);
        JSONArray infos= (JSONArray) moviesJson.get("results");
        ArrayList<MovieObject> objects=new ArrayList<MovieObject>();
        for (int i=0;i<infos.length();i++)
        {
            JSONObject info=infos.getJSONObject(i);

            MovieObject obj=new MovieObject(info.getString("title"),info.getString("overview"),info.getString("release_date"),
                    info.getDouble("vote_average"),info.getInt("vote_count"),info.getString("poster_path"));
            if (obj.rate>7 &&obj.votes>1000)
            {
                objects.add(obj);
            }

        }
        return objects;

    }

}
