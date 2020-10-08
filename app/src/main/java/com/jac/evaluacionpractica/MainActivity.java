package com.jac.evaluacionpractica;

import android.content.Intent;
import android.os.Bundle;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FABToolbarLayout morph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Inventario JJTCL");

        FloatingActionButton fab = findViewById(R.id.fab);
        morph = findViewById(R.id.fabtoolbar);
        View uno, dos, tres, cuatro, cinco;

        fab.setOnClickListener(this);
        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        tres = findViewById(R.id.tres);
        cuatro = findViewById(R.id.cinco);

        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.recycler) {
            Intent intent = new Intent(this, MyRecyclerView.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.about){
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab){
            morph.show();
        }

        switch (view.getId()) {
            case R.id.uno:
                Intent intent = new Intent(this, ListViewProductos.class);
                startActivity(intent);
                break;

            case R.id.dos:
                Intent cat = new Intent(this, ListViewCategorias.class);
                startActivity(cat);
                break;

            case R.id.tres:
                Intent use = new Intent(this, ListViewUsuario.class);
                startActivity(use);
                break;

            case R.id.cinco:
                morph.hide();
                break;
        }
    }
}