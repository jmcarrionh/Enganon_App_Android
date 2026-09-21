package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.db.DatabaseHelper;
import com.example.engan.model.Palabra;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dbHelper = new DatabaseHelper(this);
        List<Palabra> palabras = dbHelper.get3RandomPalabras();

        Button btnParticipante = findViewById(R.id.btnParticipante);
        Button btnEnganon = findViewById(R.id.btnEnganon);
        Button btnEncarni = findViewById(R.id.btnEncarni);

        if (palabras.size() >= 3) {
            btnParticipante.setOnClickListener(v -> startWordActivity("Participante", palabras.get(0)));
            btnEnganon.setOnClickListener(v -> startWordActivity("Engañón", palabras.get(1)));
            btnEncarni.setOnClickListener(v -> startWordActivity("Encarni", palabras.get(2)));
        }
    }

    private void startWordActivity(String role, Palabra palabra) {
        Intent intent = new Intent(this, WordActivity.class);
        intent.putExtra("role", role);
        intent.putExtra("palabra", palabra);
        startActivity(intent);
    }
}
