package com.machiav3lli.derdiedas.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.data.Noun;
import com.machiav3lli.derdiedas.data.SpacedRepetitionModel;
import com.machiav3lli.derdiedas.utils.AnimationUtil;

import java.util.List;

public class WordFragment extends Fragment implements View.OnClickListener {

    private String correctGender;
    private TextView nounView;
    private View v;
    private Noun currentNoun;
    private List<Noun> currentNounList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_word, container, false);
        nounView = v.findViewById(R.id.nounView);
        v.findViewById(R.id.m).setOnClickListener(this);
        v.findViewById(R.id.n).setOnClickListener(this);
        v.findViewById(R.id.f).setOnClickListener(this);
        return v;
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
        Button pressedButton = (Button) view;
        String pressedButtonGender = getResources().getResourceEntryName(view.getId());
        if (pressedButtonGender.equals(correctGender)) {
            updateList(true);
            pressedButton.setBackgroundResource(R.drawable.button_correct);
            AnimationUtil.animateJumpAndSlide(getActivity(), nounView, true);
        } else {
            updateList(false);
            int idResource = getResources().getIdentifier(correctGender, "id", getActivity().getPackageName());
            Button correctButton = v.findViewById(idResource);
            AnimationUtil.animateButtonDrawable(getActivity(), correctButton);
            pressedButton.setBackgroundResource(R.drawable.button_wrong);
            AnimationUtil.animateJumpAndSlide(getActivity(), nounView, false);
        }
    }

    private void updateList(boolean isCorrect) {
        SpacedRepetitionModel model = new SpacedRepetitionModel();
        List<Noun> newList = model.getUpdatedNounList(currentNounList, currentNoun, isCorrect);
        ((WordActivity) getActivity()).updateNounList(newList);
    }
}
