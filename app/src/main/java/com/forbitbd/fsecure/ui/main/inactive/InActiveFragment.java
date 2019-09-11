package com.forbitbd.fsecure.ui.main.inactive;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.fsecure.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InActiveFragment extends Fragment {


    public InActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in_active, container, false);
    }

}
