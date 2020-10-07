package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetalleCategoria extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCodigo, tvNombre, tvEstado;
    private Button btnEditarCat, btnBorrarCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_categoria);

        tvCodigo = findViewById(R.id.tvCodigoCatDetalle);
        tvNombre = findViewById(R.id.tvNombreCatDetalle);
        tvEstado = findViewById(R.id.tvEstadoCatDetalle);
        btnEditarCat = findViewById(R.id.btnEditarCat);
        btnBorrarCat = findViewById(R.id.btnBorrarCat);

        Bundle obj =getIntent().getExtras();
        Categoria cat = null;
        if (obj != null){
            cat = (Categoria) obj.getSerializable("categoria");
            tvCodigo.setText(""+ cat.getCodigo() );
            tvNombre.setText(cat.getNombreCategoria());
            tvEstado.setText(String.valueOf(cat.getEstado()));

        }
        btnEditarCat.setOnClickListener(this);
        btnBorrarCat.setOnClickListener(this);

    }

    private String getDateTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
        Date date = new Date();
        return format.format(date);
    }

    @Override
    public void onClick(View view) {
        Conexion con = new Conexion(this);
        Categoria cat = new Categoria();
        String cod = tvCodigo.getText().toString();
        cat.setCodigo(Integer.parseInt(cod));
        if (view.getId() == R.id.btnBorrarCat){
            con.bajaCodigo(DetalleCategoria.this, cat);
        } else if (view.getId() == R.id.btnEditarCat){
            String codel = tvCodigo.getText().toString();
            String nombre = tvNombre.getText().toString();
            String estado = tvEstado.getText().toString();

            Intent intent = new Intent(this, RegistrarCategoria.class);
            intent.putExtra("senal", "1");
            intent.putExtra("codigo", codel);
            intent.putExtra("nombre", nombre);
            intent.putExtra("estado", estado);
            startActivity(intent);

        }
    }
}