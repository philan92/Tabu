package tabu.htw_berlin.de.tabu;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by philipp on 04.01.16.
 * Diese Activity wird nach dem Druck auf den Button "Starte Spiel" gestartet.
 * Sie zeigt einen Countdown und startet nach Ablauf die Activity fuer das eigentliche Spiel.
 */
public class CountdownActivity extends AppCompatActivity {

    TextView tvCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        tvCountdown = (TextView) findViewById(R.id.tv_countdown);

        /* Countdown leicht veraendert von
        http://developer.android.com/reference/android/os/CountDownTimer.html uebernommen
        */
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvCountdown.setText(String.valueOf(0)); //TODO SpielActivity aufrufen
            }
        }.start();
    }
}
