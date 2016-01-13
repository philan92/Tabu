package tabu.htw_berlin.de.tabu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by philipp on 13.01.16.
 * In dieser Activity wird die Auswertung der Spielrunde gezeigt. Es werden die erreichten Punkte
 * sowie die gezogenen Karten gezeigt.
 */
public class ErgebnisActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnHauptmenue;
    TextView tvPunktzahl;
    ListView listView;

    ArrayAdapter<String> itemsAdapter;

    Statistik statistik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        statistik = (Statistik) bundle.getSerializable("statistik");


        tvPunktzahl = (TextView) findViewById(R.id.tv_punkte);
        btnHauptmenue = (Button) findViewById(R.id.btn_hauptmenue);
        listView = (ListView) findViewById(R.id.listView);

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(itemsAdapter);

        btnHauptmenue.setOnClickListener(this);

        tvPunktzahl.setText(String.format("%d",statistik.getPunktzahl()));


        fuelleListe();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hauptmenue:
                starteMainActivity();
        }
    }

    private void starteMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void fuelleListe() {
        for (int i = 0; i < statistik.getGezogeneBegriffe().size(); i++) {
            String begriff = (String)statistik.getGezogeneBegriffe().get(i);
            if (statistik.getKorrekteBegriffe().contains(begriff)) {
                itemsAdapter.add(begriff + "\t\t<erraten>");



            }
            else
                itemsAdapter.add(begriff);





        }
    }
}
