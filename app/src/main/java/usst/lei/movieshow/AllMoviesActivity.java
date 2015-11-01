package usst.lei.movieshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;


public class AllMoviesActivity extends  ActionBarActivity {

    DrawerLayout mDrawer;
    android.support.v4.app.ActionBarDrawerToggle toggle;

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Models.instance.setYear(getSharedPreferences("default", MODE_PRIVATE).getInt("year", 2014));

        setContentView(R.layout.activity_all_movies);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.allmoviesactivity_contain, new AllMoviesActivityFragment()).commit();
        }
        ListView drawList = (ListView) findViewById(R.id.main_draw);
        mDrawer = (DrawerLayout) findViewById(R.id.draw_layout);


        toggle = new android.support.v4.app.ActionBarDrawerToggle(this, mDrawer, R.mipmap.ic_launcher, R.string.drawopen, R.string.drawcolse) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle("Choose");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle("All Moives");
                invalidateOptionsMenu();
            }
        };
        mDrawer.setDrawerListener(toggle);
        try {


            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_launcher));

        } catch (Exception e) {
        }



        drawList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_draw_items, R.id.draw_title, getResources().getStringArray(R.array.drawtitle)));
        drawList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AllMoviesActivity.this);
                    final NumberPicker picker = new NumberPicker(AllMoviesActivity.this);
                    picker.setMaxValue(2015);
                    picker.setMinValue(1970);
                    picker.setValue(getSharedPreferences("default", MODE_PRIVATE).getInt("year", 2014));
                    picker.setHovered(true);
                    builder.setView(picker).setTitle(getString(R.string.choseyear)).setNegativeButton(getString(R.string.cancle), null).setPositiveButton(getString(R.string.certain), new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = getSharedPreferences("default", MODE_PRIVATE).edit();
                            editor.putInt("year", picker.getValue());
                            editor.commit();
                            getSupportFragmentManager().beginTransaction().add(R.id.allmoviesactivity_contain, new AllMoviesActivityFragment()).commit();
                        }
                    }).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
