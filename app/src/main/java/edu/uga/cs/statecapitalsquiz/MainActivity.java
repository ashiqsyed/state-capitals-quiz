package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Splash screen/entry point for the app that populates the questions table based on the State
 * capital CSV file if the table is not already populated.
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity.java";

    private QuestionData questionData = null;

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

        try {
            InputStream stream = getAssets().open("state_capitals.csv");
            CSVReader csvReader = new CSVReader(new InputStreamReader(stream));
            String[] nextRow = csvReader.readNext(); //lets the csvreader skip the first line which is the structure of csv lines
            questionData = new QuestionData(getBaseContext());
            questionData.open();
            List<Question> questions = questionData.getAllQuestions();
            if(questions.size() != 50) {

                while ((nextRow = csvReader.readNext()) != null) {
                    String state = nextRow[0];
                    String capital = nextRow[1];
                    String extra1 = nextRow[2];
                    String extra2 = nextRow[3];

                    Question question = new Question(state, capital, extra1, extra2);

                    new QuestionDBWriter().execute(question);

                }// while
                questions = questionData.getAllQuestions();
            }//if
            Log.d(TAG, "Size of allQuestions is " + questions.size());
            Log.d(TAG, "questionSet: " + questions);

            Button button = findViewById(R.id.button);
            button.setOnClickListener((view) -> {
                questionData.close();
                Intent intent = new Intent(view.getContext(), QuizActivity.class);
                startActivity(intent);
            });

            Button viewResultsButton = findViewById(R.id.button2);
            viewResultsButton.setOnClickListener((view) -> {
                Intent intent = new Intent(view.getContext(), ViewQuizzesActivity.class);
                startActivity(intent);
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * Asynchronously stores questions into the database.
     */
    public class QuestionDBWriter extends AsyncTask<Question, Question> {
        @Override
        protected Question doInBackground(Question... questions) {
            questionData.storeQuestion(questions[0]);
            return questions[0];
        }//doInBackground

        @Override
        protected void onPostExecute(Question question) {
        }//onPostExecute
    }//QuestionDBWriter

    /**
     * Closes any open instances of the database when the activity is put in the background.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (questionData != null) {
            questionData.close();
        }
    }//onPause

} //MainActivity