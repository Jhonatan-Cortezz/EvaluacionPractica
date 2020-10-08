package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarCategoria extends AppCompatActivity implements View.OnClickListener {
    private EditText edtCodigo, edtNombre, edtEstado;
    private Button btnGuardar;

    private boolean inputEt = false;
    private boolean inputEd = false;
    private boolean input1 = false;

    Conexion conexion = new Conexion(this);
    Categoria categoria = new Categoria();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_categoria);
        setTitle("Registro de Categorias");

        edtCodigo = findViewById(R.id.codCate);
        edtNombre = findViewById(R.id.nomCat);
        edtEstado = findViewById(R.id.estadoCat);

        btnGuardar = findViewById(R.id.registraCat);

        //here a logic for update registrer previus
        String senal = "";
        String codigo = "";
        String nombre = "";
        String estado = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                estado = bundle.getString("estado");

                if (senal.equals("1")){
                    edtCodigo.setText(codigo);
                    edtNombre.setText(nombre);
                    edtEstado.setText(estado);
                    btnGuardar.setText("ACTUALIZAR");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edtCodigo.getText().toString().length() == 0){
            edtCodigo.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }

        if (edtNombre.getText().toString().length() == 0){
            edtNombre.setError("Campo obligatorio");
            inputEd = false;
        } else {
            inputEd = true;
        }

        if (edtEstado.getText().toString().length() == 0){
            edtEstado.setError("Campo obligatorio");
            input1 = false;
        } else {
            input1 = true;
        }

        if (inputEt && inputEd && input1) {
            try {
                categoria.setCodigo(Integer.parseInt(edtCodigo.getText().toString()));
                categoria.setNombreCategoria(edtNombre.getText().toString());
                categoria.setEstado(Integer.parseInt(edtEstado.getText().toString()));

                if (conexion.insertTrad(categoria)) {
                    Toast.makeText(this, "Registro Agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ListViewCategorias.class);
                    startActivity(intent);
                }else if(btnGuardar.getText().toString().equals("ACTUALIZAR")){
                    conexion.modifcar(categoria);
                    Toast.makeText(this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ListViewCategorias.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error ya existe un registro con el mismo codigo", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Error, ya existe", Toast.LENGTH_SHORT).show();
            }
        }
    }
}