package com.hackathon.loginemergency;

import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;
import com.google.android.glass.app.Card;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class CardsScroll extends Activity {

    private List<Card> mCards;
    private CardScrollView mCardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createCards();

        mCardScrollView = new CardScrollView(this);
        ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
    }

    private void createCards() {
        mCards = new ArrayList<Card>();

        Card card;
        
        card = new Card(this);
        card.setText("Jorge Lavin");
        card.setFootnote("Primeros auxilios");
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.addImage(R.drawable.yo);
        mCards.add(card);

        card = new Card(this);
        card.setText("Alvaro Sanchez.");
        card.setFootnote("Conductor ambulancia");
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.addImage(R.drawable.alvaro);
        mCards.add(card);

        card = new Card(this);
        card.setText("Antonio Sanchez");
        card.setFootnote("Tecnico especialista");
        card.setImageLayout(Card.ImageLayout.LEFT);
        card.addImage(R.drawable.antonio);
        mCards.add(card);

 
    }

    private class ExampleCardScrollAdapter extends CardScrollAdapter {

        @Override
        public int getPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        /**
         * Returns the amount of view types.
         */
        @Override
        public int getViewTypeCount() {
            return Card.getViewTypeCount();
        }

        /**
         * Returns the view type of this card so the system can figure out
         * if it can be recycled.
         */
        @Override
        public int getItemViewType(int position){
            return mCards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView,
                ViewGroup parent) {
            return  mCards.get(position).getView(convertView, parent);
        }
    }
}