package usst.lei.movieshow;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import usst.lei.movieshow.data.SQLHepler.T_myfa;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }
    Utils.MovieObject obj;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent i = getActivity().getIntent();
        int pos = i.getIntExtra(AllMoviesActivityFragment.POSITION, -1);
        if (pos >= 0) {
           obj = Models.instance.getMovieObjects().get(pos);
            TextView tvTitle = (TextView) v.findViewById(R.id.tv_detail_moive_tilte);
            tvTitle.setText(obj.getName());
            final ImageView ivPoster = (ImageView) v.findViewById(R.id.image_detail_poster);
            Volley.newRequestQueue(getActivity().getApplicationContext()).add(new ImageRequest(Constants.getImageUrlString(obj.getPostImagurl()), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    ivPoster.setImageBitmap(response);
                }
            }, 200, 200, ImageView.ScaleType.CENTER_INSIDE, null, null));
            TextView tvYeatandvote = (TextView) v.findViewById(R.id.tv_detail_yearandvote);
            tvYeatandvote.setText(getActivity().getSharedPreferences("default", Context.MODE_PRIVATE).getInt("year",2014) + "\n" + obj.getRate()+"/10");
            TextView tvDescription= (TextView) v.findViewById(R.id.tv_detail_description);
            tvDescription.setText(obj.getIntroduce());
            final TextView tvlike= (TextView) v.findViewById(R.id.tv_detail_likeOrnot);
            tvlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvlike.getText().toString().equals(getString(R.string.like)))
                    {
                        tvlike.setText(R.string.dislike);
                        tvlike.setBackgroundResource(R.color.dislike);
                    }
                    else
                    {
                        tvlike.setText(R.string.like);
                        tvlike.setBackgroundResource(R.color.like);
                        ContentValues values=new ContentValues();
                        values.put(T_myfa.TITLE,obj.getName());
                        values.put(T_myfa.DESCRIPTION,obj.getIntroduce());
                        values.put(T_myfa.POSTERPATH,obj.getPostImagurl());
                        values.put(T_myfa.VOTEAVE,obj.getRate());
                        values.put(T_myfa.YEAR, obj.getYear());
                   Log.e("task","insert"+ getActivity().getContentResolver().insert(Uri.parse(T_myfa.Urimake()),values));
                    }
                }
            });
        }

        return v;
    }
}
