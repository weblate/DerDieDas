package com.machiav3lli.derdiedas.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.data.Noun;
import com.machiav3lli.derdiedas.data.SpacedRepetitionModel;
import com.machiav3lli.derdiedas.utils.AnimationUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordFragment extends Fragment implements View.OnClickListener {

    private String correctGender;
    private Noun currentNoun;
    private List<Noun> currentNounList;

    @BindView(R.id.nounView)
    AppCompatTextView nounView;
    @BindView(R.id.m)
    AppCompatButton masculine;
    @BindView(R.id.n)
    AppCompatButton neutral;
    @BindView(R.id.f)
    AppCompatButton feminine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        ButterKnife.bind(this, view);
        masculine.setOnClickListener(this);
        neutral.setOnClickListener(this);
        feminine.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentNounList = ((WordActivity) getActivity()).getCurrentNounList();
        currentNoun = currentNounList.get(0);
        String noun = currentNoun.getNoun();
        correctGender = currentNoun.getGender();
        nounView.setText(noun);
    }

    @Override
    public void onClick(View view) {
        AppCompatButton pressedButton = (AppCompatButton) view;
        String pressedButtonGender = getResources().getResourceEntryName(view.getId());
        if (pressedButtonGender.equals(correctGender)) {
            updateList(true);
            pressedButton.setBackgroundResource(R.drawable.button_correct);
            AnimationUtil.animateJumpAndSlide(getActivity(), nounView, true);
        } else {
            updateList(false);
            AppCompatButton correctButton = getCorrectButton(correctGender);
            AnimationUtil.animateButtonDrawable(getActivity(), correctButton);
            pressedButton.setBackgroundResource(R.drawable.button_wrong);
            AnimationUtil.animateJumpAndSlide(getActivity(), nounView, false);
        }
    }

    private AppCompatButton getCorrectButton(String correctGender) {
        switch (correctGender) {
            case "f":
                return feminine;
            case "m":
                return masculine;
            default:
                return neutral;
        }
    }

    private void updateList(boolean isCorrect) {
        SpacedRepetitionModel model = new SpacedRepetitionModel();
        List<Noun> newList = model.getUpdatedNounList(currentNounList, currentNoun, isCorrect);
        ((WordActivity) getActivity()).updateNounList(newList);
    }
}
