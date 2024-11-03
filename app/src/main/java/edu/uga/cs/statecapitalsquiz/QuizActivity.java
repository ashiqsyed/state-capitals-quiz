package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private final String TAG = "QuizActivity.java";
    private ViewPager2 viewpager;

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
            if (position == 7) {
                viewpager.setUserInputEnabled(false);
            } //if
        } //onPageSelected
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewpager.unregisterOnPageChangeCallback(listener);
    }
}