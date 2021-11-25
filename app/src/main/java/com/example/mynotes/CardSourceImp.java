package com.example.mynotes;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardSourceImp implements CardSource {

    private final List<Card>cardList;  // инициализируем

    public CardSourceImp(@NonNull Context context) {  // передаем контекст
        this.cardList = new ArrayList<>( Arrays.asList(
                new Card(context.getString(R.string.title1), R.drawable.note), // создаем стоки с информацийми по картинкам
                new Card(context.getString(R.string.title2), R.drawable.note),
                new Card(context.getString(R.string.title3), R.drawable.note),
                new Card(context.getString(R.string.title4), R.drawable.note),
                new Card(context.getString(R.string.title5), R.drawable.note),
                new Card(context.getString(R.string.title6), R.drawable.note),
                new Card(context.getString(R.string.title7), R.drawable.note),
                new Card(context.getString(R.string.title8), R.drawable.note),
                new Card(context.getString(R.string.title9), R.drawable.note)
        ));
    }


    // РЕАЛИЗОВЫВАЕМ МЕТОДЫ
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
    }

    @Override
    public void updateCard(int position, Card card) {
        cardList.set(position, card);
    }

    @Override
    public void addCard(Card card) {
        cardList.add(card);
    }

    @Override
    public void clearCards(Card card) {
        cardList.clear();
    }

    @Override
    public void clearCards() {
        cardList.clear();
    }

}
