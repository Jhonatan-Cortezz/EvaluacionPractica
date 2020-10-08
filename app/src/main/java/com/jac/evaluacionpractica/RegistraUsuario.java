package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistraUsuario extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCodigo, edtNombre, edtApellido, edtCorreo, edtUsuario, edtClave, edtEstado, edtPregunta, edtRespuesta;
    private Button btnguardar;

    private boolean inputC = false;
    private boolean inputN = false;
    private boolean inputA = false;
    private boolean inputEm = false;
    private boolean inputUs = false;
    private boolean inputCla = false;
    private boolean inputEst = false;
    private boolean inputPre = false;
    private boolean inputRes = false;

    Conexion conexion = new Conexion(this);
    Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_usuario);
        setTitle("Registro de Usuarios");

        edtCodigo = findViewById(R.id.codUse);
        edtNombre = findViewById(R.id.nomUse);
        edtApellido = findViewById(R.id.apellidoUse);
        edtCorreo = findViewById(R.id.correoUsu);
        edtUsuario = findViewById(R.id.tipoUsuario);
        edtClave = findViewById(R.id.clave);
        edtEstado = findViewById(R.id.estadoUsu);
        edtPregunta = findViewById(R.id.pregunta);
        edtRespuesta = findViewById(R.id.respuesta);
        btnguardar = findViewById(R.id.registraUsu);

        //for update
        String senal = "";
        String codi = "";
        String nombre = "";
        String apellido = "";
        String correo = "";
        String usuario = "";
        String clave = "";
        String estado = "";
        String pregunta = "";
        String respuesta = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codi = bundle.getString("codigo");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                apellido = bundle.getString("apellido");
                correo = bundle.getString("correo");
                usuario = bundle.getString("usuario");
                clave = bundle.getString("clave");
                estado = bundle.getString("estado");
                pregunta = bundle.getString("pregunta");
                respuesta = bundle.getString("respuesta");

                if (senal.equals("1")){
                    edtCodigo.setText(codi);
                    edtNombre.setText(nombre);
                    edtApellido.setText(apellido);
                    edtCorreo.setText(correo);
                    edtUsuario.setText(usuario);
                    edtClave.setText(clave);
                    edtEstado.setText(estado);
                    edtPregunta.setText(pregunta);
                    edtRespuesta.setText(respuesta);
                    btnguardar.setText("ACTUALIZAR");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        btnguardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edtCodigo.getText().toString().length() == 0){
            edtCodigo.setError("Campo obligatorio");
            inputC = false;
        } else {
            inputC = true;
        }

        if (edtNombre.getText().toString().length() == 0){
            edtNombre.setError("Campo obligatorio");
            inputN = false;
        } else {
            inputN = true;
        }

        if (edtApellido.getText().toString().length() == 0){
            edtApellido.setError("Campo obligatorio");
            inputA = false;
        } else {
            inputA = true;
        }

        if (edtApellido.getText().toString().length() == 0){
            edtApellido.setError("Campo obligatorio");
            inputA = false;
        } else {
            inputA = true;
        }

        if (edtCorreo.getText().toString().length() == 0){
            edtCorreo.setError("Campo obligatorio");
            inputEm = false;
        } else {
            inputEm = true;
        }

        if (edtUsuario.getText().toString().length() == 0){
            edtUsuario.setError("Campo obligatorio");
            inputUs = false;
        } else {
            inputUs = true;
        }

        if (edtClave.getText().toString().length() == 0){
            edtClave.setError("Campo obligatorio");
            inputCla = false;
        } else {
            inputCla = true;
        }

        if (edtEstado.getText().toString().length() == 0){
            edtEstado.setError("Campo obligatorio");
            inputEst = false;
        } else {
            inputEst = true;
        }

        if (edtPregunta.getText().toString().length() == 0){
            edtPregunta.setError("Campo obligatorio");
            inputPre = false;
        } else {
            inputPre = true;
        }

        if (edtRespuesta.getText().toString().length() == 0){
            edtRespuesta.setError("Campo obligatorio");
            inputRes = false;
        } else {
            inputRes = true;
        }

        if (inputC && inputN && inputA && inputEm && inputUs && inputCla && inputEst && inputPre && inputRes){
            try {
                usuario.setCodigoUsuario(Integer.parseInt(edtCodigo.getText().toString()));
                usuario.setNombreUsuario(edtNombre.getText().toString());
                usuario.setApellido(edtApellido.getText().toString());
                usuario.setCorreo(edtCorreo.getText().toString());
                usuario.setUsuario(edtUsuario.getText().toString());
                usuario.setClave(edtClave.getText().toString());
                usuario.setEstado(Integer.parseInt(edtEstado.getText().toString()));
                usuario.setPregunta(edtPregunta.getText().toString());
                usuario.setRespuesta(edtRespuesta.getText().toString());

                if (conexion.inserarUsuario(usuario)){
                    Toast.makeText(this, "Registro Agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ListViewUsuario.class);
                    startActivity(intent);
                } else if (btnguardar.getText().toString().equals("ACTUALIZAR")){
                    conexion.modifcarUsu(usuario);
                    Toast.makeText(this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ListViewUsuario.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error ya existe un registro con el mismo codigo", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex){
                Toast.makeText(this, "Error, ya existe", Toast.LENGTH_SHORT).show();
            }
        }
    }
}