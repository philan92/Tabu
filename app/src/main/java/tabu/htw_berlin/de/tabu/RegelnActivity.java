package tabu.htw_berlin.de.tabu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by philipp on 04.01.16.
 * Diese Activity zeigt die umfaenglichen Regeln des Tabuspiels an.
 * Der Benutzer kann mit dem Zurueckbutton des Telefons wieder ins Hauptmenue gelangen.
 */
public class RegelnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeln);
    }
}
