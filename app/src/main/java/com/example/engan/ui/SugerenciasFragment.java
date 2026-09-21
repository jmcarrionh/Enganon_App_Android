package com.example.engan.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.engan.R;

public class SugerenciasFragment extends Fragment {

    private EditText etSugerencia;

    public SugerenciasFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sugerencias, container, false);

        etSugerencia = view.findViewById(R.id.etSugerencia);
        Button btnEnviar = view.findViewById(R.id.btnEnviarSugerencia);
        LinearLayout btnBack = view.findViewById(R.id.btnBackSugerencias);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            });
        }

        btnEnviar.setOnClickListener(v -> enviarCorreo());

        return view;
    }

    private void enviarCorreo() {
        String sugerencia = etSugerencia.getText().toString().trim();

        if (sugerencia.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, escribe tu sugerencia", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show();
        }
    }
}
