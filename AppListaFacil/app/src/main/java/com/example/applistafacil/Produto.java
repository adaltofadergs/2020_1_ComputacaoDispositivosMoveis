package com.example.applistafacil;

import androidx.annotation.NonNull;

public class Produto {

    public String id, nome;
    public double preco;

    @NonNull
    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }
}
