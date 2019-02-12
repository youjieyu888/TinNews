

package com.laioffer.tinnews.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laioffer.tinnews.R;

public class ContainerFragment extends TinBasicFragment {
    public static final int HOME_PAGE = 0;
    public static final String HOME_PAGE_TAG = "home_page";
    public static final int SAVE_PAGE = 1;
    public static final String SAVE_PAGE_TAG = "save_page";
    public static final int PROFILE_PAGE = 2;
    public static final String PROFILE_PAGE_TAG = "profile_page";
    private int pageIndex;

    public static ContainerFragment newInstance(int pageIndex) {
        ContainerFragment containerFragment = new ContainerFragment();
        containerFragment.pageIndex = pageIndex;
        return containerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        TextView textView = view.findViewById(R.id.text);
        textView.setText("This is containFragment" + pageIndex);
        return view;
    }

    public static int getPositionById(int id) {
        switch (id) {
            case R.id.action_tin:
                return HOME_PAGE;
            case R.id.action_save:
                return SAVE_PAGE;
            case R.id.action_profile:
                return PROFILE_PAGE;
            default:
                throw new IndexOutOfBoundsException();
        }

    }
}