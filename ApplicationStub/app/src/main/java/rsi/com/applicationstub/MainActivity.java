package rsi.com.applicationstub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dagger.ObjectGraph;
import rsi.com.applicationstub.event.FABEvent;

public class MainActivity extends AppCompatActivity {

    private ObjectGraph mObjectGraph;

    @Inject
    Bus mEventBus;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        mObjectGraph = ObjectGraph.create(new ServiceModule(this));
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
                .add(R.id.JobListFragmentContainer, new JobListFragment())
                .commit();
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}
