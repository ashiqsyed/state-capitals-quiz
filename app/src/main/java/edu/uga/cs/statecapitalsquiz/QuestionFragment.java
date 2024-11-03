package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
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
    private TextView date;
    private TextView score;

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
        if (numQuestionsAnswered == 6) {
            return inflater.inflate(R.layout.fragment_results, container, false);
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
            date = view.findViewById(R.id.date);
            score = view.findViewById(R.id.score);

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
        } else {

        }


            Log.d(TAG, "questionNum: " + questionNum);


        /*
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup2);
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i != -1) {
                        RadioButton button = view.findViewById(i);

                        if (button != null) {
                            selectedAnswer = button.getText().toString();
                            if (selectedAnswer.equals(questions.get(questionNum).getCapital())) {
                                Log.d(TAG, selectedAnswer + " is the capital.");
                                numQuestionsCorrect++;


                            } else {
                                Log.d(TAG, selectedAnswer + " is not the capital.");
                            }
                            numQuestionsAnswered++;
                            if (numQuestionsAnswered == 6) { //quiz is over when numQuestionsAnswered == 6
                                Quiz completedQuiz = new Quiz(); //create quiz object to store in database
                                completedQuiz.setQuestion1(questions.get(0).getId());
                                completedQuiz.setQuestion2(questions.get(1).getId());
                                completedQuiz.setQuestion3(questions.get(2).getId());
                                completedQuiz.setQuestion4(questions.get(3).getId());
                                completedQuiz.setQuestion5(questions.get(4).getId());
                                completedQuiz.setQuestion6(questions.get(5).getId());
                                completedQuiz.setScore(numQuestionsCorrect);
                                completedQuiz.setDate(Calendar.getInstance().getTime().toString());

                                Log.d(TAG, "Quiz completed at " + completedQuiz.getDate()
                                        + " with score " + completedQuiz.getScore());
                                Log.d(TAG, "questionNum is " + questionNum);
                                if (questionNum + 1 == 6) {

                                    if (date == null && score == null) {
                                        Log.e(TAG, "date and score strings are both null");
                                    }
                                    if (date != null && score != null) {
                                        date.setText("Quiz completed on " + completedQuiz.getDate());
                                        score.setText("Your score is: " + completedQuiz.getScore() + "/6");
                                    }
                                }



                                //add code to store the quiz in quiz table



                            }

                            //temporary(?) solution for handling numQuestionsAnswered
                            c1.setEnabled(false);
                            c2.setEnabled(false);
                            c3.setEnabled(false);


                        }


                    }
                }
            });
        }
         */


    }

    @Override
    public void onPause() {
        super.onPause();

    }

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
