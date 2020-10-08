package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUserName, edtPassword;
    private Button btnSing;

    private boolean inputC = false;
    private boolean inputN = false;

    Conexion conexion = new Conexion(this);
    Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Inicio de sesión");

        edtUserName = findViewById(R.id.usernameLog);
        edtPassword = findViewById(R.id.passwordLog);
        btnSing = findViewById(R.id.loginLog);

        btnSing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (edtUserName.getText().toString().length() == 0){
            edtUserName.setError("Por favor ingrese su usuario");
            inputN = false;
        } else {
            inputN = true;
        }

        if (edtPassword.getText().toString().length() == 0){
            edtPassword.setError("Por favor ingrese su contraseña");
            inputC = false;
        } else {
            inputC = true;
        }

        if (inputN && inputC){
            try {
                String n = edtUserName.getText().toString();
                String p = edtPassword.getText().toString();
                boolean sesion = conexion.loginUsuario(n, p);

                if (sesion == true){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Usuario y contraseña invalido", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception x){
                x.printStackTrace();
            }

        }
    }
}