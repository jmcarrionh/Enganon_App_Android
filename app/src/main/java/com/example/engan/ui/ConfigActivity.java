package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.db.DatabaseHelper;
import com.example.engan.model.Palabra;
import com.example.engan.model.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ConfigActivity extends AppCompatActivity {

    private ArrayList<String> playerNames;
    private int numEng = 1;
    private int numEnc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        playerNames = getIntent().getStringArrayListExtra("playerNames");
        if (playerNames == null) playerNames = new ArrayList<>();

        TextView tvEnganonValue = findViewById(R.id.tvEnganonValue);
        TextView tvEncarniValue = findViewById(R.id.tvEncarniValue);
        
        tvEnganonValue.setText(String.valueOf(numEng));
        tvEncarniValue.setText(String.valueOf(numEnc));
        
        Button btnPlusEng = findViewById(R.id.btnPlusEnganon);
        Button btnMinusEng = findViewById(R.id.btnMinusEnganon);
        Button btnPlusEnc = findViewById(R.id.btnPlusEncarni);
        Button btnMinusEnc = findViewById(R.id.btnMinusEncarni);
        
        Button btnStartGame = findViewById(R.id.btnStartGame);
        View btnBack = findViewById(R.id.btnBackConfig);

        int totalPlayers = playerNames.size();

        btnPlusEng.setOnClickListener(v -> {
            int maxEng = (totalPlayers <= 10) ? 2 : 3;
            if (numEng < maxEng) {
                numEng++;
                tvEnganonValue.setText(String.valueOf(numEng));
            } else {
                Toast.makeText(this, "Máximo " + maxEng + " Engañones para este número de jugadores", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnMinusEng.setOnClickListener(v -> {
            if (numEng > 0) {
                numEng--;
                tvEnganonValue.setText(String.valueOf(numEng));
            }
        });

        btnPlusEnc.setOnClickListener(v -> {
            if (numEnc < 2) {
                numEnc++;
                tvEncarniValue.setText(String.valueOf(numEnc));
            } else {
                Toast.makeText(this, "Máximo 2 Encarnis permitidos", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnMinusEnc.setOnClickListener(v -> {
            if (numEnc > 0) {
                numEnc--;
                tvEncarniValue.setText(String.valueOf(numEnc));
            }
        });

        btnStartGame.setOnClickListener(v -> {
            if (numEng <= 0) {
                Toast.makeText(this, "Es obligatorio que haya al menos 1 Engañón", Toast.LENGTH_SHORT).show();
                return;
            }
            
            if (numEng + numEnc >= totalPlayers) {
                Toast.makeText(this, "No puede haber más roles especiales que jugadores totales", Toast.LENGTH_SHORT).show();
                return;
            }

            assignRolesAndStart(numEng, numEnc);
        });

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void assignRolesAndStart(int numEng, int numEnc) {
        ArrayList<Player> players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }

        
        ArrayList<Player.Role> roles = new ArrayList<>();
        for (int i = 0; i < numEng; i++) roles.add(Player.Role.ENGANON);
        for (int i = 0; i < numEnc; i++) roles.add(Player.Role.ENCARNI);
        while (roles.size() < players.size()) roles.add(Player.Role.PARTICIPANTE);

        
        Collections.shuffle(roles);
        
        
        Collections.shuffle(players);

        
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setRole(roles.get(i));
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Palabra palabra = dbHelper.getRandomPalabra();

        if (palabra == null) {
            Toast.makeText(this, "Error: No hay palabras en la base de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, RoleSelectionActivity.class);
        intent.putExtra("players", players);
        intent.putExtra("palabra", palabra);
        startActivity(intent);
    }
}
