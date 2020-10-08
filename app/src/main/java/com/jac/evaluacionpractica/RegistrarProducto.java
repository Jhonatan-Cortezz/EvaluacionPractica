package com.jac.evaluacionpractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrarProducto extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCodigo, edtNombre, edtDescripcion, edtStock, edtPrecio, edtUnidad, edtEstado;
    private Spinner spCategoria;
    private Button btnSave;

    private boolean inputC = false;
    private boolean inputN = false;
    private boolean inputD = false;
    private boolean inputSt = false;
    private boolean inputPr = false;
    private boolean inputUn = false;
    private boolean inputEst = false;

    Conexion conexion = new Conexion(this);
    Producto producto = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);
        setTitle("Registro de Productos");

        edtCodigo = findViewById(R.id.codPro);
        edtNombre = findViewById(R.id.nomProd);
        edtDescripcion = findViewById(R.id.descripcionProd);
        edtStock = findViewById(R.id.stock);
        edtPrecio = findViewById(R.id.precio);
        edtUnidad = findViewById(R.id.unidadM);
        edtEstado = findViewById(R.id.estadoProd);
        spCategoria = findViewById(R.id.selectCategoria);
        btnSave = findViewById(R.id.registraProd);

        conexion.consultaListaArtSpiner();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, conexion.obtenerListaCategroias());
        spCategoria.setAdapter(adapter);

        String senal = "";
        String codi = "";
        String nombre = "";
        String descipcion = "";
        String stock = "";
        String precio = "";
        String unidad = "";
        String estado = "";
        String categoria = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codi = bundle.getString("codigo");
                senal = bundle.getString("senal");
                nombre = bundle.getString("nombre");
                descipcion = bundle.getString("descripcion");
                stock = bundle.getString("stock");
                precio = bundle.getString("precio");
                unidad = bundle.getString("unidad");
                estado = bundle.getString("estado");
                categoria = bundle.getString("categoria");

                ArrayAdapter adapterS = (ArrayAdapter) spCategoria.getAdapter();
                int SpinnerPosition = adapterS.getPosition(categoria);

                if (senal.equals("1")){
                    edtCodigo.setText(codi);
                    edtNombre.setText(nombre);
                    edtDescripcion.setText(descipcion);
                    edtStock.setText(stock);
                    edtPrecio.setText(precio);
                    edtUnidad.setText(unidad);
                    edtEstado.setText(estado);
                    spCategoria.setSelection(SpinnerPosition);
                    btnSave.setText("ACTUALIZAR");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        btnSave.setOnClickListener(this);

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

        if (edtDescripcion.getText().toString().length() == 0){
            edtDescripcion.setError("Campo obligatorio");
            inputD = false;
        } else {
            inputD = true;
        }

        if (edtStock.getText().toString().length() == 0){
            edtStock.setError("Campo obligatorio");
            inputSt = false;
        } else {
            inputSt = true;
        }

        if (edtPrecio.getText().toString().length() == 0){
            edtPrecio.setError("Campo obligatorio");
            inputPr = false;
        } else {
            inputPr = true;
        }

        if (edtUnidad.getText().toString().length() == 0){
            edtUnidad.setError("Campo obligatorio");
            inputUn = false;
        } else {
            inputUn = true;
        }

        if (edtEstado.getText().toString().length() == 0){
            edtEstado.setError("Campo obligatorio");
            inputEst = false;
        } else {
            inputEst = true;
        }


        if (inputC && inputN && inputD && inputSt && inputPr && inputUn && inputEst){
            try {
                producto.setCodigo(Integer.parseInt(edtCodigo.getText().toString()));
                producto.setNombreProducto(edtNombre.getText().toString());
                producto.setDescripcion(edtDescripcion.getText().toString());
                producto.setStock(Double.parseDouble(edtStock.getText().toString()));
                producto.setPrecio(Double.parseDouble(edtPrecio.getText().toString()));
                producto.setUnidadMedida(edtUnidad.getText().toString());
                producto.setEstadoProd(Integer.parseInt(edtEstado.getText().toString()));
                producto.setCategoria(spCategoria.getSelectedItem().toString());

                if (conexion.insertarProducto(producto)){
                    Toast.makeText(this, "Registro Agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ListViewProductos.class);
                    startActivity(intent);
                } else if (btnSave.getText().toString().equals("ACTUALIZAR")) {
                    conexion.modifcarProducto(producto);
                    Toast.makeText(this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ListViewProductos.class);
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