package com.jac.evaluacionpractica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductosViewHolder> {
    private Context mCtx;
    private List<Producto> productoList;

    public AdaptadorProductos(Context mCtx, List<Producto> productoListList) {
        this.mCtx = mCtx;
        this.productoList = productoListList;
    }

    @NonNull
    @Override
    public AdaptadorProductos.ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_recyclerview, null);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductos.ProductosViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.tvCodigoProducto.setText(String.valueOf(producto.getCodigo()));
        holder.tvNombreProducto.setText(producto.getNombreProducto());
        holder.tvPrecioProducto.setText(String.valueOf(producto.getPrecio()));
        holder.tvCategoriaProducto.setText(producto.getCategoria());



    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        TextView tvCodigoProducto, tvNombreProducto, tvPrecioProducto, tvCategoriaProducto;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCodigoProducto = itemView.findViewById(R.id.textViewCodigo1);
            tvNombreProducto = itemView.findViewById(R.id.textViewDescripcion1);
            tvPrecioProducto = itemView.findViewById(R.id.textViewPrecio1);
            tvCategoriaProducto = itemView.findViewById(R.id.textViewCat);

        }
    }
}
