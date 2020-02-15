package udacity.project.quizzapp;

import java.io.Serializable;

class Question implements Serializable {

    private String mQuestion;
    private String mProposition1;
    private String mProposition2;
    private String mProposition3;
    private String mProposition4;
    private String mAnswer;
    private String[] mAnswers;
    private int mImageResourceId;

    Question(String mQuestion, String mProposition1, String mProposition2, String mProposition3, String mProposition4, String mAnswer) {

        this.mQuestion = mQuestion;
        this.mProposition1 = mProposition1;
        this.mProposition2 = mProposition2;
        this.mProposition3 = mProposition3;
        this.mProposition4 = mProposition4;
        this.mAnswer = mAnswer;
    }

    Question(String mQuestion, String mProposition1, String mProposition2, String mProposition3, String mProposition4, String[] mAnswers) {

        this.mQuestion = mQuestion;
        this.mProposition1 = mProposition1;
        this.mProposition2 = mProposition2;
        this.mProposition3 = mProposition3;
        this.mProposition4 = mProposition4;
        this.mAnswers = mAnswers;
    }

    Question(String mQuestion, String mProposition1, String mProposition2, String mProposition3, String mProposition4, String mAnswer, int mImageResourceId) {

        this.mQuestion = mQuestion;
        this.mProposition1 = mProposition1;
        this.mProposition2 = mProposition2;
        this.mProposition3 = mProposition3;
        this.mProposition4 = mProposition4;
        this.mAnswer = mAnswer;
        this.mImageResourceId = mImageResourceId;
    }

    Question(String mQuestion, String mAnswer, int mImageResourceId) {

        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
        this.mImageResourceId = mImageResourceId;
    }

    String getmQuestion() {
        return mQuestion;
    }

    String getmProposition1() {
        return mProposition1;
    }

    String getmProposition2() {
        return mProposition2;
    }

    String getmProposition3() {
        return mProposition3;
    }

    String getmProposition4() {
        return mProposition4;
    }

    String getmAnswer() {
        return mAnswer;
    }

    int getmImageResourceId() {
        return mImageResourceId;
    }

    String[] getmAnswers() {
        return mAnswers;
    }
}
