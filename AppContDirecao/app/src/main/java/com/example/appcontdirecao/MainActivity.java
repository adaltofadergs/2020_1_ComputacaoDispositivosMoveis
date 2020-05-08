package com.example.appcontdirecao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTopo, tvBase, tvEsquerda, tvDireita;
    private LinearLayout tela;
    private int contTopo = 0, contBase = 0,contEsquerda = 0,contDireita = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTopo = findViewById(R.id.tvTopo);
        tvBase = findViewById(R.id.tvBase);
        tvEsquerda = findViewById(R.id.tvEsquerda);
        tvDireita = findViewById(R.id.tvDireita);

        tela = findViewById(R.id.tela);

        tela.setOnTouchListener( new OnSwipeTouchListener(this){

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                contTopo++;
                tvTopo.setText( String.valueOf( contTopo ) );
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                contBase++;
                tvBase.setText( String.valueOf( contBase ) );
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                contEsquerda++;
                tvEsquerda.setText( String.valueOf( contEsquerda ) );
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                contDireita++;
                tvDireita.setText( String.valueOf( contDireita ) );
            }
        });



    }
}
