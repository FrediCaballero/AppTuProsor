package com.example.apptuprosor;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.actions.ItemListIntents;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private List<ItemList> items;
    private List<ItemList> originalItems;
    private String doamin_image = "http://192.168.232.136/imagenes/";

    public RecyclerAdapter(List<ItemList> items, Cursos cursos) {
        this.items = items;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista_cursos, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        ItemList item = items.get(position);
        //holder.imgCurso.setImageResource(item.getImgResource());
        holder.tvTitulo.setText(item.getTitulo());
        holder.tvFecha.setText(item.getFecha());
        holder.tvHora.setText(item.getHora());
        holder.tvEnlace.setText(item.getEnlace());
        holder.tvDescripcion.setText(item.getDescripcion());
        Picasso.get().load(doamin_image+item.getImgResource()).into(holder.imgCurso);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(String strSearch){
        if(strSearch.length() == 0){
            items.clear();
            items.addAll(originalItems);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.clear();
                List<ItemList> collect = originalItems.stream().filter(i -> i.getTitulo().toLowerCase().contains(strSearch)).collect(Collectors.toList());
                items.addAll(collect);
            }else{
                items.clear();
                for (ItemList i : originalItems){
                    if(i.getTitulo().toLowerCase().contains(strSearch)){
                        items.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imgCurso;
        private TextView tvTitulo;
        private TextView tvFecha;
        private TextView tvHora;
        private TextView tvEnlace;
        private TextView tvDescripcion;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            imgCurso = itemView.findViewById(R.id.imgCurso);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvEnlace = itemView.findViewById(R.id.tvEnlace);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
