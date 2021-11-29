package com.example.mynotes;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


public class DateFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatePicker mDatePicker;
    private TextView mInfoTextView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DateFragment() {
        // Required empty public constructor
    }


    public static DateFragment newInstance(String param1, String param2) {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        setContentView(R.layout.fragment_date);

//        mInfoTextView = (TextView) findViewById(R.id.date_textView);
//        mDatePicker = (DatePicker) findViewById(R.id.datePicker);

        Calendar today = Calendar.getInstance();

        mDatePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(getApplicationContext(),
//                                "onDateChanged", Toast.LENGTH_SHORT).show();

                        mInfoTextView.setText("Год: " + year + "\n" + "Месяц: "
                                + (monthOfYear + 1) + "\n" + "День: " + dayOfMonth);
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        setContentView(R.layout.fragment_date);

        Calendar today = Calendar.getInstance();

        mDatePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(getApplicationContext(),
//                                "onDateChanged", Toast.LENGTH_SHORT).show();

                        mInfoTextView.setText("Год: " + year + "\n" + "Месяц: "
                                + (monthOfYear + 1) + "\n" + "День: " + dayOfMonth);
                    }
                });

        Button check_date = view.findViewById(R.id.check_date);
        check_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoTextView.setText(new StringBuilder()
                        // Месяц отсчитывается с 0, поэтому добавляем 1
                        .append(mDatePicker.getDayOfMonth()).append(".")
                        .append(mDatePicker.getMonth() + 1).append(".")
                        .append(mDatePicker.getYear()));
            }
        });

        super.onViewCreated(view, savedInstanceState);

        TextView date_textView = view.findViewById(R.id.date_textView);




//        mInfoTextView = (TextView) findViewById(R.id.date_textView);
//        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
    }

    // устанавливаем текущую дату
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCurrentDateOnView() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        mInfoTextView.setText(new StringBuilder()
                // Месяц отсчитывается с 0, поэтому добавляем 1
                .append(day).append(".").append(month + 1).append(".")
                .append(year));

        // Устанавливаем текущую дату для DatePicker
        mDatePicker.init(year, month, day, null);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }
}