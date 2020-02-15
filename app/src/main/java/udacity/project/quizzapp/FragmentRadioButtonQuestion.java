package udacity.project.quizzapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentRadioButtonQuestion extends Fragment {

    @BindView(R.id.txt_question)
    TextView questionView;
    @BindView(R.id.img_question)
    ImageView imageView;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.radio_prp1)
    RadioButton prp1View;
    @BindView(R.id.radio_prp2)
    RadioButton prp2View;
    @BindView(R.id.radio_prp3)
    RadioButton prp3View;
    @BindView(R.id.radio_prp4)
    RadioButton prp4View;
    @BindView(R.id.btn_next)
    ImageView nextBtn;
    @BindView(R.id.btn_previous)
    ImageView previousBtn;
    onNextClick mCallBack_next;
    onPreviousClick mCallBack_previous;
    private Question question;
    private boolean mIsAnswered = false;
    private boolean mWasCorrect;
    private static final String ARGUMENT_QUESTION = "question";

    public FragmentRadioButtonQuestion() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            question = (Question) getArguments().getSerializable(ARGUMENT_QUESTION);
            mCallBack_next = (onNextClick) context;
            mCallBack_previous = (onPreviousClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement all methods");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.radio_button_question_fragment, container, false);
        ButterKnife.bind(this, rootView);

        if (question != null) {
            questionView.setText(question.getmQuestion());
            prp1View.setText(question.getmProposition1());
            prp2View.setText(question.getmProposition2());
            prp3View.setText(question.getmProposition3());
            prp4View.setText(question.getmProposition4());

            if (question.getmImageResourceId() != 0) {
                imageView.setImageResource(question.getmImageResourceId());
            } else {
                imageView.setVisibility(View.GONE);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) radioGroup.getLayoutParams();
                params.verticalBias = 0.5f;
                radioGroup.setLayoutParams(params);

            }
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = rootView.findViewById(selectedId);
                mCallBack_next.onNextBtnClick(isCorrect(getSelectedAnswer(radioButton)));
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = rootView.findViewById(selectedId);
                mCallBack_previous.onPreviousBtnClick(isCorrect(getSelectedAnswer(radioButton)));
            }
        });
        return rootView;
    }

    public boolean isCorrect(String answer) {

        if (!mIsAnswered) { // answering for the first time

            // the answer is correct in the first try
            if (answer.equals(question.getmAnswer())) {
                mIsAnswered = true;
                mWasCorrect = true;
                return true;
            } else // if  not
                mIsAnswered = true;
            mWasCorrect = false;
            return false;
        } else { // answering for 2nd, 3rd .. time

            // the answer is correct in the 2nd || 3rd.. time were previously the answer was wrong
            if (!mWasCorrect && answer.equals(question.getmAnswer())) {
                mWasCorrect = true;
                return true;
            }
        }
        return false;
    }

    public String getSelectedAnswer(RadioButton radioButton) {

        if (radioButton != null) {
            return radioButton.getText().toString();
        }
        return "";
    }


    public interface onNextClick {
        void onNextBtnClick(boolean isCorrect);
    }

    public interface onPreviousClick {
        void onPreviousBtnClick(boolean isCorrect);
    }

}
