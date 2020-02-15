package udacity.project.quizzapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentOutro extends Fragment {

    @BindView(R.id.img_congrats)
    ImageView img_congrats;
    @BindView(R.id.resaul_1)
    TextView result1;
    @BindView(R.id.resaul_2)
    TextView result2;
    FragmentOutro.onStartClick mCallBack;
    private int mCorrectAnswers;

    public FragmentOutro() {
    }

    @OnClick(R.id.btn_restart)
    public void restart(View v) {
        mCallBack.onStarClicked();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.outro_fragment, container, false);
        ButterKnife.bind(this, rootView);

        if (mCorrectAnswers == 6) {

            setOutro(R.drawable.allcorrectquestion, "correct Answers: " + mCorrectAnswers, R.string.all_answers_correct);

        } else if (mCorrectAnswers == 0) {

            setOutro(R.drawable.nullcorrectanswers, "correct Answers: " + mCorrectAnswers, R.string.null_answers_correct);

        } else {

            setOutro(R.drawable.somecorrectanswers, "correct Answers: " + mCorrectAnswers, R.string.some_answers_correct);

        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCorrectAnswers = getArguments().getInt("correctAnswers");
            mCallBack = (FragmentOutro.onStartClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnStarClick");
        }
    }

    public void setOutro(int imgId, String correctAnswers, int msg) {

        img_congrats.setImageResource(imgId);
        result1.setText(correctAnswers);
        result2.setText(msg);
    }

    public interface onStartClick {

        void onStarClicked();
    }

}
