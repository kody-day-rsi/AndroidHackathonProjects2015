package rsi.com.applicationstub.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dagger.ObjectGraph;
import rsi.com.applicationstub.DaggerModule;
import rsi.com.applicationstub.R;
import rsi.com.applicationstub.event.FABEvent;
import rsi.com.applicationstub.view.JobListFragment;
import rsi.com.applicationstub.view.SearchJobFragment;

public class MainActivity extends AppCompatActivity {

    private ObjectGraph mObjectGraph;

    @Inject
    Bus mEventBus;

    private static String LIST_TAG = "DON'T LEAVE PLEASE";

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        mObjectGraph = ObjectGraph.create(new DaggerModule(this));
        mObjectGraph.inject(this);

        setContentView(R.layout.activity_main);
        FloatingActionButton addJobButton = (FloatingActionButton) findViewById(R.id.addJobButton);
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventBus.post(new FABEvent());
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.JobListFragmentContainer, new JobListFragment(), LIST_TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_job_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_search:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.JobListFragmentContainer, new SearchJobFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}
