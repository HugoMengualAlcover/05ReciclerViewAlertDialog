package com.example.a05reciclerviewalertdialog.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05reciclerviewalertdialog.MOdelos.ToDo;
import com.example.a05reciclerviewalertdialog.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {

    private List<ToDo> objects;
    private int resource;
    private Context context;

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    /**
     * ALGO!!! NO ME IMPORTA. Llamará a este metodo para crear una nueva fila
     * @param parent
     * @param viewType
     * @return un objeto ViewHolder
     */
    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(resource, null);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        toDoView.setLayoutParams(lp);
        return new ToDoVH(toDoView);
    }


    /**
     * A partir de un view holder asigna valores a los elementos
     * @param holder -> Fila a configurar
     * @param position -> Elemento de la lista a mostrar
     */
    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        ToDo toDo = objects.get(position);
        holder.lblTitulo.setText(toDo.getTitulo());
        holder.lblContenido.setText(toDo.getContenido());
        holder.lblFecha.setText(toDo.getFecha().toString());
        if (toDo.isCompletado())
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        else
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaCambioEstado("Seguro que quieres cambiar el estado?",toDo).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmaCambioEstado(String mensaje, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    /**
     * Objeto que se instancia cada vez que tenga que mostrar un To-Do enel recicler
     * Pero solo se instancian los que caben en la pantalla +1/+2
     */
    public class ToDoVH extends RecyclerView.ViewHolder {

        TextView lblTitulo,lblContenido, lblFecha;
        ImageButton btnCompletado;

        public ToDoVH(@NonNull View itemView) {
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTituloToDoModelVIew);
            lblContenido = itemView.findViewById(R.id.lblContenidoToDoModelView);
            lblFecha = itemView.findViewById(R.id.lblFechaToDoModelView);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoModelView);
        }
    }
}
