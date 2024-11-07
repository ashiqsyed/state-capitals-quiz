package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

/**
 * Activity to display the past completed quizzes.
 */
public class ViewQuizzesActivity extends AppCompatActivity {

    private QuizData quizData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_quizzes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quizData = new QuizData(getBaseContext());
        quizData.open();
        List<Quiz> quizzes = quizData.getAllQuizzes();
        quizData.close();
        String quizResults = "";
        int count = 1;
        for (int i = quizzes.size(); i > 0; i--) {
            Quiz temp = quizzes.get(i - 1);
            quizResults = quizResults + "Quiz " + count + ": score = "
                    + temp.getScore() + "/6 date completed = " + temp.getDate() + "\n";
            count++;
        } // for

        TextView results = findViewById(R.id.resultsText);
        results.setText(quizResults);
    }
}