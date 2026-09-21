package com.example.engan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.engan.MainActivity;
import com.example.engan.R;

public class FinalResultActivity extends AppCompatActivity {

    public static final String EXTRA_ENGANON_WON = "enganonWon";
    public static final String EXTRA_SKIP_SUSPENSE = "skipSuspense";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        TextView tvSuspense = findViewById(R.id.tvSuspense);
        CardView cvFinalImage = findViewById(R.id.cvFinalImage);
        ImageView ivBackground = findViewById(R.id.ivFinalResultBackground);
        Button btnContinue = findViewById(R.id.btnFinalContinue);

        boolean enganonWon = getIntent().getBooleanExtra(EXTRA_ENGANON_WON, false);
        boolean skipSuspense = getIntent().getBooleanExtra(EXTRA_SKIP_SUSPENSE, false);

        int imgRes = enganonWon ? R.drawable.enganon_ganador : R.drawable.enganon_perdedor;
        ivBackground.setImageResource(imgRes);

        if (skipSuspense) {
            tvSuspense.setVisibility(View.GONE);
            revealImage(cvFinalImage, btnContinue);
        } else {
            new Handler().postDelayed(() -> {
                if (tvSuspense != null) {
                    tvSuspense.animate()
                            .alpha(0f)
                            .setDuration(800)
                            .withEndAction(() -> {
                                tvSuspense.setVisibility(View.GONE);
                                revealImage(cvFinalImage, btnContinue);
                            })
                            .start();
                }
            }, 2500);
        }

        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(FinalResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void revealImage(CardView cvFinalImage, Button btnContinue) {
        if (cvFinalImage != null) {
            cvFinalImage.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(1200)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(() -> {
                        if (btnContinue != null) {
                            btnContinue.setVisibility(View.VISIBLE);
                            btnContinue.animate().alpha(1f).setDuration(500).start();
                        }
                    })
                    .start();
        }
    }
}
