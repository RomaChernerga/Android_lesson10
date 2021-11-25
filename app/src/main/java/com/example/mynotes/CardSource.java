package com.example.mynotes;

public interface CardSource { // источник данных



    Card getCard(int position);
    int size();
    void deleteCard(int position);
    void updateCard(int position, Card card);
    void addCard(Card card);
    void clearCards(Card card);


    void clearCards();
}
