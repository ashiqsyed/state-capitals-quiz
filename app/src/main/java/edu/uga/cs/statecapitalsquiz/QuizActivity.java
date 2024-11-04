package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private static final org.apache.commons.logging.Log log = LogFactory.getLog(QuizActivity.class);
    private final String TAG = "QuizActivity.java";
    private ViewPager2 viewpager;
    private QuizData quizData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        List<Question> questions = quizSetup();
        viewpager = findViewById(R.id.viewpager);
        QuestionsPagerAdapter questionsAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(),
                getLifecycle(), questions);
        viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewpager.setAdapter(questionsAdapter);
        viewpager.registerOnPageChangeCallback(listener);
        Log.d(TAG, "There are " + questionsAdapter.getItemCount() + " questions");

    }

    private List<Question> quizSetup() {
        QuestionData questionData = new QuestionData(getBaseContext());
        questionData.open();
        List<Question> allQuestions = questionData.getAllQuestions();
        if(allQuestions != null) {
            List<Question> quizQuestions = new ArrayList<>();
            while (quizQuestions.size() < 6) {
                Random r = new Random();
                int i = r.nextInt(allQuestions.size());
                quizQuestions.add(allQuestions.get(i));
                allQuestions.remove(i);
            } // while
            questionData.close();
            quizQuestions.add(new Question());
            /*
            quizData = new QuizData(getBaseContext());
            quizData.open();
            long q1 = quizQuestions.get(0).getId();
            long q2 = quizQuestions.get(1).getId();
            long q3 = quizQuestions.get(2).getId();
            long q4 = quizQuestions.get(3).getId();
            long q5 = quizQuestions.get(4).getId();
            long q6 = quizQuestions.get(5).getId();
            Quiz quiz = new Quiz(q1, q2, q3, q4, q5, q6, 0, null, 0);
            new QuizDBWriter().execute(quiz);
             */
            return quizQuestions;
        } else {
            Log.d("QuizActivity.java", "questions is null");
            questionData.close();
            return null;
        } // else
    } //quizSetup

    private ViewPager2.OnPageChangeCallback listener = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (position == 6) {
                viewpager.setUserInputEnabled(false);
            } //if
        } //onPageSelected
    };
    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... quizzes) {
            quizData.storeQuiz(quizzes[0]);
            return quizzes[0];
        }//doInBackground

        @Override
        protected void onPostExecute(Quiz quiz) {
            quizData.close();
        }//onPostExecute
    }//QuestionDBWriter

    /*
    @Override
    protected void onResume() {
        super.onResume();
        QuestionData questionData = new QuestionData(getBaseContext());
        questionData.open();
        List<Question> allQuestions = questionData.getAllQuestions();
        questionData.close();
        quizData = new QuizData(getBaseContext());
        quizData.open();
        Quiz quiz = quizData.getCurrentQuiz();
        quizData.close();
        if (quiz != null) {
            List<Question> quizQuestions = new ArrayList<>();
            quizQuestions.add(allQuestions.get((int) quiz.getQuestion1()));
            quizQuestions.add(allQuestions.get((int) quiz.getQuestion2()));
            quizQuestions.add(allQuestions.get((int) quiz.getQuestion3()));
            quizQuestions.add(allQuestions.get((int) quiz.getQuestion4()));
            quizQuestions.add(allQuestions.get((int) quiz.getQuestion5()));
            quizQuestions.add(allQuestions.get((int) quiz.getQuestion6()));
            viewpager = findViewById(R.id.viewpager);
            QuestionsPagerAdapter questionsAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(),
                    getLifecycle(), quizQuestions);
            viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            viewpager.setAdapter(questionsAdapter);
            viewpager.registerOnPageChangeCallback(listener);
        } //if
    }
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewpager.unregisterOnPageChangeCallback(listener);
    }
}