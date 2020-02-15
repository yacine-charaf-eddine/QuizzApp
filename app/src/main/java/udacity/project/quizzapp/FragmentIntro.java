package udacity.project.quizzapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentIntro extends Fragment {

    onStartClick mCallBack;

    public FragmentIntro() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.intro_fragment, container, false);

        Button startBtn = rootView.findViewById(R.id.btn_start);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallBack.onStarClicked();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            mCallBack = (onStartClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "OnStarClick must be implemented !!");
        }

    }

    public interface onStartClick {

        void onStarClicked();
    }

}

