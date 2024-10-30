package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.java";

    private QuestionData questionData = null;
    private Question question;

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
        Log.d(TAG, "MainActivity.onCreate()");
        LinearLayout layout = findViewById(R.id.main);
        try {
            InputStream stream = getAssets().open("state_capitals.csv");
            CSVReader csvReader = new CSVReader(new InputStreamReader(stream));
            String[] nextRow = csvReader.readNext(); //lets the csvreader skip the first line which is the structure of csv lines
            QuestionDBWriter writer = new QuestionDBWriter();
            questionData = new QuestionData(getBaseContext());
            questionData.open();
            while ((nextRow = csvReader.readNext()) != null) {
                TextView tv = new TextView(getBaseContext());
                String state = nextRow[0];
                String capital = nextRow[1];
                String extra1 = nextRow[2];
                String extra2 = nextRow[3];
                tv.setText(capital + ", " + state);
                layout.addView(tv);

                question = new Question(state, capital, extra1, extra2);


                writer.execute(question);

            }
            List<Question> questions = questionData.getAllQuestions();
            Set<Question> questionSet = new HashSet<>(questions);
            Log.d(TAG, "Size of allQuestions is " + questionSet.size());
            Log.d(TAG, "questionSet: " + questionSet);





        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public class QuestionDBWriter extends AsyncTask<Question, Question> {
        @Override
        protected Question doInBackground(Question... questions) {

//            Log.d(TAG, "In QuestionDBWriter: " + questions[0]);
            questionData.storeQuestion(questions[0]);
            return questions[0];
        }

        @Override
        protected void onPostExecute(Question question) {
//            Log.d(TAG, "Question added to database");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}