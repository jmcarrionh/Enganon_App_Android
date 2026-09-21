package com.example.engan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.db.DatabaseHelper;
import com.example.engan.ui.PlayersActivity;
import com.example.engan.ui.SugerenciasActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        new DatabaseHelper(this);
        
        Button btnJugar = findViewById(R.id.btnJugar);
        if (btnJugar != null) {
            btnJugar.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PlayersActivity.class);
                startActivity(intent);
            });
        }

        Button btnInstrucciones = findViewById(R.id.btnInstrucciones);
        if (btnInstrucciones != null) {
            btnInstrucciones.setOnClickListener(v -> showInstructions());
        }

        Button btnSugerencias = findViewById(R.id.btnSugerencias);
        if (btnSugerencias != null) {
            btnSugerencias.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SugerenciasActivity.class);
                startActivity(intent);
            });
        }
    }

    private void showInstructions() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_instructions, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();
        
        Button btnCerrar = dialogView.findViewById(R.id.btnCerrarInst);
        if (btnCerrar != null) {
            btnCerrar.setOnClickListener(v -> dialog.dismiss());
        }

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show();
    }
}
