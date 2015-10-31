package usst.lei.movieshow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class AllMoviesActivityFragment extends Fragment {


    public static final String POSITION = "info";

    public AllMoviesActivityFragment() {
    }

  /*  class  LoadUrlTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader bufferedReader=null;
            StringBuilder builder=new StringBuilder();
            String line=null;

            try {
                connection= (HttpURLConnection) Constants.getSearchUrl(Integer.parseInt( params[0])).openConnection();
                bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line=bufferedReader.readLine())!=null)
            {
                builder.append(line);
                builder.append("\n");
            }
            } catch (Exception e) {
               Log.e("task",e.toString());
                return null;
            }
            finally {
                try {
                    connection.disconnect();
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                ArrayList<Utils.MovieObject> movieObjects= Utils.MoiveUrlInfoGetObjects(s);
                for (Utils.MovieObject object : movieObjects)
                {
                    adapter.add(Constants.getImageUrlString(object.getPostImagurl()));
                    Log.e("tastee",Constants.getImageUrlString(object.getPostImagurl()));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
               Log.e("task",e.toString());
            }
        }
    }
*/
    class UrlImageAdapter extends ArrayAdapter<String>{
        int resouceID;
        public UrlImageAdapter(Context context, int resource) {
            super(context, resource);
            resouceID=resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=convertView;
          final   ViewHolder holder;
            if (v==null)
            {
                v=LayoutInflater.from(getContext()).inflate(resouceID,parent,false);
                holder=new ViewHolder();
                holder.imageView= (ImageView) v.findViewById(R.id.grid_list_item_imageview);
                v.setTag(holder);
            }
            else {
                 holder = (ViewHolder) v.getTag();
            }
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(size.x/2,size.y/2);
            holder.imageView.setLayoutParams(params);
            ImageRequest request=new ImageRequest(getItem(position), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    holder.imageView.setImageBitmap(response);
                }
            }, size.x/2, size.y/2, ImageView.ScaleType.FIT_XY,null,null);
            Cache.Entry entry=mainQueue.getCache().get(request.getCacheKey());
            if (entry==null) {
                holder.imageView.setImageResource(R.mipmap.ic_launcher);


           mainQueue.add(request);}
            else
            {
                holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length));
            }

            return v;

        }
        class  ViewHolder{
            public ImageView imageView;
        }
    }
    UrlImageAdapter adapter;
    DiskBasedCache mainCache;
    RequestQueue mainQueue;
    Point size;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_all_movies, container, false);
        GridView gv= (GridView) v.findViewById(R.id.main_gridview);
             adapter=new UrlImageAdapter(getActivity(),R.layout.grid_list_item);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent=new Intent(getActivity(),DetailActivity.class);
                detailIntent.putExtra(POSITION, position);
                startActivity(detailIntent);
            }
        });
      WindowManager manager= (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display= manager.getDefaultDisplay();
        size=new Point();
        display.getSize(size);

//        LoadUrlTask task=new LoadUrlTask();
//        task.execute("2014");
    mainQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        try {
            //利用Volley将网页api的string缓存至本地
            mainQueue.add(new StringRequest(Constants.getSearchUrl(2013), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                       Models.instance.setMovieObjects( Utils.MoiveUrlInfoGetObjects(response));
                        for (Utils.MovieObject obj : Models.instance.getMovieObjects()) {
                            adapter.add(Constants.getImageUrlString(obj.getPostImagurl()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return v;
    }


}
