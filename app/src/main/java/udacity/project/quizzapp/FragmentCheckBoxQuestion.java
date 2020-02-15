package udacity.project.quizzapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCheckBoxQuestion extends Fragment {

    @BindView(R.id.checkbox_prp_1)
    CheckBox checkBox1;
    @BindView(R.id.checkbox_prp_2)
    CheckBox checkBox2;
    @BindView(R.id.checkbox_prp_3)
    CheckBox checkBox3;
    @BindView(R.id.checkbox_prp_4)
    CheckBox checkBox4;
    @BindView(R.id.txt_question)
    TextView questionView;
    @BindView(R.id.img_question)
    ImageView imageView;
    @BindView(R.id.btn_next)
    ImageView nextBtn;
    @BindView(R.id.btn_previous)
    ImageView previousBtn;
    FragmentCheckBoxQuestion.onNextClick mCallBack_next;
    FragmentCheckBoxQuestion.onPreviousClick mCallBack_previous;
    private Question question;
    private boolean mIsAnswered = false;
    private boolean mWasCorrect;
    private ArrayList<String> mCheckedBoxesValues = new ArrayList<>();
    private ArrayList<CheckBox> mCheckBoxes = new ArrayList<>();
    private static final String ARGUMENT_QUESTION = "question";

    public FragmentCheckBoxQuestion() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            question = (Question) getArguments().getSerializable(ARGUMENT_QUESTION);
            mCallBack_next = (FragmentCheckBoxQuestion.onNextClick) context;
            mCallBack_previous = (FragmentCheckBoxQuestion.onPreviousClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement all methods");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.check_box_question_fragment, container, false);
        ButterKnife.bind(this, rootView);

        if (question != null) {

            questionView.setText(question.getmQuestion());
            checkBox1.setText(question.getmProposition1());
            checkBox2.setText(question.getmProposition2());
            checkBox3.setText(question.getmProposition3());
            checkBox4.setText(question.getmProposition4());

            if (question.getmImageResourceId() != 0) {
                imageView.setImageResource(question.getmImageResourceId());
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        mCheckBoxes.add(checkBox1);
        mCheckBoxes.add(checkBox2);
        mCheckBoxes.add(checkBox3);
        mCheckBoxes.add(checkBox4);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCheckBoxes();
                mCallBack_next.onNextBtnClick(isCorrect(mCheckedBoxesValues));
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCheckBoxes();
                mCallBack_previous.onPreviousBtnClick(isCorrect(mCheckedBoxesValues));
            }
        });
        return rootView;
    }

    public boolean isCorrect(ArrayList<String> answers) {

        if (!mIsAnswered) { // answering for the first time

            // the answer is correct in the first try
            if (answers.size() == question.getmAnswers().length) {

                int i = 0;
                while (i < question.getmAnswers().length) {

                    if (answers.contains(question.getmAnswers()[i])) {
                        i++;
                    } else { // if not
                        mIsAnswered = true;
                        mWasCorrect = false;
                        return false;
                    }
                }
                mIsAnswered = true;
                mWasCorrect = true;
                return true;
            } else {
                mIsAnswered = true;
                mWasCorrect = false;
                return false;
            }


        } else { // answering for 2nd, 3rd .. time

            // the answer is correct in the 2nd || 3rd.. time were previously the answer was wrong
            if (!mWasCorrect && answers.size() == question.getmAnswers().length) {
                int i = 0;
                while (i < question.getmAnswers().length) {

                    if (answers.contains(question.getmAnswers()[i])) {
                        i++;
                    } else {
                        return false;
                    }
                }
                mWasCorrect = true;
                return true;
            }

        }
        return false;
    }

    public void addCheckBoxes() {

        for (CheckBox c : mCheckBoxes) {
            if (c.isChecked()) {
                mCheckedBoxesValues.add(c.getText().toString());
            }
        }
    }

    public interface onNextClick {
        void onNextBtnClick(boolean isCorrect);
    }

    public interface onPreviousClick {
        void onPreviousBtnClick(boolean isCorrect);
    }

}
