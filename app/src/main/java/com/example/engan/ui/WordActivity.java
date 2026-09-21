package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.db.DatabaseHelper;
import com.example.engan.model.Palabra;

public class WordActivity extends AppCompatActivity {

    private TextView tvRoleTitle, tvPalabra, tvPista;
    private Button btnMostrarPista, btnNuevaPalabra;
    private DatabaseHelper dbHelper;
    private String currentRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        dbHelper = new DatabaseHelper(this);

        tvRoleTitle = findViewById(R.id.tvRoleTitle);
        tvPalabra = findViewById(R.id.tvPalabra);
        tvPista = findViewById(R.id.tvPista);
        btnMostrarPista = findViewById(R.id.btnMostrarPista);
        btnNuevaPalabra = findViewById(R.id.btnNuevaPalabra);

        currentRole = getIntent().getStringExtra("role");
        Palabra palabra = (Palabra) getIntent().getSerializableExtra("palabra");

        tvRoleTitle.setText("Rol: " + currentRole);
        if (palabra != null) {
            displayPalabra(palabra);
        }

        btnMostrarPista.setOnClickListener(v -> tvPista.setVisibility(View.VISIBLE));

        btnNuevaPalabra.setOnClickListener(v -> {
            Palabra nueva = dbHelper.getRandomPalabra();
            if (nueva != null) {
                displayPalabra(nueva);
            }
        });
    }

    private void displayPalabra(Palabra palabra) {
        tvPalabra.setText(palabra.getTexto());
        tvPista.setText(palabra.getPista());
        tvPista.setVisibility(View.INVISIBLE);
    }
}
