package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;

import java.util.ArrayList;

public class PlayersActivity extends AppCompatActivity {

    private ArrayList<String> playerNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        playerNames = new ArrayList<>();
        
        adapter = new ArrayAdapter<String>(this, R.layout.item_player, R.id.tvPlayerNameItem, playerNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                return view;
            }
        };

        EditText etPlayerName = findViewById(R.id.etPlayerName);
        Button btnAddPlayer = findViewById(R.id.btnAddPlayer);
        GridView gvPlayers = findViewById(R.id.lvPlayers);
        Button btnConfig = findViewById(R.id.btnConfig);
        View btnBack = findViewById(R.id.btnBackPlayers);

        gvPlayers.setAdapter(adapter);

        btnAddPlayer.setOnClickListener(v -> {
            String name = etPlayerName.getText().toString().trim();
            if (!name.isEmpty()) {
                playerNames.add(name);
                adapter.notifyDataSetChanged();
                etPlayerName.setText("");
            }
        });

        btnConfig.setOnClickListener(v -> {
            if (playerNames.size() < 3) {
                Toast.makeText(this, "Añade al menos 3 jugadores", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, ConfigActivity.class);
            intent.putStringArrayListExtra("playerNames", playerNames);
            startActivity(intent);
        });

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
