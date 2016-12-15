package net.test.fmaghi.centifoglia;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

//    private AppInfoAdapter appInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showList = (Button) findViewById(R.id.button_list);
//        Intent intent = new Intent(this, AppListActivity.class);
//        startActivity(intent);
    }
    public void openList(View view){
        Intent intent = new Intent(this, AppListActivity.class);
        startActivity(intent);
    }

    public void centifoglia() {

        Button showBubble = (Button) findViewById(R.id.button_bubble);
        showBubble.setText("");
    }
}
