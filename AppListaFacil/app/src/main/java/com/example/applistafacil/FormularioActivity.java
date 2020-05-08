package com.example.applistafacil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome, etPreco;
    private Button btnSalvar;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome  = findViewById( R.id.etNomeProduto );
        etPreco  = findViewById( R.id.etPreco );
        btnSalvar = findViewById( R.id.btnSarvar );

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    private void salvar(){
        String nome = etNome.getText().toString();
        String preco = etPreco.getText().toString();

        if( !nome.isEmpty()  && !preco.isEmpty() ){
            preco = preco.replace(",", ".");
            double valor = Double.valueOf( preco );
            Produto produto = new Produto();
            produto.nome = nome;
            produto.preco = valor;

            database = FirebaseDatabase.getInstance();
            reference = database.getReference();
            reference.child("produtos").push().setValue( produto );
            finish();
        }
    }

}
