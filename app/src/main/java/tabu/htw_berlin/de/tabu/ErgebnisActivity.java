package tabu.htw_berlin.de.tabu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by philipp on 13.01.16.
 */
public class ErgebnisActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnHauptmenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);

        btnHauptmenue = (Button) findViewById(R.id.btn_hauptmenue);

        btnHauptmenue.setOnClickListener(this);
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
}
