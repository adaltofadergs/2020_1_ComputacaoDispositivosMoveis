package com.example.applistafacil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private ListView lvProdutos;
    private List<Produto> listaDeProdutos;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private ArrayAdapter<Produto> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaActivity.this, FormularioActivity.class);
                startActivity( intent );
            }
        });

        lvProdutos = findViewById( R.id.lvProdutos );
        listaDeProdutos = new ArrayList<>();
        adapter = new ArrayAdapter<Produto>(
                ListaActivity.this, android.R.layout.simple_list_item_1, listaDeProdutos);
        lvProdutos.setAdapter( adapter );

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excluir( position );
                return true;
            }
        });

    }

    private void excluir(final int posicao){

        final Produto prodSelecionado = listaDeProdutos.get( posicao );

        AlertDialog.Builder alerta = new AlertDialog.Builder(ListaActivity.this);
        alerta.setTitle("Excluir Produto");
        alerta.setIcon( android.R.drawable.ic_delete );
        alerta.setMessage("Confirma a exclusão do produto " + prodSelecionado.nome + "?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reference.child("produtos").child( prodSelecionado.id ).removeValue();

                listaDeProdutos.remove( posicao );
                adapter.notifyDataSetChanged();
            }
        });
        alerta.show();

    }



    @Override
    protected void onStart() {
        super.onStart();

        listaDeProdutos.clear();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        query = reference.child("produtos").orderByChild("nome");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Produto p = new Produto();
                p.id = dataSnapshot.getKey();
                p.nome = dataSnapshot.child("nome").getValue(String.class);
                p.preco = dataSnapshot.child("preco").getValue(Double.class);

                listaDeProdutos.add( p );

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };



        query.addChildEventListener( childEventListener );
    }

    @Override
    protected void onStop() {
        super.onStop();

        query.removeEventListener( childEventListener );
    }
}
