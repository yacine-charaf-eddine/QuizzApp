package udacity.project.quizzapp;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements FragmentIntro.onStartClick,
        FragmentOutro.onStartClick,
        FragmentEditTextQuestion.onNextClick,
        FragmentEditTextQuestion.onPreviousClick,
        FragmentRadioButtonQuestion.onNextClick,
        FragmentRadioButtonQuestion.onPreviousClick,
        FragmentCheckBoxQuestion.onNextClick,
        FragmentCheckBoxQuestion.onPreviousClick {

    @BindView(R.id.value_time)
    TextView timeValue;
    @BindView(R.id.nbr_questions)
    TextView nbrQuestions;
    @BindView(R.id.btn_submit)
    Button submitBtn;
    @BindView(R.id.txt_time)
    TextView timeTxt;
    @BindView(R.id.txt_nbr_questions)
    TextView questionTxt;
    private int mFragmentIndex;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private CountDownTimer mTimer;
    private int mCorrectAnswers;
    private static final String ARGUMENT_QUESTION = "question";

    @OnClick(R.id.btn_submit)
    public void submit(View v) {

        mFragments.clear();

        setVisibility("invisible");
        submitBtn.setVisibility(View.INVISIBLE);

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        FragmentOutro fragmentOutro = new FragmentOutro();
        Bundle bundle = new Bundle();
        bundle.putInt("correctAnswers", mCorrectAnswers);
        fragmentOutro.setArguments(bundle);
        setFragment(fragmentOutro);
        Toast.makeText(this, "correct Answers: " + mCorrectAnswers, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        startQuiz();

    }

    public void onStarClicked() {

        if (mFragments.size() == 0) {
            InitializeFragments();
        }

        mFragmentIndex++;
        setFragment(mFragments.get(mFragmentIndex));
        mCorrectAnswers = 0;

        setVisibility("visible");

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1500);

        timeTxt.startAnimation(in);
        questionTxt.startAnimation(in);
        timeValue.setAnimation(in);
        nbrQuestions.setAnimation(in);

        // displaying the number of question
        String nbrQ = mFragmentIndex + "/" + (mFragments.size() - 1);
        nbrQuestions.setText(nbrQ);

        setTimer(31);
    }

    public void onNextBtnClick(boolean isCorrect) {

        if (isCorrect) {
            mCorrectAnswers++;
        }
        if (mFragmentIndex < mFragments.size() - 1) {

            mFragmentIndex++;
            setFragment(mFragments.get(mFragmentIndex));
            String nbrQ = mFragmentIndex + "/" + (mFragments.size() - 1);
            nbrQuestions.setText(nbrQ);

        }

    }

    public void onPreviousBtnClick(boolean isCorrect) {

        if (isCorrect) {
            mCorrectAnswers++;
        }
        if (mFragmentIndex > 1) {
            mFragmentIndex--;
            setFragment(mFragments.get(mFragmentIndex));
            String nbrQ = mFragmentIndex + "/" + (mFragments.size() - 1);
            nbrQuestions.setText(nbrQ);
        }

    }

    public void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss();
    }

    public void InitializeFragments() {

        // create questions

        Question question_radio1 = new Question("what does this logo stands for ?", "superAndroid", "android jetpack", "flyAndroid", "spaceDroid", "android jetpack", R.drawable.jetpack);
        Question question_radio2 = new Question("the behavior of an ACTIVITY when the device is rotated between portrait and landscape orientations is ?", "the ACTIVITY is unchanged", "the ACTIVIT's onConfigurationChanged method is called", "The ACTIVITY is destroyed and recreated", "The ACTIVITY launches a service to update the UI", "The ACTIVITY is destroyed and recreated");
        Question question_radio3 = new Question("The basic building element of android's interface is called", "View", "ViewGroup", "Layout", "Activity", "View");
        Question question_check1 = new Question("Language which is supported by Android for application development ?", "java", "VB.net", "kotlin", "R", new String[]{"java", "kotlin"});
        Question question_check2 = new Question("Which of these methods are called when the screen changes orientation from portrait to landscape in Android?", "onCreate()", "onStop()", "onRestart()", "onRestoreInstanceState()", new String[]{"onCreate()", "onStop()", "onRestoreInstanceState()"});
        Question question_edit1 = new Question("What is the value of String number after the following code snippet", "\"3\"", R.drawable.code);

        // create && initialize fragments

        FragmentIntro fragmentIntro = new FragmentIntro();

        FragmentRadioButtonQuestion fragmentRadioButtonQuestion1 = new FragmentRadioButtonQuestion();
        setQuestion(fragmentRadioButtonQuestion1, question_radio1);

        FragmentEditTextQuestion fragmentEditTextQuestion = new FragmentEditTextQuestion();
        setQuestion(fragmentEditTextQuestion, question_edit1);

        FragmentRadioButtonQuestion fragmentRadioButtonQuestion2 = new FragmentRadioButtonQuestion();
        setQuestion(fragmentRadioButtonQuestion2, question_radio2);

        FragmentRadioButtonQuestion fragmentRadioButtonQuestion3 = new FragmentRadioButtonQuestion();
        setQuestion(fragmentRadioButtonQuestion3, question_radio3);

        FragmentCheckBoxQuestion fragmentCheckBoxQuestion1 = new FragmentCheckBoxQuestion();
        setQuestion(fragmentCheckBoxQuestion1, question_check1);

        FragmentCheckBoxQuestion fragmentCheckBoxQuestion2 = new FragmentCheckBoxQuestion();
        setQuestion(fragmentCheckBoxQuestion2, question_check2);

        mFragments.add(fragmentIntro);
        mFragments.add(fragmentRadioButtonQuestion1);
        mFragments.add(fragmentCheckBoxQuestion1);
        mFragments.add(fragmentEditTextQuestion);
        mFragments.add(fragmentRadioButtonQuestion2);
        mFragments.add(fragmentCheckBoxQuestion2);
        mFragments.add(fragmentRadioButtonQuestion3);
        mFragmentIndex = 0;
    }

    public void setQuestion(Fragment fragment, Question question) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT_QUESTION, question);
        fragment.setArguments(bundle);
    }

    public void startQuiz() {

        InitializeFragments();
        setFragment(mFragments.get(mFragmentIndex));

        setVisibility("invisible");
        submitBtn.setVisibility(View.INVISIBLE);

    }

    public void setVisibility(String visibility) {

        if (visibility.equals("visible")) {
            timeTxt.setVisibility(View.VISIBLE);
            questionTxt.setVisibility(View.VISIBLE);
            timeValue.setVisibility(View.VISIBLE);
            nbrQuestions.setVisibility(View.VISIBLE);
        } else {
            timeTxt.setVisibility(View.INVISIBLE);
            questionTxt.setVisibility(View.INVISIBLE);
            timeValue.setVisibility(View.INVISIBLE);
            nbrQuestions.setVisibility(View.INVISIBLE);
        }
    }

    public void setTimer(long seconds) {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String time = ":" + Long.toString(millisUntilFinished / 1000);
                timeValue.setText(time);

                if (millisUntilFinished / 1000 == 15) {
                    submitBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                submit(submitBtn);
            }
        }.start();
    }

}

