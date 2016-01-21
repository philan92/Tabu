package tabu.htw_berlin.de.tabu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
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
    public static final long SLEEPTIME = 10;

    Statistik statistik;

    boolean isRunning;
    Thread refreshThread;
    boolean gestartet;
    double time;
    int startTime; // wird als Referenzgroesse fuer Progbar benutzt

    List<String> gezogeneKarte; // enthaelt die Begriffe der aktuell gezogenen Karte
    List<Integer> bereitsGezogen; // enthaelt die Zeilennummern der bereits gezogenen Karten
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
    TextView tvVerbleibendeZeit;
    ProgressBar progBar;


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
        tvVerbleibendeZeit = (TextView) findViewById(R.id.tv_verbleibendeZeit);
        progBar = (ProgressBar) findViewById(R.id.progressBar);

        btnRichtig.setOnClickListener(this);
        btnNaechsteKarte.setOnClickListener(this);

        statistik = new Statistik();

        gestartet = false;

        time = 60.00; // Zeit fuer eine Runde

        startTime = (int)time;
        progBar.setMax(startTime);

        bereitsGezogen = new ArrayList<>();
        punktzahl = 0;

        isRunning = true;
        initThread();

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
                tvPunkte.setText(String.format("Punkte: %d", punktzahl));
                statistik.addKorrektenBegriff(gezogeneKarte.get(0));
                statistik.erhoehePunktzahl();
                zieheKarte();
                break;
            case R.id.btn_naechsteKarte:
                zieheKarte();
                time -= 5.00; // 5 Strafsekunden
                Toast.makeText(SpielActivity.this, R.string.str_strafe, Toast.LENGTH_SHORT).show();
                break;
        }
    }



    // Zeigt die gezogenen Begriffe auf dem Display an
    private void zeigeKarte() {
        tvBegriff1.setText((gezogeneKarte.get(0)).toUpperCase());
        tvBegriff2.setText(gezogeneKarte.get(1));
        tvBegriff3.setText(gezogeneKarte.get(2));
        tvBegriff4.setText(gezogeneKarte.get(3));
        tvBegriff5.setText(gezogeneKarte.get(4));
        tvBegriff6.setText(gezogeneKarte.get(5));

        statistik.addGezogenenBegriff(gezogeneKarte.get(0));
    }

    // Initialisiert und startet den Timer
    private void initThread() {
        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    time -= 0.01;

                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvVerbleibendeZeit.setText(String.format("%.2f", time)); // schreibt verbleibene Zeit

                            progBar.setProgress((int) Math.abs(time - startTime));

                            // pruefe, ob Zeit abgelaufen ist
                            if (time <= 0) {
                                isRunning = false;
                                tvVerbleibendeZeit.setText(R.string.str_platzhalter_zeit);

                                // startet die ErgebnisActivity genau einmal
                                if (!gestartet){
                                    gestartet = true;
                                    starteErgebnisActivity();
                                }

                            }
                        }
                    });

                }
            }
        });
        refreshThread.start();
    }

    private void starteErgebnisActivity() {
        Intent intent = new Intent(this, ErgebnisActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("statistik", statistik);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
