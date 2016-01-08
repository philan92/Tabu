package tabu.htw_berlin.de.tabu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnStarteSpiel;
    Button btnRegeln;
    Button btnBeenden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Erzeugen der Widgets
        btnStarteSpiel = (Button) findViewById(R.id.btn_starte_spiel);
        btnRegeln      = (Button) findViewById(R.id.btn_regeln);
        btnBeenden     = (Button) findViewById(R.id.btn_beenden);

        // Buttons dem Listener vorstellen
        btnStarteSpiel.setOnClickListener(this);
        btnRegeln.setOnClickListener(this);
        btnBeenden.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_starte_spiel:
                starteCountdownActivity();
                break;
            case R.id.btn_regeln:
                starteRegelnActivity();
                break;
            case R.id.btn_beenden:
                System.exit(0);
                break;
        }
    }

    private void starteRegelnActivity() {
        Intent intent = new Intent(this, RegelnActivity.class);
        startActivity(intent);
    }

    private void starteCountdownActivity() {
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }
}
