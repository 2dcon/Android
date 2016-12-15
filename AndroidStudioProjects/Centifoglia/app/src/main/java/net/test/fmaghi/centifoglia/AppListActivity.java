package net.test.fmaghi.centifoglia;

import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A list that displays all installed apps, the list of selected app can be saved
 */
public class AppListActivity extends ListActivity {

    private PackageManager pm;
    private List<ApplicationInfo> ai;
    private AppInfoAdapter appInfoAdapter;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        pm = getPackageManager();
        ai = pm.getInstalledApplications(0);
        appInfoAdapter = new AppInfoAdapter(this, R.layout.app_info, ai);

        loadSaved(this);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(appInfoAdapter);

    }

    /**
     * when clicked, set unselected apps selected, vice versa
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        appInfoAdapter.switchState(position);
        //refresh!
        this.refreshList();
    }

    public ArrayList<String> saveNQuit (View view){
//        writeToFile(this.appInfoAdapter, this);

        this.finish();
        return loadSaved(this);
    }

    public void clearSelection(View view){
        this.appInfoAdapter.reset();
        this.refreshList();
    }

    private void refreshList(){
        TextView txt = (TextView)findViewById(R.id.tv);
        txt.setText(appInfoAdapter.checkCount() + " app(s) selected");
        appInfoAdapter.notifyDataSetChanged();
    }

    /**
     * Save file, copied from Internet
     * @param aia
     * @param context
     */
    private void writeToFile(AppInfoAdapter aia,Context context) {
        try {
            if(aia.checkCount() > 0) {
                //get checked list
                ArrayList<ApplicationInfo> list = aia.getChecked();
                String str = new String();

                for (int i = 0; i < list.size(); i++) {
                    str = str + list.get(i).packageName + '\n';
                }

                System.out.print(str);//test

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(str);
                outputStreamWriter.close();
            } else {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
                outputStreamWriter.close();
            }

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * read file
     * @param context
     * @return
     */
    private ArrayList<String> loadSaved(Context context) {

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                ArrayList<String> ret = new ArrayList<>();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = new String();
                System.out.print("\nloading data!\n");

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    ret.add(receiveString.replace("\n", ""));
                    System.out.print("\napp loaded: ");//test
                    System.out.print(receiveString);
                }
                System.out.print("\n" + ret.size() + " item(s) loaded\n"); //test
                //pass the arrayList to appinfoadapter
                this.appInfoAdapter.loadList(ret);

                return ret;
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return null;
    }


}
