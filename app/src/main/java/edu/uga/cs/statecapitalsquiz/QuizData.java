package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This class helps deal with fetching quizzes from the SQLite database
 */
public class QuizData {

    private final String TAG = "QuizData.java";

    private SQLiteDatabase db;
    private SQLiteOpenHelper quizzesDbHelper;
    private static final String[] columns = {
            QuizzesDBHelper.QUIZZES_COLUMN_ID,
            QuizzesDBHelper.QUIZZES_COLUMN_QUESTION1,
            QuizzesDBHelper.QUIZZES_COLUMN_QUESTION2,
            QuizzesDBHelper.QUIZZES_COLUMN_QUESTION3,
            QuizzesDBHelper.QUIZZES_COLUMN_QUESTION4,
            QuizzesDBHelper.QUIZZES_COLUMN_QUESTION5,
            QuizzesDBHelper.QUIZZES_COLUMN_QUESTION6,
            QuizzesDBHelper.QUIZZES_COLUMN_SCORE,
            QuizzesDBHelper.QUIZZES_COLUMN_DATE,
    };

    public QuizData(Context context) {
        this.quizzesDbHelper = QuizzesDBHelper.getInstance(context);
    }

    public void open() {
        db = quizzesDbHelper.getWritableDatabase();
        Log.d(TAG, "Database opened");
    }

    public void close() {
        if (quizzesDbHelper != null) {
            quizzesDbHelper.close();
            Log.d(TAG, "Database closed");
        }
    }

    public boolean isDatabaseOpen() {
        return db.isOpen();
    }

    public List<Quiz> getAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor c = null;
        int column;

        try {

            c = db.query(QuizzesDBHelper.TABLE_QUIZZES, columns, null, null, null, null, null);
            if (c != null && c.getCount() > 0) {
                while(c.moveToNext()) {
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_ID);
                    long id = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION1);
                    long question1 = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION2);
                    long question2 = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION3);
                    long question3 = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION4);
                    long question4 = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION5);
                    long question5 = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION6);
                    long question6 = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_SCORE);
                    long score = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUIZZES_COLUMN_DATE);
                    String date = c.getString(column);

                    Quiz quiz = new Quiz(question1, question2, question3, question4, question5,
                            question6, score, date);
                    quiz.setId(id);
                    quizzes.add(quiz);

                }
            }
            if (c != null) {
                Log.d(TAG, "# of records from database is " + c.getCount());
            } else {
                Log.d(TAG, "# of records from database is 0");
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return quizzes;
    }//getAllQuizzes

    public Quiz storeQuiz(Quiz q) {
//        Log.d(TAG, "Question: " + q);
        if (q == null) {
            Log.d(TAG, "q is null");
        } else {

            ContentValues val = new ContentValues();
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_ID, q.getId());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION1, q.getQuestion1());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION2, q.getQuestion2());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION3, q.getQuestion3());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION4, q.getQuestion4());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION5, q.getQuestion5());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_QUESTION6, q.getQuestion6());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_SCORE, q.getScore());
            val.put(QuizzesDBHelper.QUIZZES_COLUMN_DATE, q.getDate());

            long id = db.insert(QuizzesDBHelper.TABLE_QUIZZES, null, val);
            q.setId(id);
        }//else
//        Log.d(TAG, "Stored question with id " + id);
        return q;
    }//storeQuestion



}
