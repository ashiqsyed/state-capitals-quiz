package edu.uga.cs.statecapitalsquiz;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionFragment extends Fragment {
    private int questionNum;
    private static List<Question> questions;
    private String selectedAnswer;
    public final String TAG = "QuestionFragment";
    private static int numQuestionsAnswered;
    private static int numQuestionsCorrect;
    private QuizData quizData;

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
        if (numQuestionsAnswered == 5) {

            Log.d(TAG, "check");
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

            TextView resultText = new TextView(getActivity());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);

            resultText.setTextSize(font2);
            resultText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            resultText.setLayoutParams(params);
            String resultString = "Your score is " + numQuestionsCorrect + "/6";
            String dateString = Calendar.getInstance().getTime().toString();
            String completionString = "\n\nQuiz completed on: " + dateString;
            resultString = resultString + completionString;
            resultText.setText(resultString);
            layout.addView(resultText);

            quizData = new QuizData(getActivity().getBaseContext());
            quizData.open();
            long q1 = questions.get(0).getId();
            long q2 = questions.get(1).getId();
            long q3 = questions.get(2).getId();
            long q4 = questions.get(3).getId();
            long q5 = questions.get(4).getId();
            long q6 = questions.get(5).getId();
            Quiz quiz = new Quiz(q1, q2, q3, q4, q5, q6, numQuestionsCorrect, dateString, numQuestionsAnswered);
            new QuizDBWriter().execute(quiz);

            return layout;
        }
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            quizData.storeQuiz(quizzes[0]);
            return quizzes[0];
        }//doInBackground

        @Override
        protected void onPostExecute(Quiz quiz) {
//            Log.d(TAG, "Question added to database");
        }//onPostExecute
    }//QuestionDBWriter

    @Override
    public void onPause() {
        super.onPause();
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

        if (quizData != null) {
            quizData.close();
        }


    } // onPause

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy() callback");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop() callback");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.d(TAG, "onDestroyView() callback");
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        Log.d(TAG, "onDetach callback");
    }

}
