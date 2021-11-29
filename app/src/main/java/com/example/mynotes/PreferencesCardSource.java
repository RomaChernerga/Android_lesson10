package com.example.mynotes;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class PreferencesCardSource implements CardSource{

    private static final String CARD_DATA = "CARD_DATA";
    public SharedPreferences sharedPreferences;
    private List<Card>cardList;

    public PreferencesCardSource(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        fetch();
    }

    @Override
    public Card getCard(int position) {
        return cardList.get(position);
    }

    @Override
    public int size() {
        return cardList.size();
    }

    @Override
    public void deleteCard(int position) {
        cardList.remove(position);
        update();
    }

    @Override
    public void updateCard(int position, Card card) {
        cardList.set(position, card);
        update();
    }

    @Override
    public void addCard(Card card) {
        cardList.add(card);
        update();

    }

    @Override
    public void clearCards(Card card) {
        cardList.clear();

    }

    @Override
    public void clearCards() {
        cardList.clear();
    }

    private void fetch() { // для сохранения данных
        String json = sharedPreferences.getString(CARD_DATA, null); //превращает элемент в строку
        if(json == null) {
            cardList = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Card>>() {}.getType(); // создание типа
            cardList = new GsonBuilder().create().fromJson(json, type);
        }

    }

    public void update() {  // для загрузки данных

        String json = new GsonBuilder().create().toJson(cardList); //превращает элемент в строку
        sharedPreferences.edit()
                .putString(CARD_DATA, json)
                .apply();

    }
}
