package usst.lei.movieshow;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class AllMoviesActivityFragment extends Fragment {


    public AllMoviesActivityFragment() {
    }

    class  LoadUrlTask extends AsyncTask<String,Void,String>{
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

    class UrlImageAdapter extends ArrayAdapter<String>{
        int resouceID;
        public UrlImageAdapter(Context context, int resource) {
            super(context, resource);
            resouceID=resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=convertView;
            ViewHolder holder;
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

            Picasso.with(getContext()).load(getItem(position)).resize(getContext().getApplicationContext().getResources().getDisplayMetrics().widthPixels / 2, getContext().getApplicationContext().getResources().getDisplayMetrics().heightPixels/2).into(holder.imageView);
            return v;

        }
        class  ViewHolder{
            public ImageView imageView;
        }
    }
    UrlImageAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_all_movies, container, false);
        GridView gv= (GridView) v.findViewById(R.id.main_gridview);
             adapter=new UrlImageAdapter(getActivity(),R.layout.grid_list_item);
        gv.setAdapter(adapter);
        LoadUrlTask task=new LoadUrlTask();
        task.execute("2014");
        return v;
    }


}
