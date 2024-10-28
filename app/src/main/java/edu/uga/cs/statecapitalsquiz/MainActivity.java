package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout layout = findViewById(R.id.main);
        try {
            InputStream stream = getAssets().open("state_capitals.csv");
            CSVReader csvReader = new CSVReader(new InputStreamReader(stream));
            String[] nextRow;

            while ((nextRow = csvReader.readNext()) != null) {
                TextView tv = new TextView(getBaseContext());
                tv.setText(nextRow[1]);
                layout.addView(tv);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error reading CSV file");
        }
    }
}