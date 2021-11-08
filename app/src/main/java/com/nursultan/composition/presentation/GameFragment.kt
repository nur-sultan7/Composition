package com.nursultan.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.nursultan.composition.R
import com.nursultan.composition.databinding.FragmentGameBinding
import com.nursultan.composition.domain.entity.GameResult
import com.nursultan.composition.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseLevel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startGame(level)
        binding.progressBar.secondaryProgress = viewModel.requiredPercentageOfRightAnswers
        observeModelView()
        setClickOptions()
    }
    private fun setClickOptions()
    {
        for (tvOption in tvOptions)
            tvOption.setOnClickListener {
                checkAnswer(tvOption.text)
            }
    }
    private fun observeModelView()
    {
        viewModel.isRequiredCount.observe(viewLifecycleOwner, {
            binding.tvProgress.setTextColor(getIsRightColor(it))
        })
        viewModel.isRequiredPercentage.observe(viewLifecycleOwner, {
            binding.progressBar.progressTintList = ColorStateList.valueOf(getIsRightColor(it))
        })
        viewModel.gameResult.observe(viewLifecycleOwner, {
            launchGameResultFragment(it)
        })
        viewModel.gameQuestion.observe(viewLifecycleOwner, {
            binding.gameSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (index in 0 until tvOptions.size)
            {
                tvOptions[index].text = it.option[index].toString()
            }
            binding.tvOption1.text = it.option[0].toString()
            binding.tvOption2.text = it.option[1].toString()
            binding.tvOption3.text = it.option[2].toString()
            binding.tvOption4.text = it.option[3].toString()
            binding.tvOption5.text = it.option[4].toString()
            binding.tvOption6.text = it.option[5].toString()
        })
        viewModel.timerTime.observe(viewLifecycleOwner, {
            binding.tvTimer.text = it.toString()
        })
        viewModel.progressString.observe(viewLifecycleOwner, {
            binding.tvProgress.text = it
        })
        viewModel.percentageOfRightAnswers.observe(viewLifecycleOwner, {
            binding.progressBar.setProgress(it,true)
        })
    }

    private fun getIsRightColor(boolean: Boolean): Int {
        return if (boolean) {
            ContextCompat.getColor(requireContext(),android.R.color.holo_green_dark)
        } else {
            ContextCompat.getColor(requireContext(),android.R.color.holo_red_light)
        }
    }

    private fun checkAnswer(answer: CharSequence) {
        viewModel.checkIsRightAnswer(answer.toString().toInt())
    }


    private fun launchGameResultFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameResultFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseLevel() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {
        const val NAME = "GameFragment"
        private const val ZERO_RIGHT_ANSWERS = 0
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}