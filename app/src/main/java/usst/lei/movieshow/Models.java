package usst.lei.movieshow;

import java.util.ArrayList;

/**
 * Created by Lei on 2015/10/31.
 */
public class Models {
    public static Models instance;
    static
    {
        instance=new Models();
    }
    private ArrayList<Utils.MovieObject> movieObjects;

    public void setMovieObjects(ArrayList<Utils.MovieObject> movieObjects) {
        this.movieObjects = movieObjects;
    }

    public ArrayList<Utils.MovieObject> getMovieObjects() {
        return movieObjects;
    }
}
