package com.fpoly.managebookings.tool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fpoly.managebookings.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class DialogSelectDate extends BottomSheetDialogFragment {
    private View view;
    private NumberPicker dayPicker, monthPicker, yearPicker;
    private ImageView btn_cancel_filter;
    private TextView btn_confirm_filter, tvTitle;
    private String day, month, year, hour, minute;
    private RadioGroup filterRadio;
    private RadioButton rdoDay;
    private RadioButton rdoMonth;
    private RadioButton rdoYear;
    private LinearLayout selectTimePicker,datePickerLayout;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private int isRadioButton, isVisible;
    private String title;

    private ValueOfDatePicker mValueOfDatePicker;

    public interface ValueOfDatePicker {
        void getDateValue(String hour, String minute, String day, String month, String year, int isRadioButton);
    }

    public DialogSelectDate(ValueOfDatePicker mValueOfDatePicker, String title, int isVisible) {
        this.mValueOfDatePicker = mValueOfDatePicker;
        this.isVisible = isVisible;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_date, container, false);

        initView();
        setDataOfDate();

        tvTitle.setText(title);

        switch (isVisible) {
            case 0:
                selectTimePicker.setBottom(20);
                datePickerLayout.setBottom(50);
                filterRadio.setVisibility(View.INVISIBLE);
                break;
            case 1:
                filterRadio.setTop(20);
                datePickerLayout.setTop(50);
                selectTimePicker.setVisibility(View.INVISIBLE);
                break;
        }

        btn_cancel_filter.setOnClickListener(view1 -> {
            dismiss();
        });

        btn_confirm_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayPicker.getValue() < 10) {
                    day = "0" + dayPicker.getValue();
                } else {
                    day = String.valueOf(dayPicker.getValue());
                }

                if (monthPicker.getValue() < 10) {
                    month = "0" + monthPicker.getValue();
                } else {
                    month = String.valueOf(monthPicker.getValue());
                }

                year = String.valueOf(yearPicker.getValue());

                hour = String.valueOf(hourPicker.getValue());

                minute = minutePicker.getDisplayedValues()[minutePicker.getValue()];
                if (rdoDay.isChecked()) {
                    isRadioButton = 0;
                } else if (rdoMonth.isChecked()) {
                    isRadioButton = 1;
                } else if (rdoYear.isChecked()) {
                    isRadioButton = 2;
                }

                mValueOfDatePicker.getDateValue(hour, minute, day, month, year, isRadioButton);


                dismiss();
            }
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
        monthPicker.setDisplayedValues(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
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

        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(24);
        hourPicker.setValue(cal.getTime().getHours());

        minutePicker.setValue(0);
        minutePicker.setMaxValue(11);
        minutePicker.setValue(0);
        minutePicker.setDisplayedValues(new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"});
    }

    private void initView() {
        dayPicker = (NumberPicker) view.findViewById(R.id.day_picker);
        monthPicker = (NumberPicker) view.findViewById(R.id.month_picker);
        yearPicker = (NumberPicker) view.findViewById(R.id.year_picker);
        btn_cancel_filter = view.findViewById(R.id.btn_cancel_filter);
        btn_confirm_filter = view.findViewById(R.id.btn_confirm_filter);
        filterRadio = (RadioGroup) view.findViewById(R.id.filter_radio);
        rdoDay = (RadioButton) view.findViewById(R.id.rdo_day);
        rdoMonth = (RadioButton) view.findViewById(R.id.rdo_month);
        rdoYear = (RadioButton) view.findViewById(R.id.rdo_year);
        selectTimePicker = (LinearLayout) view.findViewById(R.id.select_time_picker);
        hourPicker = (NumberPicker) view.findViewById(R.id.hour_picker);
        minutePicker = (NumberPicker) view.findViewById(R.id.minute_picker);
        tvTitle = view.findViewById(R.id.tvTitleDialog);
        datePickerLayout = view.findViewById(R.id.date_picker_layout);
    }

}
