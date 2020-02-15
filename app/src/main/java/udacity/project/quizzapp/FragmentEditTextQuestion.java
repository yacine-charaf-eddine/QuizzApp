package udacity.project.quizzapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentEditTextQuestion extends Fragment {

    @BindView(R.id.txt_question)
    TextView questionView;
    @BindView(R.id.img_question)
    ImageView imageView;
    @BindView(R.id.edit_answer)
    EditText editAnswer;
    FragmentEditTextQuestion.onNextClick mCallBack_next;
    FragmentEditTextQuestion.onPreviousClick mCallBack_previous;
    private Question question;
    private boolean mIsAnswered = false;
    private boolean mWasCorrect;
    private static final String ARGUMENT_QUESTION = "question";

    public FragmentEditTextQuestion() {
    }

    @OnClick({R.id.btn_next, R.id.btn_previous})
    void onBtnClicked(View v) {
        String answer;
        switch (v.getId()) {
            case R.id.btn_next:
                answer = editAnswer.getText().toString();
                mCallBack_next.onNextBtnClick(isCorrect(answer));
                break;
            case R.id.btn_previous:
                answer = editAnswer.getText().toString();
                mCallBack_previous.onPreviousBtnClick(isCorrect(answer));
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            question = (Question) getArguments().getSerializable(ARGUMENT_QUESTION);
            mCallBack_next = (FragmentEditTextQuestion.onNextClick) context;
            mCallBack_previous = (FragmentEditTextQuestion.onPreviousClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement all methods");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.edit_text_question_fragment, container, false);
        ButterKnife.bind(this, rootView);


        if (question != null) {
            questionView.setText(question.getmQuestion());
            imageView.setImageResource(question.getmImageResourceId());
        }
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

    public interface onNextClick {
        void onNextBtnClick(boolean isCorrect);
    }

    public interface onPreviousClick {
        void onPreviousBtnClick(boolean isCorrect);
    }

}
