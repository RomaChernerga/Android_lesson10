package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Card> cardList = Arrays.asList(
                new Card(getString(R.string.title1), R.drawable.note), // создаем стоки с информацийми по картинкам
                new Card(getString(R.string.title2), R.drawable.note),
                new Card(getString(R.string.title3), R.drawable.note),
                new Card(getString(R.string.title4), R.drawable.note),
                new Card(getString(R.string.title5), R.drawable.note),
                new Card(getString(R.string.title6), R.drawable.note),
                new Card(getString(R.string.title7), R.drawable.note),
                new Card(getString(R.string.title8), R.drawable.note),
                new Card(getString(R.string.title9), R.drawable.note)
        );
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        CardsAdapter adapter = new CardsAdapter(cardList);
        adapter.setClickListener((view, position) -> {

            Toast.makeText(this, "Редактирование " + cardList.get(position).getTitle(),  Toast.LENGTH_SHORT).show();

        });
        //        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 2 : 1; // если портр режим - 1 столбец отображения, иначе по 2
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL); //разделитель элементов
        dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(this, R.drawable.separator));  // создаем Декоратор для разделения рисунков
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);  // запускаем декоратор
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false); // чтобы все элементы списка были одного размера
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
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}