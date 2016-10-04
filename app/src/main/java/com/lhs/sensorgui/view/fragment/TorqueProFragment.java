package com.lhs.sensorgui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.TorqueApp;

/**
 * Created by Dragan on 9/29/2016.
 */
public class TorqueProFragment extends Fragment {

    public TorqueProFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TorqueProFragment newInstance(int sectionNumber) {
        TorqueProFragment fragment = new TorqueProFragment();
        Bundle args = new Bundle();
        // args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_torque_pro, container, false);

        return rootView;
    }
}
