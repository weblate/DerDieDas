package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.machiav3lli.derdiedas.R
import com.machiav3lli.derdiedas.data.Noun
import com.machiav3lli.derdiedas.data.getUpdatedNounList
import com.machiav3lli.derdiedas.databinding.FragmentWordBinding
import com.machiav3lli.derdiedas.utils.AnimationUtil.animateButtonDrawable
import com.machiav3lli.derdiedas.utils.AnimationUtil.animateJumpAndSlide
import com.machiav3lli.derdiedas.utils.getStringByName

class WordFragment : Fragment(), View.OnClickListener {
    private var currentNounList: MutableList<Noun> = mutableListOf()
    private lateinit var currentNoun: Noun
    private var correctGender: String? = null
    private lateinit var binding: FragmentWordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentWordBinding.inflate(inflater, container, false)
        binding.m.setOnClickListener(this)
        binding.n.setOnClickListener(this)
        binding.f.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentNounList = (requireActivity() as WordActivity).currentNounList
        currentNoun = currentNounList[0]
        val noun = currentNoun.noun
        correctGender = currentNoun.gender
        binding.nounText.text = noun
        binding.translatedText.text = requireContext().getStringByName(noun)
    }

    override fun onClick(view: View) {
        val pressedButton = view as AppCompatButton
        val pressedButtonGender = resources.getResourceEntryName(view.getId())
        if (pressedButtonGender == correctGender) {
            updateList(true)
            pressedButton.setBackgroundResource(R.drawable.button_correct)
            binding.nounView.animateJumpAndSlide(requireActivity(), true)
        } else {
            updateList(false)
            val correctButton = getCorrectButton(correctGender)
            animateButtonDrawable(correctButton)
            pressedButton.setBackgroundResource(R.drawable.button_wrong)
            binding.nounView.animateJumpAndSlide(requireActivity(), false)
        }
    }

    private fun getCorrectButton(correctGender: String?): AppCompatButton = when (correctGender) {
        "f" -> binding.f
        "m" -> binding.m
        else -> binding.n
    }

    private fun updateList(isCorrect: Boolean) {
        val newList = currentNounList.getUpdatedNounList(currentNoun, isCorrect)
        (requireActivity() as WordActivity).updateNounList(newList)
    }
}