package com.gokulsundar4545.gokulchat4545;

import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int TabCount;

    public  PageAdapter(FragmentManager fm,int Counttab){

        super(fm);
        this.TabCount=Counttab;
    }








    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                ChatFragment chat=new ChatFragment();
                return chat;

            case 1:
                statusFragment status=new statusFragment();
                return status;

            case 2:
                CallFragment call=new CallFragment();
                return call;

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return TabCount;
    }
}
