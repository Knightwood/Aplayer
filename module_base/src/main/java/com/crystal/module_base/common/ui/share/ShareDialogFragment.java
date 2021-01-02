package com.crystal.module_base.common.ui.share;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.module_base.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShareDialogFragment extends BottomSheetDialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public static ShareDialogFragment newInstance(String param1) {
        ShareDialogFragment fragment = new ShareDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_dialog, container, false);
    }
    public void show(Context context){

    }
}
