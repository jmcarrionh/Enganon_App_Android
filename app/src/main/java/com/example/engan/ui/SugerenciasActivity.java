package com.example.engan.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;

public class SugerenciasActivity extends AppCompatActivity {

    private EditText etSugerencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sugerencias);

        etSugerencia = findViewById(R.id.etSugerencia);
        Button btnEnviar = findViewById(R.id.btnEnviarSugerencia);
        LinearLayout btnBack = findViewById(R.id.btnBackSugerencias);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnEnviar.setOnClickListener(v -> enviarCorreo());
    }

    private void enviarCorreo() {
        String sugerencia = etSugerencia.getText().toString().trim();

        if (sugerencia.isEmpty()) {
            Toast.makeText(this, "Por favor, escribe tu sugerencia", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"enganonsugerencias@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Nueva sugerencia desde la app");
        intent.putExtra(Intent.EXTRA_TEXT, sugerencia);

        try {
            startActivity(Intent.createChooser(intent, "Enviar sugerencia..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show();
        }
    }
}
