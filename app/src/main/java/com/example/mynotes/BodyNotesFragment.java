package com.example.mynotes;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BodyNotesFragment extends Fragment {

    private static final String ARG_POSITION = "ARG_POSITION";
    private int position = -1;  // позиция по умолчанию
    private TextView source;


    public BodyNotesFragment() {
        // Required empty public constructor
    }

    public static BodyNotesFragment newInstance(int position) {
        BodyNotesFragment fragment = new BodyNotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setSubtitle("Редактирование заметки");
        return inflater.inflate(R.layout.fragment_body_notes, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuItem menuItem = menu.findItem(R.id.action_about);
        if(menuItem != null){
            menuItem.setVisible(false); // скрываем меню
        }

        menu.add(Menu.NONE, 10, Menu.NONE,"CHANGE TEXT");  // новый элемент в меню

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button button_exit = view.findViewById(R.id.button_exit);    // клик по кнопке выход
        button_exit.setOnClickListener(v -> {
            new AlertDialog.Builder(requireActivity())
                    .setMessage("Вы действительно хотите закрыть приложение ?")
//                    .setTitle("Title")
                    .setIcon(R.drawable.icon_info)
                    .setPositiveButton("Да", (dialog, which) -> {
                        requireActivity().finish(); // обработчик кнопки

                        Toast.makeText(requireActivity(), "Приложение закрыто", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Нет", (dialog, which) -> {
                        Toast.makeText(requireActivity(), "Продолжаем работу", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        super.onViewCreated(view, savedInstanceState);

        EditText body_note = view.findViewById(R.id.body_note);  // текстовое поле заметки
        body_note.setBackgroundColor(Color.WHITE);       // текстовое поле заметки
        body_note.setTypeface(Typeface.SERIF);       // текстовое поле заметки
        body_note.setTextSize(20);       // текстовое поле заметки


        TextView note_name = view.findViewById(R.id.note_name);
        note_name.setText("Заметка # " + position);           // исправить строчку кода!!!


        note_name.setTextSize(16);
        note_name.setTypeface(Typeface.DEFAULT_BOLD);
        note_name.setTextColor(Color.BLACK);


        Button note_save = view.findViewById(R.id.note_save);  // для сохранения заметки(код будет в будущем )))
        note_save.setOnClickListener(v -> {
            saveText();
        });

        Button text_clear = view.findViewById(R.id.text_clear);  // для сохранения заметки(код будет в будущем )))
        text_clear.setOnClickListener(v -> {
            body_note.setText("");
        });
    }
    private void saveText() {



    }



}