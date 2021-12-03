package com.fpoly.managebookings.tool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fpoly.managebookings.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class DialogSelectDate extends BottomSheetDialogFragment{
    private View view;
    private NumberPicker dayPicker, monthPicker, yearPicker;
    private ImageView btn_cancel_filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_date, container, false);

        initView();
        setDataOfDate();
        btn_cancel_filter.setOnClickListener(view1 -> {
            dismiss();
        });

        return view;
    }

    private void setDataOfDate() {
        Calendar cal = Calendar.getInstance();


        dayPicker.setMinValue(1);
        dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));
        dayPicker.setMaxValue(30);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (picker.getValue() == 2) {
                    dayPicker.setMaxValue(28);
                } else if (picker.getValue() == 4 || picker.getValue() == 6 || picker.getValue() == 9 || picker.getValue() == 11) {
                    dayPicker.setMaxValue(30);
                } else {
                    dayPicker.setMaxValue(31);
                }

            }
        });

        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(2040);
        yearPicker.setValue(cal.get(Calendar.YEAR));

    }

    private void initView() {
        dayPicker = (NumberPicker) view.findViewById(R.id.day_picker);
        monthPicker = (NumberPicker) view.findViewById(R.id.month_picker);
        yearPicker = (NumberPicker) view.findViewById(R.id.year_picker);
        btn_cancel_filter = view.findViewById(R.id.btn_cancel_filter);
    }

}
