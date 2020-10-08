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

public class ListViewUsuario extends AppCompatActivity implements View.OnClickListener {

    private ListView listViewUsuario;
    private ArrayAdapter adapter;
    private SearchView searchView;
    private Button newUser;

    Conexion conexion = new Conexion(this);
    Usuario usuario = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_usuario);
        setTitle("Usuarios");

        listViewUsuario = findViewById(R.id.lstUsuario);
        searchView = findViewById(R.id.searchViewUse);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.consulataUsuario1());
        listViewUsuario.setAdapter(adapter);
        newUser = findViewById(R.id.newUsuario);

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

        listViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String informacion = "Código: "+ conexion.consultaListaArtSpinerUsuario().get(i).getCodigoUsuario()+ "\n";
                informacion += "Nombre: " + conexion.consultaListaArtSpinerUsuario().get(i).getNombreUsuario() + "\n";
                informacion += "Apellido: " + conexion.consultaListaArtSpinerUsuario().get(i).getApellido() + "\n";
                informacion += "Correo: " + conexion.consultaListaArtSpinerUsuario().get(i).getCorreo() + "\n";
                informacion += "Usuario: " + conexion.consultaListaArtSpinerUsuario().get(i).getUsuario() + "\n";
                informacion += "Clave: " + conexion.consultaListaArtSpinerUsuario().get(i).getClave() + "\n";
                informacion += "Estado: " + conexion.consultaListaArtSpinerUsuario().get(i).getEstado()  + "\n";
                informacion += "Pregunta: " + conexion.consultaListaArtSpinerUsuario().get(i).getPregunta() + "\n";
                informacion += "Respuesta: " + conexion.consultaListaArtSpinerUsuario().get(i).getRespuesta();

                Usuario user = conexion.consultaListaArtSpinerUsuario().get(i);
                Intent intent = new Intent(ListViewUsuario.this, DetalleUsuario.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", user);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        newUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, RegistraUsuario.class);
        startActivity(intent);
    }
}