package com.fpoly.managebookings.tool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fpoly.managebookings.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DialogSelectDateTime extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.date_picker_bottom_sheet, container, false);

        return view;
    }
}
