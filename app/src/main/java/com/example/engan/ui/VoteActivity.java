package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.engan.R;
import com.example.engan.model.Player;

import java.util.ArrayList;
import java.util.Locale;

public class VoteActivity extends AppCompatActivity {

    private ArrayList<Player> players;
    private ArrayAdapter<Player> adapter;
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        ArrayList<Player> initialPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("players");
        players = initialPlayers != null ? new ArrayList<>(initialPlayers) : new ArrayList<>();

        endTime = getIntent().getLongExtra("endTime", System.currentTimeMillis() + 600000);

        tvTimer = findViewById(R.id.tvVoteTimer);
        GridView gvVotePlayers = findViewById(R.id.lvVotePlayers);

        adapter = new ArrayAdapter<Player>(this, R.layout.item_player, R.id.tvPlayerNameItem, players) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                return view;
            }
        };

        gvVotePlayers.setAdapter(adapter);

        gvVotePlayers.setOnItemClickListener((parent, view, position, id) -> {
            if (countDownTimer != null) countDownTimer.cancel();
            Player selected = players.get(position);
            Intent intent = new Intent(VoteActivity.this, VoteResultActivity.class);
            intent.putExtra("selectedPlayer", selected);
            
            ArrayList<Player> updatedPlayers = new ArrayList<>(players);
            if (selected.getRole() != Player.Role.ENGANON) {
                updatedPlayers.remove(position);
            }
            
            intent.putExtra("players", updatedPlayers);
            intent.putExtra("endTime", endTime);
            startActivity(intent);
            finish();
        });

        startTimer();
    }

    private void startTimer() {
        long timeLeft = endTime - System.currentTimeMillis();
        if (timeLeft <= 0) {
            if (tvTimer != null) tvTimer.setText("00:00");
            return;
        }

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountDownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if (tvTimer != null) tvTimer.setText("00:00");
            }
        }.start();
    }

    private void updateCountDownText(long millis) {
        if (tvTimer == null) return;
        int minutes = (int) (millis / 1000) / 60;
        int seconds = (int) (millis / 1000) % 60;
        tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
