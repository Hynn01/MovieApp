package cuc.edu.cn.hynnsapp02.ui.pageViewers_fragement3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cuc.edu.cn.hynnsapp02.R;

public class PageViewers_documentary extends PageViewers_action  {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pageviewers_action, container, false);
        return view;
    }

}
