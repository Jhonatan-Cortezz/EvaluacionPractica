package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

public class ListViewCategorias extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewCategoria;
    private ArrayAdapter adapter;
    private SearchView searchView;
    private Button newCat;

    Conexion conexion = new Conexion(this);
    Categoria categoria = new Categoria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_categorias);

        listViewCategoria = findViewById(R.id.lstCategorias);
        searchView = findViewById(R.id.searchViewCat);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.consulataCategorias1());
        listViewCategoria.setAdapter(adapter);
        newCat = findViewById(R.id.newCategoria);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String tex = s;
                adapter.getFilter().filter(tex);
                return false;
            }
        });

        listViewCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String informacion = "Código: "+ conexion.consultaListaArtSpiner().get(i).getCodigo()+ "\n";
                informacion += "Nombre: " + conexion.consultaListaArtSpiner().get(i).getNombreCategoria() + "\n";
                informacion += "Estado: " + conexion.consultaListaArtSpiner().get(i).getEstado();


                Categoria categ = conexion.consultaListaArtSpiner().get(i);
                Intent intent = new Intent(ListViewCategorias.this, DetalleCategoria.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("categoria", categ);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        newCat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RegistrarCategoria.class);
        startActivity(intent);
    }
}x