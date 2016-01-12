package tabu.htw_berlin.de.tabu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by philipp on 11.01.16.
 * Diese Activity repraesentiert das eigentliche Spiel. Dem Spieler werden die
 * zufaellig gezogenen Karten, die Punktzahl und die verbleibende Zeit angezeigt.
 */
public class SpielActivity extends AppCompatActivity implements View.OnClickListener{

    final int ANZAHL_KARTEN = 64; //Anzahl der Zeilen in tabu.txt
    List gezogeneKarte; // enthaelt die Begriffe der aktuell gezogenen Karte
    List bereitsGezogen; // enthaelt die Zeilennummern der bereits gezogenen Karten
    int punktzahl;

    TextView tvPunkte;
    Button btnRichtig;
    Button btnNaechsteKarte;
    TextView tvBegriff1;
    TextView tvBegriff2;
    TextView tvBegriff3;
    TextView tvBegriff4;
    TextView tvBegriff5;
    TextView tvBegriff6;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiel);

        tvPunkte = (TextView) findViewById(R.id.tv_punkte);
        btnRichtig = (Button) findViewById(R.id.btn_richtig);
        btnNaechsteKarte = (Button) findViewById(R.id.btn_naechsteKarte);
        tvBegriff1 = (TextView) findViewById(R.id.tv_begriff1);
        tvBegriff2 = (TextView) findViewById(R.id.tv_begriff2);
        tvBegriff3 = (TextView) findViewById(R.id.tv_begriff3);
        tvBegriff4 = (TextView) findViewById(R.id.tv_begriff4);
        tvBegriff5 = (TextView) findViewById(R.id.tv_begriff5);
        tvBegriff6 = (TextView) findViewById(R.id.tv_begriff6);


        btnRichtig.setOnClickListener(this);
        btnNaechsteKarte.setOnClickListener(this);

        bereitsGezogen = new ArrayList<>();
        punktzahl = 0;

        zieheKarte(); // Startkarte ziehen
    }

    // waehlt eine zufaellige Zeile, die bisher noch nicht gezogen wurde
    private void zieheKarte() {
        Random random = new Random();
        int zeile;
        boolean zeileOK = false;

        do {
            zeile = random.nextInt(ANZAHL_KARTEN + 1);
            if (!bereitsGezogen.contains(zeile)) {
                zeileOK = true;
                liesZeile(zeile);
                bereitsGezogen.add(zeile);
            }
        } while (!zeileOK);

    }

    // Liest die Zeile, die uebergeben wird und schreibt die Woerter in "gezogene Karte"
    private void liesZeile(int zeile) {
        String text;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.tabu);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (int i = 0; i < zeile - 1; i++)
                reader.readLine();
            text = reader.readLine();

            packeTextzeileInArray(text);
            zeigeKarte();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private void packeTextzeileInArray(String text) {
        gezogeneKarte = new ArrayList<>(Arrays.asList(text.split(",")));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_richtig:
                punktzahl++;
                tvPunkte.setText("Punkte: " + punktzahl);
                zieheKarte();
                break;
            case R.id.btn_naechsteKarte:
                zieheKarte();
                // TODO Zeitstrafe
                break;
        }
    }

    // Zeigt die gezogenen Begriffe auf dem Display an
    private void zeigeKarte() {
        tvBegriff1.setText((String)gezogeneKarte.get(0));
        tvBegriff2.setText((String)gezogeneKarte.get(1));
        tvBegriff3.setText((String)gezogeneKarte.get(2));
        tvBegriff4.setText((String)gezogeneKarte.get(3));
        tvBegriff5.setText((String)gezogeneKarte.get(4));
        tvBegriff6.setText((String)gezogeneKarte.get(5));
    }
}
