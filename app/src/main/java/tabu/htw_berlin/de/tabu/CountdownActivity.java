package tabu.htw_berlin.de.tabu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by philipp on 04.01.16.
 * Diese Activity wird nach dem Druck auf den Button "Starte Spiel" gestartet.
 * Sie zeigt einen Countdown und startet nach Ablauf die Activity fuer das eigentliche Spiel.
 */
public class CountdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
    }
}
