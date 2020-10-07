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

public class ListViewProductos extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewProductos;
    private ArrayAdapter adapter;
    private SearchView searchViewProd;
    private Button newProd;

    Conexion conexion = new Conexion(this);
    Producto producto = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_productos);

        listViewProductos = findViewById(R.id.lstProducto);
        searchViewProd = findViewById(R.id.searchViewProd);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.consulataProducto1());
        listViewProductos.setAdapter(adapter);
        newProd = findViewById(R.id.newProducto);

        searchViewProd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String informacion = "Código: "+ conexion.consultaListaArtSpinerProducto().get(i).getCodigo()+ "\n";
                informacion += "Nombre: " + conexion.consultaListaArtSpinerProducto().get(i).getNombreProducto() + "\n";
                informacion += "Apellido: " + conexion.consultaListaArtSpinerProducto().get(i).getDescripcion() + "\n";
                informacion += "Correo: " + conexion.consultaListaArtSpinerProducto().get(i).getStock() + "\n";
                informacion += "Usuario: " + conexion.consultaListaArtSpinerProducto().get(i).getPrecio() + "\n";
                informacion += "Clave: " + conexion.consultaListaArtSpinerProducto().get(i).getUnidadMedida() + "\n";
                informacion += "Estado: " + conexion.consultaListaArtSpinerProducto().get(i).getEstadoProd()  + "\n";
                informacion += "Pregunta: " + conexion.consultaListaArtSpinerProducto().get(i).getCategoria();

                Producto user = conexion.consultaListaArtSpinerProducto().get(i);
                Intent intent = new Intent(ListViewProductos.this, DetalleProducto.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("producto", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        newProd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RegistrarProducto.class);
        startActivity(intent);
    }

}