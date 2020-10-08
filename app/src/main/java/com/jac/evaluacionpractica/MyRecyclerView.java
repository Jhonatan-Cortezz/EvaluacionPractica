package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MyRecyclerView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdaptadorProductos adaptadorProductos;
    Conexion datos = new Conexion(MyRecyclerView.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        setTitle("Consulta RecyclerView");

        recyclerView = findViewById(R.id.rvProd);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptadorProductos = new AdaptadorProductos(MyRecyclerView.this, datos.mostrarProducto());

        recyclerView.setAdapter(adaptadorProductos);
    }
}