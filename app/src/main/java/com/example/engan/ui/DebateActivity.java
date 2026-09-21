package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.model.Player;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class DebateActivity extends AppCompatActivity {

    private ArrayList<Player> players;
    private TextView tvTimer, tvStarter;
    private CountDownTimer countDownTimer;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        endTime = System.currentTimeMillis() + 600000;

        tvTimer = findViewById(R.id.tvDebateTimer);
        tvStarter = findViewById(R.id.tvStarter);
        Button btnGoToVote = findViewById(R.id.btnGoToVote);

        if (players != null && !players.isEmpty()) {
            Random random = new Random();
            Player starter = players.get(random.nextInt(players.size()));
            tvStarter.setText("Empieza " + starter.getName());
        }

        startTimer();

        btnGoToVote.setOnClickListener(v -> goToVote());
    }

    private void startTimer() {
        long timeLeft = endTime - System.currentTimeMillis();
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountDownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                goToVote();
            }
        }.start();
    }

    private void updateCountDownText(long millis) {
        int minutes = (int) (millis / 1000) / 60;
        int seconds = (int) (millis / 1000) % 60;
        tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    private void goToVote() {
        if (countDownTimer != null) countDownTimer.cancel();
        Intent intent = new Intent(this, VoteActivity.class);
        intent.putExtra("players", players);
        intent.putExtra("endTime", endTime);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
