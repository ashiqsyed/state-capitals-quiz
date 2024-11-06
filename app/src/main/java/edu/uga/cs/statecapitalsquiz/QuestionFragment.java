package edu.uga.cs.statecapitalsquiz;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.apache.commons.logging.LogFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QuestionFragment extends Fragment {
    private static final org.apache.commons.logging.Log log = LogFactory.getLog(QuestionFragment.class);
    private int questionNum;
    private static List<Question> questions;
    private String selectedAnswer;
    public final String TAG = "QuestionFragment";
    private static int numQuestionsCorrect;
    private QuizData quizData;
    private View view;
    private TextView resultText;

    public QuestionFragment() {

    }

    public static QuestionFragment newInstance(int questionNum, List<Question> q) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("questionNum", questionNum);
        fragment.setArguments(args);
        questions = q;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt("questionNum");
        }
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (questionNum == 6) {

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);


            TextView resultsText = new TextView(getActivity());
            int font = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 28,
                    getActivity().getResources().getDisplayMetrics());
            resultsText.setTextSize(font);
            resultsText.setText("Results");
            resultsText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(resultsText);

            int font2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    14, getActivity().getResources().getDisplayMetrics());

            resultText = new TextView(getActivity());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);



            resultText.setTextSize(font2);
            resultText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            resultText.setLayoutParams(params);
            layout.addView(resultText);


            return layout;

        }
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        if (questionNum < 6) {
            TextView tv = view.findViewById(R.id.question);
            String text = "Question  " + (questionNum + 1) + "\n\nThe capital of "
                    + questions.get(questionNum).getState() + " is: ";
            if (tv != null) {
                tv.setText(text);
            }

            RadioButton c1 = view.findViewById(R.id.choice1);
            RadioButton c2 = view.findViewById(R.id.choice2);
            RadioButton c3 = view.findViewById(R.id.choice3);

            List<String> answers = new ArrayList<String>();
            answers.add(questions.get(questionNum).getCapital());
            answers.add(questions.get(questionNum).getExtra1());
            answers.add(questions.get(questionNum).getExtra2());

            Collections.shuffle(answers);

            if (c1 != null && c2 != null && c3 != null) {
                c1.setText(answers.get(0));
                c2.setText(answers.get(1));
                c3.setText(answers.get(2));
            }

            RadioGroup radioGroup = view.findViewById(R.id.radioGroup2);
            if (radioGroup != null) {
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i != -1) {
                            RadioButton button = view.findViewById(i);
                            if (button != null) {
                                selectedAnswer = button.getText().toString();
                            } // if
                        } // if
                    } // onCheckChanged
                }); // listener
            } // if

        Log.d(TAG, "questionNum: " + questionNum);

        } // if
    } // onViewCreated

    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {
        @Override
        protected Quiz doInBackground(Quiz... quizzes) {

//            Log.d(TAG, "In QuestionDBWriter: " + questions[0]);
            quizData.updateQuiz(quizzes[0]);
            return quizzes[0];
        }//doInBackground

        @Override
        protected void onPostExecute(Quiz quiz) {
            quizData.close();
        }//onPostExecute
    }//QuestionDBWriter

    @Override
    public void onPause() {
        super.onPause();
        quizData = new QuizData(view.getContext());
        quizData.open();
        Quiz quiz = quizData.getCurrentQuiz();
        int numQuestionsAnswered = (int) quiz.getCurrentQuestion();
        numQuestionsCorrect = (int) quiz.getScore();

        if(selectedAnswer != null) {
            Log.d(TAG, "numQuestionsAnswered is " + numQuestionsAnswered);
            if (selectedAnswer.equals(questions.get(questionNum).getCapital())) {
                Log.d(TAG, selectedAnswer + " is the capital.");

                numQuestionsCorrect++;
                Log.d(TAG, "Score: " + numQuestionsCorrect);

            } else {
                Log.d(TAG, selectedAnswer + " is not the capital.");
            } // else
            numQuestionsAnswered++;
        } // if

        quiz.setCurrentQuestion(numQuestionsAnswered);
        quiz.setScore(numQuestionsCorrect);
        if(questionNum < 5) {
            new QuizDBWriter().execute(quiz);
        } else {
            quizData.updateQuiz(quiz);
            quizData.close();
        }
    } // onPause

    @Override
    public void onResume() {
        super.onResume();
        if(resultText != null) {
            quizData = new QuizData(view.getContext());
            quizData.open();
            Quiz quiz = quizData.getCurrentQuiz();
            numQuestionsCorrect = (int) quiz.getScore();
            String dateString = Calendar.getInstance().getTime().toString();
            quiz.setDate(dateString);
            String resultString = "Your score is " + numQuestionsCorrect + "/6";
            String completionString = "\n\nQuiz completed on: " + dateString;
            resultString = resultString + completionString;
            resultText.setText(resultString);
            new QuizDBWriter().execute(quiz);
        } //if
    } //onResume


}
