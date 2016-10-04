package com.lhs.sensorgui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhs.sensorgui.R;

/**
 * Created by Dragan on 9/29/2016.
 */
public class CycleProFragment extends Fragment {

    public CycleProFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CycleProFragment newInstance(int sectionNumber) {
        CycleProFragment fragment = new CycleProFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cyc_pro, container, false);

        return rootView;
    }
}
