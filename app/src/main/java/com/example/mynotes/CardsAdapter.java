package com.example.mynotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    public static final String TAG = "Card Adapter";
    private final List<Card>cardList;
    private onCardClickListener clickListener ;

    public CardsAdapter(List<Card> cardList) {  // конструктор для списка элементов
        this.cardList = cardList;
    }

    public void setClickListener(onCardClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_layout, parent, false); // создание View Холдера
        Log.d(TAG, "onCreateViewHolder");
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        holder.bind(cardList.get(position));

        Log.d(TAG, "onBindViewHolder" + position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

     class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title = itemView.findViewById(R.id.title);
        CheckBox like = itemView.findViewById(R.id.like);
        ImageView image = itemView.findViewById(R.id.imageView);

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind (Card card){
            title.setText(card.getTitle());
            like.setChecked(card.isLike());
            image.setImageResource(card.getImage());
            image.setOnClickListener(v -> {  //клик по картинке
                if(clickListener != null) {
                    clickListener.onCardClick(v, getAdapterPosition());
                }
            });
        }
    }

    interface onCardClickListener {
        void onCardClick(View view, int position);
    }


}
