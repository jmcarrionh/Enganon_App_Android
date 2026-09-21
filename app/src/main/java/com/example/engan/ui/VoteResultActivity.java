package com.example.engan.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.engan.MainActivity;
import com.example.engan.R;
import com.example.engan.model.Player;

import java.util.ArrayList;
import java.util.Locale;

public class VoteResultActivity extends AppCompatActivity {

    private Player selectedPlayer;
    private ArrayList<Player> players;
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private long endTime;
    private final int ELEGANT_BROWN = Color.parseColor("#4E342E");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_result);

        selectedPlayer = (Player) getIntent().getSerializableExtra("selectedPlayer");
        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");
        
        endTime = getIntent().getLongExtra("endTime", System.currentTimeMillis() + 600000);

        tvTimer = findViewById(R.id.tvVoteTimerResult);
        TextView tvSuspectName = findViewById(R.id.tvSuspectName);
        TextView tvIntrigueQuestion = findViewById(R.id.tvIntrigueQuestion);
        CardView cvResultCard = findViewById(R.id.cvResultCard);
        TextView tvRoleRevealed = findViewById(R.id.tvRoleRevealed);
        TextView tvMainVerdict = findViewById(R.id.tvMainVerdict);
        
        Button btnRevealVote = findViewById(R.id.btnRevealVote);
        Button btnContinue = findViewById(R.id.btnContinue);

        tvSuspectName.setText(selectedPlayer.getName());
        startTimer();

        btnRevealVote.setOnClickListener(v -> {
            btnRevealVote.setVisibility(View.GONE);
            tvIntrigueQuestion.setVisibility(View.VISIBLE);
            tvIntrigueQuestion.setAlpha(0f);
            tvIntrigueQuestion.animate().alpha(1f).setDuration(600).start();

            new Handler().postDelayed(() -> {
                boolean isEnganon = selectedPlayer.getRole() == Player.Role.ENGANON;
                boolean isGameOver = false;
                boolean enganonWon = false;

                if (isEnganon) {
                    isGameOver = true;
                    enganonWon = false;
                } else {
                    if (players.size() <= 2) {
                        boolean enganonSigueVivo = false;
                        for (Player p : players) {
                            if (p.getRole() == Player.Role.ENGANON) {
                                enganonSigueVivo = true;
                                break;
                            }
                        }
                        if (enganonSigueVivo) {
                            isGameOver = true;
                            enganonWon = true;
                        }
                    }
                }

                if (isGameOver) {
                    if (countDownTimer != null) countDownTimer.cancel();
                    Intent intent = new Intent(this, FinalResultActivity.class);
                    intent.putExtra(FinalResultActivity.EXTRA_ENGANON_WON, enganonWon);
                    intent.putExtra(FinalResultActivity.EXTRA_SKIP_SUSPENSE, true);
                    startActivity(intent);
                    finish();
                } else {
                    tvIntrigueQuestion.animate().alpha(0f).setDuration(400).withEndAction(() -> {
                        tvIntrigueQuestion.setVisibility(View.GONE);
                        
                        tvMainVerdict.setText("NO ERA EL ENGAÑÓN");
                        tvMainVerdict.setTextColor(ELEGANT_BROWN);
                        
                        tvRoleRevealed.setText("Era: " + selectedPlayer.getRole().getDisplayName());
                        
                        cvResultCard.setVisibility(View.VISIBLE);
                        cvResultCard.animate()
                                .alpha(1f)
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(700)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .withEndAction(() -> {
                                    btnContinue.setVisibility(View.VISIBLE);
                                    btnContinue.animate().alpha(1f).setDuration(400).start();
                                })
                                .start();
                    }).start();
                }
            }, 2000);
        });

        btnContinue.setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel();
            Intent intent = new Intent(this, VoteActivity.class);
            intent.putExtra("players", players);
            intent.putExtra("endTime", endTime);
            startActivity(intent);
            finish();
        });
    }

    private void startTimer() {
        long timeLeft = endTime - System.currentTimeMillis();
        if (timeLeft <= 0) {
            tvTimer.setText("00:00");
            return;
        }

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountDownText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
            }
        }.start();
    }

    private void updateCountDownText(long millis) {
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
