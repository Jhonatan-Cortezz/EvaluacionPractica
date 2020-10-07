package com.jac.evaluacionpractica;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Conexion extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventario.db";

    boolean estadoDelete = true;

    ArrayList<String> listaCategoria;
    ArrayList<String> listaUsuario;
    ArrayList<String> listaProducto;

    //representa la tabla producto
    ArrayList<Categoria> artListCat;
    ArrayList<Usuario> artListUse;
    ArrayList<Producto> artListProd;


    public Conexion(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulos(codigo integer not null primary key," +
                " nombre text," +
                " descripcion text," +
                " stock real," +
                " precio real," +
                " unidad_medida text," +
                " estado integer," +
                " fecha text," +
                " codigo_categoria text, "+
                " FOREIGN KEY(codigo_categoria) REFERENCES categoria(codigo))");

        db.execSQL("create table categoria(codigo integer not null primary key," +
                " nombre text," +
                " estado integer)");

        db.execSQL("create table usuario(codigo integer not null primary key," +
                " nombre text," +
                " apellido text," +
                " correo real," +
                " usuario real," +
                " clave text," +
                " estado integer," +
                " pregunta text," +
                " respuesta text," +
                " fecha text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists articulos");
        db.execSQL("drop table if exists categoria");
        db.execSQL("drop table if exists usuario");
        onCreate(db);
    }

    public SQLiteDatabase bd(){
        SQLiteDatabase bd = this.getWritableDatabase();
        return bd;
    }

    //inserta en la tabla categoria
    public boolean insertTrad(Categoria dto){
        boolean estado = true;
        int resultado;
        try {
            int codigo = dto.getCodigo();
            String nombreCategoria = dto.getNombreCategoria();
            int estadoCategoria = dto.getEstado();

            Cursor fila = bd().rawQuery("select codigo from categoria where codigo='" + codigo + "' ", null);

            if (fila.moveToFirst()== true){
                estado = false;
            } else {
                String Sql = "insert into categoria " + "(codigo, nombre, estado) " +
                        "values " + "('" + String.valueOf(codigo) + "', '" + nombreCategoria + "', '" + String.valueOf(estadoCategoria) + "');";
                bd().execSQL(Sql);
                bd().close();
                estado = true;
            }
        } catch (Exception ex){
            estado = false;
            Log.e(" Error ", ex.toString());
        }
        return estado;
    }

    //consulta por codigo la tabla categoria
    public boolean consultaCodigo(Categoria dto){
        boolean estado = true;
        int resultado;

        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            int codigo = dto.getCodigo();
            Cursor fila = bd.rawQuery("select codigo, nombre, estado from categoria where codigo=" + codigo, null);
            if (fila.moveToFirst()){
                dto.setCodigo(Integer.parseInt(fila.getString(0)));
                dto.setNombreCategoria(fila.getString(1));
                dto.setEstado(Integer.parseInt(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }

            bd.close();
        } catch (Exception e){
            estado = false;
            Log.e("Error", e.toString());
        }

        return estado;
    }

    //consulta por nombre la tabla categoria
    public boolean consultaDescripcion(Categoria dto){
        boolean estado = true;
        int resultao;

        SQLiteDatabase bd = this.getReadableDatabase();
        try{
            String[] parametros = {String.valueOf(dto.getNombreCategoria())};
            String[] campos = {"codigo", "nombre", "estado"};
            Cursor fila = bd.query("categoria", campos, "nombre=?", parametros, null, null, null);

            if (fila.moveToFirst()){
                dto.setCodigo(Integer.parseInt(fila.getString(0)));
                dto.setNombreCategoria(fila.getString(1));
                dto.setEstado(Integer.parseInt(fila.getString(2)));
                estado = true;
            } else {
                estado = false;
            }
            fila.close();
            bd.close();
        } catch (Exception ex){
            estado = false;
            Log.e("error", ex.toString());
        }

        return estado;
    }

    //borrar por codigo un registro de la tabla categoria
    public boolean bajaCodigo(final Context context, final Categoria dto){

        estadoDelete = true;
        try {
            int codigo = dto.getCodigo();
            Cursor fila = bd().rawQuery("select * from categoria where codigo=" + codigo,null);
            if (fila.moveToFirst()){
                dto.setCodigo(Integer.parseInt(fila.getString(0)));
                dto.setNombreCategoria(fila.getString(1));
                dto.setEstado(Integer.parseInt(fila.getString(2)));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_launcher_background);//poner un icono
                builder.setTitle("Alerta");
                builder.setMessage("¿Está seguro de borrar el registro? \nCódigo: " + dto.getCodigo() +
                        "\nNombre: " + dto.getNombreCategoria());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = dto.getCodigo();
                        int cant = bd().delete("categoria", "codigo=" + codigo, null);

                        if (cant > 0){
                            estadoDelete = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ListViewCategorias.class);
                            context.startActivity(intent);
                        } else {
                            estadoDelete = false;
                        }
                        bd().close();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "No hay coincidencias en la busqueda", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex){
            estadoDelete = false;
            Log.e("Error", ex.toString());
        }

        return estadoDelete;
    }

    //editar la tabla categorias
    public boolean modifcar(Categoria dto){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            int codigo = dto.getCodigo();
            String nombre = dto.getNombreCategoria();
            int estadoCat = dto.getEstado();

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("estado", estadoCat);

            int cant = (int) bd.update("categoria", registro, "codigo=" + codigo, null);

            bd.close();
            if (cant > 0){
                estado = true;
            } else {
                estado = false;
            }

        } catch (Exception x){
            estado = false;
            Log.e("Error", x.toString());
        }

        return estado;
    }


    public ArrayList<Categoria> consultaListaArtSpiner(){
        boolean estado = false;

        SQLiteDatabase bd = this.getReadableDatabase();
        Categoria art = null;
        artListCat = new ArrayList<Categoria>();

        try {
            Cursor fila = bd.rawQuery("select * from categoria", null);
            while (fila.moveToNext()){
                art = new Categoria();
                art.setCodigo(fila.getInt(0));
                art.setNombreCategoria(fila.getString(1));
                art.setEstado(fila.getInt(2));

                artListCat.add(art);
            }

            obtenerListaCategroias();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return artListCat;
    }

    public ArrayList<String> obtenerListaCategroias() {
        listaCategoria = new ArrayList<String>();

        listaCategoria.add("Seleccione");

        for (int i = 0; i < artListCat.size(); i++){
            listaCategoria.add(artListCat.get(i).getNombreCategoria());
        }

        return listaCategoria;
    }

    //para mostrar todas las categorias en un ListView
    public ArrayList<String> consulataCategorias1(){
        boolean estado = false;
        SQLiteDatabase bd = this.getReadableDatabase();

        Categoria articulo = null;
        artListCat = new ArrayList<Categoria>();

        try {
            Cursor fila = bd.rawQuery("select * from categoria", null);
            while(fila.moveToNext()){
                articulo = new Categoria();
                articulo.setCodigo(fila.getInt(0));
                articulo.setNombreCategoria(fila.getString(1));
                articulo.setEstado(fila.getInt(2));

                artListCat.add(articulo);
            }

            listaCategoria = new ArrayList<String>();

            for (int i=0; i< artListCat.size(); i++ ){
                listaCategoria.add(artListCat.get(i).getCodigo() + " - " + artListCat.get(i).getNombreCategoria());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return listaCategoria;
    }

    //################################################################
    //THE NEXT CODE IS IMPLEMENTS THE LOGIC FOR CRUD TABLE USER
    //IS SIMILAR TO PREVIUS CODE

    //metodo para insertar datos en la tabla usuario
    public boolean inserarUsuario(Usuario dto){
        boolean estado = true;
        int resultado;
        try {
            int codigo = dto.getCodigoUsuario();
            String nombreUsuario = dto.getNombreUsuario();
            String apellido = dto.getApellido();
            String correo = dto.getCorreo();
            String usuario = dto.getUsuario();
            String clave = dto.getClave();
            int estadoU = dto.getEstado();
            String pregunta = dto.getPregunta();
            String respuesta = dto.getRespuesta();

            Cursor fila = bd().rawQuery("select codigo from usuario where codigo='" + codigo + "' ", null);

            if (fila.moveToFirst()== true){
                estado = false;
            } else {
                String Sql = "insert into usuario " + "(codigo, nombre, apellido, correo, usuario, clave, estado, pregunta, respuesta) " +
                        "values " + "('" + String.valueOf(codigo) + "', '" + nombreUsuario + "', '" + apellido + "', '" + correo + "', '" + usuario + "', '" + clave + "', '" + String.valueOf(estadoU) + "', '" + pregunta + "', '" + respuesta + "');";
                bd().execSQL(Sql);
                bd().close();
                estado = true;
            }
        } catch (Exception ex){
            estado = false;
            Log.e(" Error ", ex.toString());
        }
        return estado;
    }

    //metodo para mostrar los registros en el listview
    public ArrayList<String> consulataUsuario1(){
        boolean estado = false;
        SQLiteDatabase bd = this.getReadableDatabase();

        Usuario articulo = null;
        artListUse = new ArrayList<Usuario>();

        try {
            Cursor fila = bd.rawQuery("select * from usuario", null);
            while(fila.moveToNext()){
                articulo = new Usuario();
                articulo.setCodigoUsuario(fila.getInt(0));
                articulo.setNombreUsuario(fila.getString(1));
                articulo.setApellido(fila.getString(2));
                articulo.setCorreo(fila.getString(3));
                articulo.setUsuario(fila.getString(4));
                articulo.setClave(fila.getString(5));
                articulo.setEstado(fila.getInt(6));
                articulo.setPregunta(fila.getString(7));
                articulo.setRespuesta(fila.getString(8));

                artListUse.add(articulo);
            }

            listaUsuario = new ArrayList<String>();

            for (int i=0; i< artListUse.size(); i++ ){
                listaUsuario.add(artListUse.get(i).getCodigoUsuario() + " - " + artListUse.get(i).getNombreUsuario());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return listaUsuario;
    }

    //borrar por codigo un registro de la tabla categoria
    public boolean bajaCodigoUsuario(final Context context, final Usuario dto){

        estadoDelete = true;
        try {
            int codigo = dto.getCodigoUsuario();
            Cursor fila = bd().rawQuery("select * from usuario where codigo=" + codigo,null);
            if (fila.moveToFirst()){
                dto.setCodigoUsuario(Integer.parseInt(fila.getString(0)));
                dto.setNombreUsuario(fila.getString(1));
                dto.setApellido(fila.getString(2));
                dto.setCorreo(fila.getString(3));
                dto.setUsuario(fila.getString(4));
                dto.setClave(fila.getString(5));
                dto.setEstado(Integer.parseInt(fila.getString(6)));
                dto.setPregunta(fila.getString(7));
                dto.setRespuesta(fila.getString(8));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_launcher_background);//poner un icono
                builder.setTitle("Alerta");
                builder.setMessage("¿Está seguro de borrar el registro? \nCódigo: " + dto.getNombreUsuario() +
                        "\nNombre: " + dto.getNombreUsuario());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = dto.getCodigoUsuario();
                        int cant = bd().delete("usuario", "codigo=" + codigo, null);

                        if (cant > 0){
                            estadoDelete = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ListViewUsuario.class);
                            context.startActivity(intent);
                        } else {
                            estadoDelete = false;
                        }
                        bd().close();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "No hay coincidencias en la busqueda", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex){
            estadoDelete = false;
            Log.e("Error", ex.toString());
        }

        return estadoDelete;
    }

    public ArrayList<Usuario> consultaListaArtSpinerUsuario(){
        boolean estado = false;

        SQLiteDatabase bd = this.getReadableDatabase();
        Usuario art = null;
        artListUse = new ArrayList<Usuario>();

        try {
            Cursor fila = bd.rawQuery("select * from usuario", null);
            while (fila.moveToNext()){
                art = new Usuario();
                art.setCodigoUsuario(fila.getInt(0));
                art.setNombreUsuario(fila.getString(1));
                art.setApellido(fila.getString(2));
                art.setCorreo(fila.getString(3));
                art.setUsuario(fila.getString(4));
                art.setClave(fila.getString(5));
                art.setEstado(fila.getInt(6));
                art.setPregunta(fila.getString(7));
                art.setRespuesta(fila.getString(8));

                artListUse.add(art);
            }

            obtenerListaUsuario();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return artListUse;
    }

    public ArrayList<String> obtenerListaUsuario() {
        listaUsuario = new ArrayList<String>();

        listaUsuario.add("Seleccione");

        for (int i = 0; i < artListUse.size(); i++){
            listaUsuario.add(artListUse.get(i).getCodigoUsuario()+" - "+ artListUse.get(i).getNombreUsuario() +" - "+ artListUse.get(i).getApellido());
        }

        return listaCategoria;
    }

    public boolean modifcarUsu(Usuario dto){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            int codigo = dto.getCodigoUsuario();
            String nombre = dto.getNombreUsuario();
            String apellido = dto.getApellido();
            String correo = dto.getCorreo();
            String usu = dto.getUsuario();
            String clave = dto.getClave();
            int estadoU = dto.getEstado();
            String pregunta = dto.getPregunta();
            String respuesta = dto.getRespuesta();

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("correo", correo);
            registro.put("usuario", usu);
            registro.put("clave", clave);
            registro.put("estado", estadoU);
            registro.put("pregunta", pregunta);
            registro.put("respuesta", respuesta);

            int cant = (int) bd.update("usuario", registro, "codigo=" + codigo, null);

            bd.close();
            if (cant > 0){
                estado = true;
            } else {
                estado = false;
            }

        } catch (Exception x){
            estado = false;
            Log.e("Error", x.toString());
        }

        return estado;
    }
    //FINALIZAN METODOS PARA GESTIONAR LOS DATOS DE LA TABLA USUARIO
    //*****************************************************************************
    //*****************************************************************************



    //INICIAN METODOS PARA ADMINISTRAR LOS DATOS DE LA TABLA PRODUCTOS
    //metodo para insertar datos en la tabla usuario
    public boolean insertarProducto(Producto dto){
        boolean estado = true;
        int resultado;
        try {
            int codigo = dto.getCodigo();
            String nombreProd = dto.getNombreProducto();
            String descripcion = dto.getDescripcion();
            double stock = dto.getStock();
            double precioP = dto.getPrecio();
            String unidad = dto.getUnidadMedida();
            int estadoP = dto.getEstadoProd();
            String categoria = dto.getCategoria();

            Cursor fila = bd().rawQuery("select codigo from articulos where codigo='" + codigo + "' ", null);

            if (fila.moveToFirst()== true){
                estado = false;
            } else {
                String Sql = "insert into articulos " + "(codigo, nombre, descripcion, stock, precio, unidad_medida, estado, codigo_categoria) " +
                        "values " + "('" + String.valueOf(codigo) + "', '" + nombreProd + "', '" + descripcion + "', '" + String.valueOf(stock) + "', '" + String.valueOf(precioP) + "', '" + unidad + "', '" + String.valueOf(estadoP) + "', '" + categoria + "');";
                bd().execSQL(Sql);
                bd().close();
                estado = true;
            }
        } catch (Exception ex){
            estado = false;
            Log.e(" Error ", ex.toString());
        }
        return estado;
    }

    //metodo para mostrar los registros en el listview
    public ArrayList<String> consulataProducto1(){
        boolean estado = false;
        SQLiteDatabase bd = this.getReadableDatabase();

        Producto articulo = null;
        artListProd = new ArrayList<Producto>();

        try {
            Cursor fila = bd.rawQuery("select * from articulos", null);
            while(fila.moveToNext()){
                articulo = new Producto();
                articulo.setCodigo(fila.getInt(0));
                articulo.setNombreProducto(fila.getString(1));
                articulo.setDescripcion(fila.getString(2));
                articulo.setStock(fila.getDouble(3));
                articulo.setPrecio(fila.getDouble(4));
                articulo.setUnidadMedida(fila.getString(5));
                articulo.setEstadoProd(fila.getInt(6));
                articulo.setCategoria(fila.getString(7));

                artListProd.add(articulo);
            }

            listaProducto= new ArrayList<String>();

            for (int i=0; i< artListProd.size(); i++ ){
                listaProducto.add(artListProd.get(i).getCodigo() + " - " + artListProd.get(i).getNombreProducto());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return listaProducto;
    }

    //borrar por codigo un registro de la tabla categoria
    public boolean bajaCodigoProducto(final Context context, final Producto dto){

        estadoDelete = true;
        try {
            int codigo = dto.getCodigo();
            Cursor fila = bd().rawQuery("select * from articulos where codigo=" + codigo,null);
            if (fila.moveToFirst()){
                dto.setCodigo(Integer.parseInt(fila.getString(0)));
                dto.setNombreProducto(fila.getString(1));
                dto.setDescripcion(fila.getString(2));
                dto.setStock(fila.getDouble(3));
                dto.setPrecio(fila.getDouble(4));
                dto.setUnidadMedida(fila.getString(5));
                dto.setEstadoProd(Integer.parseInt(fila.getString(6)));
                dto.setCategoria(fila.getString(7));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_launcher_background);//poner un icono
                builder.setTitle("Alerta");
                builder.setMessage("¿Está seguro de borrar el registro? \nCódigo: " + dto.getCodigo() +
                        "\nNombre: " + dto.getNombreProducto());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = dto.getCodigo();
                        int cant = bd().delete("articulos", "codigo=" + codigo, null);

                        if (cant > 0){
                            estadoDelete = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ListViewProductos.class);//cambiar destino
                            context.startActivity(intent);
                        } else {
                            estadoDelete = false;
                        }
                        bd().close();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(context, "No hay coincidencias en la busqueda", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex){
            estadoDelete = false;
            Log.e("Error", ex.toString());
        }

        return estadoDelete;
    }

    public ArrayList<Producto> consultaListaArtSpinerProducto(){
        boolean estado = false;

        SQLiteDatabase bd = this.getReadableDatabase();
        Producto art = null;
        artListProd = new ArrayList<Producto>();

        try {
            Cursor fila = bd.rawQuery("select * from articulos", null);
            while (fila.moveToNext()){
                art = new Producto();
                art.setCodigo(fila.getInt(0));
                art.setNombreProducto(fila.getString(1));
                art.setDescripcion(fila.getString(2));
                art.setStock(fila.getDouble(3));
                art.setPrecio(fila.getDouble(4));
                art.setUnidadMedida(fila.getString(5));
                art.setEstadoProd(fila.getInt(6));
                art.setCategoria(fila.getString(7));

                artListProd.add(art);
            }

            obtenerListaProducto();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return artListProd;
    }

    public ArrayList<String> obtenerListaProducto() {
        listaProducto = new ArrayList<String>();

        listaProducto.add("Seleccione");

        for (int i = 0; i < artListProd.size(); i++){
            listaProducto.add(artListProd.get(i).getCodigo()+" - "+ artListProd.get(i).getNombreProducto());
        }

        return listaProducto;
    }

    public boolean modifcarProducto(Producto dto){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            int codigo = dto.getCodigo();
            String nombre = dto.getNombreProducto();
            String descripcion = dto.getDescripcion();
            double stock = dto.getStock();
            double precio = dto.getPrecio();
            String unidadM = dto.getUnidadMedida();
            int estadoP = dto.getEstadoProd();
            String categoria = dto.getCategoria();


            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("descripcion", descripcion);
            registro.put("stock", stock);
            registro.put("precio", precio);
            registro.put("unidad", unidadM);
            registro.put("estado", estadoP);
            registro.put("categoria", categoria);

            int cant = (int) bd.update("articulos", registro, "codigo=" + codigo, null);

            bd.close();
            if (cant > 0){
                estado = true;
            } else {
                estado = false;
            }

        } catch (Exception x){
            estado = false;
            Log.e("Error", x.toString());
        }

        return estado;
    }

}

