package com.example.engan.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.model.Palabra;
import com.example.engan.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoleSelectionActivity extends AppCompatActivity {

    private ArrayList<Player> players;
    private Palabra palabra;
    private int currentIndex = 0;

    private TextView tvPlayerName, tvRoleName, tvSecretInfo, tvSecretHint;
    private ImageView ivAvatar;
    private View llCover, llSecretContent;
    private Button btnNext;
    
    private float startY;
    private List<Integer> availableAvatars;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");
        palabra = (Palabra) getIntent().getSerializableExtra("palabra");

        if (players == null || palabra == null) {
            Toast.makeText(this, "Error en los datos de partida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvPlayerName = findViewById(R.id.tvPlayerName);
        tvRoleName = findViewById(R.id.tvRoleName);
        tvSecretInfo = findViewById(R.id.tvSecretInfo);
        tvSecretHint = findViewById(R.id.tvSecretHint);
        ivAvatar = findViewById(R.id.ivAvatar);
        llCover = findViewById(R.id.llCover);
        llSecretContent = findViewById(R.id.llSecretContent);
        btnNext = findViewById(R.id.btnNext);

        prepareAvatars();

        setupPlayer();

        llCover.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startY = event.getY();
                    return true;
                case MotionEvent.ACTION_UP:
                    float endY = event.getY();
                    if (startY - endY > 100) {
                        revealWithAnimation();
                    }
                    return true;
            }
            return false;
        });

        btnNext.setOnClickListener(v -> {
            currentIndex++;
            if (currentIndex < players.size()) {
                setupPlayer();
            } else {
                Intent intent = new Intent(this, DebateActivity.class);
                intent.putExtra("players", players);
                startActivity(intent);
                finish();
            }
        });
    }

    private void prepareAvatars() {
        availableAvatars = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            availableAvatars.add(i);
        }
        Collections.shuffle(availableAvatars);
    }

    private void setupPlayer() {
        Player p = players.get(currentIndex);
        tvPlayerName.setText(p.getName());
        
        if (availableAvatars.isEmpty()) {
            prepareAvatars();
        }

        int avatarNum = availableAvatars.remove(0);
        int resID = getResources().getIdentifier("avatar" + avatarNum, "drawable", getPackageName());
        if (resID != 0) {
            ivAvatar.setImageResource(resID);
        }

        tvRoleName.setText(p.getRole().getDisplayName());
        if (p.getRole() == Player.Role.ENGANON) {
            tvSecretInfo.setText("PISTA: " + palabra.getPista());
            tvSecretHint.setVisibility(View.GONE);
        } else if (p.getRole() == Player.Role.ENCARNI) {
            tvSecretInfo.setText(palabra.getTexto());
            tvSecretHint.setText("PISTA: " + palabra.getPista());
            tvSecretHint.setVisibility(View.VISIBLE);
        } else {
            tvSecretInfo.setText(palabra.getTexto());
            tvSecretHint.setVisibility(View.GONE);
        }

        llCover.setVisibility(View.VISIBLE);
        llCover.setTranslationY(0);
        llSecretContent.setAlpha(0f);
        btnNext.setVisibility(View.GONE);
    }

    private void revealWithAnimation() {
        llCover.animate()
                .translationY(-llCover.getHeight())
                .setDuration(500)
                .withEndAction(() -> {
                    llCover.setVisibility(View.GONE);
                    llSecretContent.animate().alpha(1f).setDuration(300).withEndAction(() -> {
                        btnNext.setVisibility(View.VISIBLE);
                    }).start();
                });
    }
}
