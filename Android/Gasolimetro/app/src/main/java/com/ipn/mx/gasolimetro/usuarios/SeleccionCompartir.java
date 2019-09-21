package com.ipn.mx.gasolimetro.usuarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ipn.mx.gasolimetro.R;

public class SeleccionCompartir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_compartir);

        showToolbar(getResources().getString(R.string.toolbar_tittle_seleccion_compartir),true);
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void compartirInsignia(View view){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);

        // change the type of data you need to share,
        // for image use "image/*"
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Insignia chida");
        startActivity(Intent.createChooser(intent, "Compartir"));
    }

    public void compartirMedicion(View view){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);

        // change the type of data you need to share,
        // for image use "image/*"
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Medición chida");
        startActivity(Intent.createChooser(intent, "Compartir"));
    }

}
