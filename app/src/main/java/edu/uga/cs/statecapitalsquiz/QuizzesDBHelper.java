package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizzesDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizzesDBHelper";

    private static final String DB_NAME = "Quizzes.db";
    private static final int DB_VERSION = 2;

    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "capital";
    public static final String QUESTIONS_COLUMN_EXTRA1 = "extra1";
    public static final String QUESTIONS_COLUMN_EXTRA2 = "extra2";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_QUESTION1 = "question1";
    public static final String QUIZZES_COLUMN_QUESTION2 = "question2";
    public static final String QUIZZES_COLUMN_QUESTION3 = "question3";
    public static final String QUIZZES_COLUMN_QUESTION4 = "question4";
    public static final String QUIZZES_COLUMN_QUESTION5 = "question5";
    public static final String QUIZZES_COLUMN_QUESTION6 = "question6";
    public static final String QUIZZES_COLUMN_SCORE = "score";
    public static final String QUIZZES_COLUMN_DATE = "date";


    private static QuizzesDBHelper helperInstance;


    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_STATE + " TEXT, "
                    + QUESTIONS_COLUMN_CAPITAL + " TEXT, "
                    + QUESTIONS_COLUMN_EXTRA1 + " TEXT, "
                    + QUESTIONS_COLUMN_EXTRA2 + " TEXT" + ")";

    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_QUESTION1 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION2 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION3 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION4 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION5 + " INTEGER, "
                    + QUIZZES_COLUMN_QUESTION6 + " INTEGER, "
                    + QUIZZES_COLUMN_SCORE + " INTEGER, "
                    + QUIZZES_COLUMN_DATE + " TEXT" + ")";


    private QuizzesDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    } //constructor

    public static synchronized QuizzesDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuizzesDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    } //Access method

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_QUESTIONS );
        db.execSQL( CREATE_QUIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " created" );
    } //onCreate

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUESTIONS );
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " upgraded" );
    } //onUpgrade
} //QuizzesDBHelper
