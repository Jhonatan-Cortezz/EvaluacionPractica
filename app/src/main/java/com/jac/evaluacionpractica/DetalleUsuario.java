package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetalleUsuario extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCodigo, tvNombre, tvApellido, tvCorreo, tvUsuario, tvClave, tvEstado, tvPregunta, tvRespuesta;
    private Button btnEditar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        tvCodigo = findViewById(R.id.tvCodigoUsu);
        tvNombre = findViewById(R.id.tvNombreUsu);
        tvApellido = findViewById(R.id.tvApellidoUsu);
        tvCorreo = findViewById(R.id.tvCorreoUsu);
        tvUsuario = findViewById(R.id.tvUsuarioT);
        tvClave = findViewById(R.id.tvClaveUsu);
        tvEstado = findViewById(R.id.tvEstadoU);
        tvPregunta = findViewById(R.id.tvPregunta);
        tvRespuesta = findViewById(R.id.tvRespuesta);

        btnEditar = findViewById(R.id.btnEditarUsu);
        btnBorrar = findViewById(R.id.btnBorrarUsu);

        Bundle obj =getIntent().getExtras();
        Usuario usu = new Usuario();
        if (obj != null){
            usu = (Usuario) obj.getSerializable("usuario");
            tvCodigo.setText(""+ usu.getCodigoUsuario());
            tvNombre.setText(usu.getNombreUsuario());
            tvApellido.setText(usu.getApellido());
            tvCorreo.setText(usu.getCorreo());
            tvUsuario.setText(usu.getUsuario());
            tvClave.setText(usu.getClave());
            tvEstado.setText(String.valueOf(usu.getEstado()));
            tvPregunta.setText(usu.getPregunta());
            tvRespuesta.setText(usu.getRespuesta());
        }

        btnBorrar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Conexion con = new Conexion(this);
        Usuario use = new Usuario();
        String cod = tvCodigo.getText().toString();
        use.setCodigoUsuario(Integer.parseInt(cod));

        if (view.getId() == R.id.btnBorrarUsu){
            con.bajaCodigoUsuario(DetalleUsuario.this, use);
        } else if (view.getId() == R.id.btnEditarUsu){
            String code = tvCodigo.getText().toString();
            String nombre = tvNombre.getText().toString();
            String apellido = tvApellido.getText().toString();
            String correo = tvCorreo.getText().toString();
            String usuario = tvUsuario.getText().toString();
            String clave = tvClave.getText().toString();
            String estado = tvEstado.getText().toString();
            String pregunta = tvPregunta.getText().toString();
            String respuesta = tvRespuesta.getText().toString();

            Intent intent = new Intent(this, RegistraUsuario.class);
            intent.putExtra("senal", "1");
            intent.putExtra("codigo", code);
            intent.putExtra("nombre", nombre);
            intent.putExtra("apellido", apellido);
            intent.putExtra("correo", correo);
            intent.putExtra("usuario", usuario);
            intent.putExtra("clave", clave);
            intent.putExtra("estado", estado);
            intent.putExtra("pregunta", pregunta);
            intent.putExtra("respuesta", respuesta);
            startActivity(intent);
        }
    }
}