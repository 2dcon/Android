package net.test.fmaghi.centifoglia;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fmaghi on 12/13/16.
 */

public class AppInfoAdapter extends ArrayAdapter<ApplicationInfo> {
    private Context context;
    private List<ApplicationInfo> appsList = null;
    private PackageManager pm;
    private ArrayList<Boolean> checkedList;

    public AppInfoAdapter(Context context, int textViewResourceId, List<ApplicationInfo> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        this.pm = context.getPackageManager();
        this.appsList = list;
        this.checkedList = new ArrayList<Boolean>(Collections.nCopies(list.size(), false));
    }

    /**
     * check or uncheck
     * @param position
     */
    public void switchState(int position){
        this.checkedList.set(position,!checkedList.get(position));
    }

    public void reset(){
        Collections.fill(this.checkedList, false);
    }

    /**
     * @return list of checked apps
     */
    public ArrayList<ApplicationInfo> getChecked(){
        ArrayList<ApplicationInfo> checked = new ArrayList<ApplicationInfo>();

        for(int i = 0; i<this.appsList.size(); i++){
            if(this.checkedList.get(i)){
                checked.add(appsList.get(i));
            }
        }

        return checked;
    }

    public int checkCount(){
        int count = 0;
        for(int i = 0; i<this.appsList.size(); i++){
            if(this.checkedList.get(i)){
                ++count;
            }
        }
        return count;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        ApplicationInfo applicationInfo = appsList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.app_info, null);

        if(applicationInfo != null){
            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
            TextView appName = (TextView) view.findViewById(R.id.app_name);

            appName.setText(applicationInfo.loadLabel(pm));
            iconview.setImageDrawable(applicationInfo.loadIcon(pm));
            if(checkedList.get(position)){
                view.setBackgroundColor(0xffcccccc);
            }
        }

        return view;
    }

    /**
     * load the checked item last time
     * by finding the apps who's package names are contained in the passed list.
     * @param ret an arraylist contains package names
     * @return number of items loaded
     */
    public int loadList(ArrayList<String> ret) {
        int count = 0;
        for(int i = 0, j = 0; i < ret.size(); i++){
            for(int k = j; k < appsList.size(); k++){
                if(appsList.get(k).packageName.equals(ret.get(i))){
                    this.checkedList.set(k,true);
                    j = k;
                    count ++; //test
                    break;
                }
            }

        }
        return count;
    }

}
