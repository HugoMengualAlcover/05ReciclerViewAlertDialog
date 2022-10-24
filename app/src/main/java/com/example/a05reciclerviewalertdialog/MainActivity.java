package com.example.a05reciclerviewalertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.a05reciclerviewalertdialog.Adapters.ToDoAdapter;
import com.example.a05reciclerviewalertdialog.MOdelos.ToDo;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a05reciclerviewalertdialog.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> toDosList;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        toDosList = new ArrayList<>();
        crearToDos();

        adapter = new ToDoAdapter(toDosList, R.layout.todo_view_model, MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);

        //layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager = new GridLayoutManager(MainActivity.this, 2);

        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo("Nueva Tarea").show();
            }
        });
    }

    private AlertDialog createToDo(String titulo){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);

        View contenido = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_todo_alert_dialog, null);
        EditText txtTitulo = contenido.findViewById(R.id.txtTituloAddTodo);
        EditText txtContenido = contenido.findViewById(R.id.txtContenidoAddToDO);

        builder.setView(contenido);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToDo toDo = new ToDo(txtTitulo.getText().toString(), txtContenido.getText().toString());
                toDosList.add(toDo);
                adapter.notifyDataSetChanged();
            }
        });
        return builder.create();
    }

    private void crearToDos() {
        for (int i = 0; i < 10; i++) {
            toDosList.add(new ToDo("titulo "+i, "Contenido "+i));
        }
    }
}