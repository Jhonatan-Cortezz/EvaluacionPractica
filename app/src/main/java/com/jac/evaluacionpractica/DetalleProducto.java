package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalleProducto extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCodigo, tvNombre, tvDescripcion, tvStock, tvPrecio, tvUnidad, tvEstado, tvCategoria;
    private Button btnEditar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        setTitle("Detalle de Producto");

        tvCodigo = findViewById(R.id.tvCodigoProd);
        tvNombre = findViewById(R.id.tvNombreProd);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvStock = findViewById(R.id.tvStock);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvUnidad = findViewById(R.id.tvUnidadM);
        tvEstado = findViewById(R.id.tvEstadoProd);
        tvCategoria = findViewById(R.id.tvCategoria);

        btnEditar = findViewById(R.id.btnEditarProd);
        btnBorrar = findViewById(R.id.btnBorrarProd);

        Bundle obj =getIntent().getExtras();
        Producto usu = new Producto();
        if (obj != null){
            usu = (Producto) obj.getSerializable("producto");
            tvCodigo.setText(""+ usu.getCodigo());
            tvNombre.setText(usu.getNombreProducto());
            tvDescripcion.setText(usu.getDescripcion());
            tvStock.setText(String.valueOf(usu.getStock()));
            tvPrecio.setText(String.valueOf(usu.getPrecio()));
            tvUnidad.setText(usu.getUnidadMedida());
            tvEstado.setText(String.valueOf(usu.getEstadoProd()));
            tvCategoria.setText(usu.getCategoria());
        }

        btnBorrar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Conexion con = new Conexion(this);
        Producto use = new Producto();
        String cod = tvCodigo.getText().toString();
        use.setCodigo(Integer.parseInt(cod));

        if (view.getId() == R.id.btnBorrarProd){
            con.bajaCodigoProducto(DetalleProducto.this, use);
        } else if (view.getId() == R.id.btnEditarProd) {
            String code = tvCodigo.getText().toString();
            String nombre = tvNombre.getText().toString();
            String apellido = tvDescripcion.getText().toString();
            String correo = tvStock.getText().toString();
            String usuario = tvPrecio.getText().toString();
            String clave = tvUnidad.getText().toString();
            String estado = tvEstado.getText().toString();
            String pregunta = tvCategoria.getText().toString();

            Intent intent = new Intent(this, RegistrarProducto.class);
            intent.putExtra("senal", "1");
            intent.putExtra("codigo", code);
            intent.putExtra("nombre", nombre);
            intent.putExtra("descripcion", apellido);
            intent.putExtra("stock", correo);
            intent.putExtra("precio", usuario);
            intent.putExtra("unidad", clave);
            intent.putExtra("estado", estado);
            intent.putExtra("categoria", pregunta);
            startActivity(intent);
        }
    }
}