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
 * This class helps deal with fetching questions from the SQLite database
 */
public class QuestionData {
    private final String TAG = "QuestionData.java";

    private SQLiteDatabase db;
    private SQLiteOpenHelper questionsDbHelper;
    private static final String[] columns = {
        QuizzesDBHelper.QUESTIONS_COLUMN_CAPITAL,
        QuizzesDBHelper.QUESTIONS_COLUMN_ID,
        QuizzesDBHelper.QUESTIONS_COLUMN_EXTRA1,
        QuizzesDBHelper.QUESTIONS_COLUMN_EXTRA2,
        QuizzesDBHelper.QUESTIONS_COLUMN_STATE,

    };

    public QuestionData(Context context) {
        this.questionsDbHelper = QuizzesDBHelper.getInstance(context);
    }

    public void open() {

        db = questionsDbHelper.getWritableDatabase();
        Log.d(TAG, "Database opened");
    }

    public void close() {
        if (questionsDbHelper != null) {
            questionsDbHelper.close();
            Log.d(TAG, "Database closed");
        }
    }

    public boolean isDatabaseOpen() {
        return db.isOpen();
    }

    public List<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        Cursor c = null;
        int column;

        try {

            c = db.query(QuizzesDBHelper.TABLE_QUESTIONS, columns, null, null, null, null, null);
            if (c != null && c.getCount() > 0) {
                while(c.moveToNext()) {
                    column = c.getColumnIndex(QuizzesDBHelper.QUESTIONS_COLUMN_ID);
                    long id = c.getLong(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUESTIONS_COLUMN_CAPITAL);
                    String capital = c.getString(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUESTIONS_COLUMN_EXTRA1);
                    String extra1 = c.getString(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUESTIONS_COLUMN_EXTRA2);
                    String extra2 = c.getString(column);
                    column = c.getColumnIndex(QuizzesDBHelper.QUESTIONS_COLUMN_STATE);
                    String state = c.getString(column);

                    Question question = new Question(state, capital, extra1, extra2);
                    question.setId(id);
                    questions.add(question);

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
        return questions;
    }

    public Question storeQuestion(Question q) {
//        Log.d(TAG, "Question: " + q);
        if (q == null) {
            Log.d(TAG, "q is null");
        }
        ContentValues val = new ContentValues();
        val.put(QuizzesDBHelper.QUESTIONS_COLUMN_CAPITAL, q.getCapital());
        val.put(QuizzesDBHelper.QUESTIONS_COLUMN_STATE, q.getState());
        val.put(QuizzesDBHelper.QUESTIONS_COLUMN_EXTRA1, q.getExtra1());
        val.put(QuizzesDBHelper.QUESTIONS_COLUMN_EXTRA2, q.getExtra2());

        long id = db.insert(QuizzesDBHelper.TABLE_QUESTIONS, null, val);
        q.setId(id);

//        Log.d(TAG, "Stored question with id " + id);
        return q;
    }
}
