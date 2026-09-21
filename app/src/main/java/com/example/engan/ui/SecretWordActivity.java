package com.example.engan.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.model.Palabra;
import com.example.engan.model.Player;

public class SecretWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_word);

        Player player = (Player) getIntent().getSerializableExtra("player");
        Palabra palabra = (Palabra) getIntent().getSerializableExtra("palabra");

        TextView tvPlayerName = findViewById(R.id.tvPlayerName);
        TextView tvRoleName = findViewById(R.id.tvRoleName);
        TextView tvMainInfo = findViewById(R.id.tvMainInfo);
        TextView tvSecondaryInfo = findViewById(R.id.tvSecondaryInfo);
        Button btnReveal = findViewById(R.id.btnReveal);
        Button btnBack = findViewById(R.id.btnBack);

        if (player != null && palabra != null) {
            tvPlayerName.setText(player.getName());
            tvRoleName.setText("Rol: " + player.getRole().name());

            if (player.getRole() == Player.Role.ENGANON) {
                tvMainInfo.setText("PISTA: " + palabra.getPista());
                tvSecondaryInfo.setVisibility(View.GONE);
            } else if (player.getRole() == Player.Role.ENCARNI) {
                tvMainInfo.setText(palabra.getTexto());
                tvSecondaryInfo.setText("PISTA: " + palabra.getPista());
            } else {
                tvMainInfo.setText(palabra.getTexto());
                tvSecondaryInfo.setVisibility(View.GONE);
            }
        }

        btnReveal.setOnClickListener(v -> {
            tvMainInfo.setVisibility(View.VISIBLE);
            if (player.getRole() == Player.Role.ENCARNI) {
                tvSecondaryInfo.setVisibility(View.VISIBLE);
            }
            btnReveal.setVisibility(View.GONE);
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
