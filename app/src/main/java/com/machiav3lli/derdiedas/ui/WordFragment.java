package com.machiav3lli.derdiedas.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.data.Noun;
import com.machiav3lli.derdiedas.data.SpacedRepetitionModel;
import com.machiav3lli.derdiedas.databinding.FragmentWordBinding;
import com.machiav3lli.derdiedas.utils.AnimationUtil;

import java.util.List;

public class WordFragment extends Fragment implements View.OnClickListener {

    private String correctGender;
    private Noun currentNoun;
    private List<Noun> currentNounList;
    private FragmentWordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentWordBinding.inflate(inflater, container, false);
        binding.m.setOnClickListener(this);
        binding.n.setOnClickListener(this);
        binding.f.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentNounList = ((WordActivity) getActivity()).getCurrentNounList();
        currentNoun = currentNounList.get(0);
        String noun = currentNoun.getNoun();
        correctGender = currentNoun.getGender();
        binding.nounView.setText(noun);
    }

    @Override
    public void onClick(View view) {
        AppCompatButton pressedButton = (AppCompatButton) view;
        String pressedButtonGender = getResources().getResourceEntryName(view.getId());
        if (pressedButtonGender.equals(correctGender)) {
            updateList(true);
            pressedButton.setBackgroundResource(R.drawable.button_correct);
            AnimationUtil.animateJumpAndSlide(getActivity(), binding.nounView, true);
        } else {
            updateList(false);
            AppCompatButton correctButton = getCorrectButton(correctGender);
            AnimationUtil.animateButtonDrawable(getActivity(), correctButton);
            pressedButton.setBackgroundResource(R.drawable.button_wrong);
            AnimationUtil.animateJumpAndSlide(getActivity(), binding.nounView, false);
        }
    }

    private AppCompatButton getCorrectButton(String correctGender) {
        switch (correctGender) {
            case "f":
                return binding.f;
            case "m":
                return binding.m;
            default:
                return binding.n;
        }
    }

    private void updateList(boolean isCorrect) {
        SpacedRepetitionModel model = new SpacedRepetitionModel();
        List<Noun> newList = model.getUpdatedNounList(currentNounList, currentNoun, isCorrect);
        ((WordActivity) getActivity()).updateNounList(newList);
    }
}
