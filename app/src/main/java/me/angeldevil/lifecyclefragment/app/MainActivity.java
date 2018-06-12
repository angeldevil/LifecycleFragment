package me.angeldevil.lifecyclefragment.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DebugFragment.OnVisibleListener {

    private TabLayout tab;
    private ViewPager pager;
    private ScrollView logScroll;
    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logScroll = findViewById(R.id.log_scroll);
        log = findViewById(R.id.log);

        pager = findViewById(R.id.pager);
        pager.setAdapter(new FirstAdapter(getSupportFragmentManager()));

        tab = findViewById(R.id.tab);
        tab.setupWithViewPager(pager);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LifecycleFragment", "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("LifecycleFragment", "onStart");
    }

    @Override
    public void onVisible(DebugFragment fragment, boolean firstVisible) {
        if (log != null) {
            log.append(fragment.getName() + "--Visible, firstVisible: " + firstVisible + "\n");
            logScroll.fullScroll(View.FOCUS_DOWN);
        }
    }

    @Override
    public void onInvisible(DebugFragment fragment) {
        if (log != null) {
            log.append(fragment.getName() + "--Invisible\n");
            logScroll.fullScroll(View.FOCUS_DOWN);
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (log != null && !log.getText().toString().endsWith("\n\n")) {
            log.append("\n");
        }
    }
}
