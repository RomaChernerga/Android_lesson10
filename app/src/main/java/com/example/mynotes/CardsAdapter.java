package com.example.mynotes;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    public static final String TAG = "Card Adapter";
    private final CardSource source;
    private final Activity activity;
    private onCardClickListener clickListener ;
    private int menuPosition = -1;  // задаем вводную для menuPosition

    public CardsAdapter(Activity activity, CardSource source) {
        this.source = source;
        this.activity = activity;
    }

    public void setClickListener(onCardClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public int getMenuPosition() {  // для последующего вызова MenuPosition
        return menuPosition;
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

        holder.bind(source.getCard(position));

        Log.d(TAG, "onBindViewHolder" + position);
    }

    @Override
    public int getItemCount() {
        return source.size();
    }

     class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title = itemView.findViewById(R.id.title);
        CheckBox like = itemView.findViewById(R.id.like);
        ImageView image = itemView.findViewById(R.id.imageView);



        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            activity.registerForContextMenu(itemView);  //в него передаем контекстное меню
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        void bind (Card card){
            title.setText(card.getTitle());
            like.setChecked(card.isLike());
            image.setImageResource(card.getImage());
            image.setOnClickListener(v -> {  //клик по картинке
                if(clickListener != null) {
                    clickListener.onCardClick(v, getAdapterPosition());
                }
            });
            // для каждого View Holder регестрируем разное контекстное меню
            image.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();  // должны сохранить позицию
                itemView.showContextMenu(100,100); // отступ от края при открытии контекстного меню
                return true;
            });  // длинное нажатие
        }
    }

    interface onCardClickListener {
        void onCardClick(View view, int position);
    }


}
