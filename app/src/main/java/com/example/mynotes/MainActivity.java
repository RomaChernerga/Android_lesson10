package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

public class MainActivity extends AppCompatActivity {


    private CardSource source;
    private CardsAdapter adapter;
    private RecyclerView recyclerView;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();


        recyclerView = findViewById(R.id.recycler_view);
        source = new CardSourceImp(this);
        adapter = new CardsAdapter(this, source); // передаем контекст и ресурс
        adapter.setClickListener((view, position) -> {



            currentPosition = position; // сохранение картинки в данной позиции
            showBody(position);
//            updateBackground();



            Toast.makeText(this, "Редактирование " + source.getCard(position).getTitle(),  Toast.LENGTH_SHORT).show();

        });
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1; // если портр режим - 1 столбец отображения, иначе по 2
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL); //разделитель элементов
        dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(this, R.drawable.separator));  // создаем Декоратор для разделения рисунков
        ScaleInBottomAnimator animator = new ScaleInBottomAnimator(); // создаем аниматор для анимаций

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);  // запускаем декоратор
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false); // чтобы все элементы списка были одного размера
        recyclerView.setItemAnimator(animator);  // Добавляем аниматор

    }


    void showBody(int position) {
        BodyNotesFragment bodyNotesFragment = BodyNotesFragment.newInstance(position);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide, R.anim.slide)
                .add(R.id.fragment_container1,bodyNotesFragment)
                .addToBackStack(null)
                .commit();
    }

    private void initToolbar() {  // метод для ActionBar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(  //сэндвич
                this,
                drawerLayout,
//                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView  = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {   // для работы кнопок в шторке

            int id = item.getItemId();
            if (id == R.id.action_about) {

                getSupportFragmentManager() // обработчик кнопки
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id. fragment_container1, new AboutFragment())
                        .commit();
                drawerLayout.closeDrawers();  // для закрытия шторки
                return true;

            } else if (id == R.id.action_back) {
                onBackPressed();        // обработчик кнопки
                drawerLayout.closeDrawers();   // для закрытия шторки
                return true;
            } else if (id == R.id.button_exit) {
                new AlertDialog.Builder(this)
                        .setMessage("Вы действительно хотите закрыть приложение ?")
                        .setIcon(R.drawable.icon_info)
                        .setPositiveButton("Да", (dialog, which) -> {
                            finish(); // обработчик кнопки
                            Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Нет", (dialog, which) -> {
                            Toast.makeText(this, "Продолжаем работу", Toast.LENGTH_SHORT).show();
                        }).show();
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {  // метод для возврата назад по кнопке

        if (getSupportFragmentManager().getFragments().size() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  //для отображения меню
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // метод для меню который появляется при нажатии на заметку
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.card_menu, menu); //регестрируем контекстное меню(заинфлейтели))
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {  // метод для контекстного меню
        if(item.getItemId() == R.id.action_delete) { // для удаления карточки
            source.deleteCard(adapter.getMenuPosition());
            adapter.notifyItemRemoved(adapter.getMenuPosition()); // ОЧень важно! мы уведомляем адаптер об изменениях
            return true;
        } else if(item.getItemId() == R.id.action_update) { // для обновления
            source.updateCard(adapter.getMenuPosition(),
                    new Card("Новая карточка", R.drawable.note ));
            adapter.notifyItemChanged(adapter.getMenuPosition()); // ОЧень важно! мы уведомляем адаптер об изменениях
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //для работы меню
        int id = item.getItemId();

        if (id == R.id.action_about) {
            getSupportFragmentManager() // обработчик кнопки
                    .beginTransaction()
                    .addToBackStack("")
                    .add(R.id. fragment_container1, new AboutFragment())
                    .commit();
            return true;

        } else if (id == R.id.action_back) {
            onBackPressed();        // обработчик кнопки
            return true;

        } else if(item.getItemId() == R.id.action_add) {
            source.addCard(
                    new Card("Новая карточка", R.drawable.note ) // для создания новой карточки
            );
            adapter.notifyItemInserted(source.size() - 1); // уведомляем адаптер об изменении
            recyclerView.scrollToPosition(source.size() - 1);
            return true;

        } else if(item.getItemId() == R.id.action_clear) {
            int size = source.size(); // выявляем колличество ячеек
            source.clearCards();
            adapter.notifyItemRangeRemoved(0,size);// Уведомляем адаптер об изменении числа заметок
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}