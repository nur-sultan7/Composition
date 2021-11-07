package com.nursultan.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nursultan.composition.R
import com.nursultan.composition.databinding.FragmentGameBinding
import com.nursultan.composition.domain.entity.GameResult
import com.nursultan.composition.domain.entity.GameSettings
import com.nursultan.composition.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private var iif = 10
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseLevel()
        viewModel = GameViewModel()
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
        viewModel..observe(viewLifecycleOwner, {
            gameSettings = it
            val timerTime = it.gameTimeInSeconds
            binding.tvTimer.text = timerTime.toString()
            binding.progressBar.secondaryProgress = it.minPercentOfRightAnswers
            viewModel.generateGameQuestion(it.maxSumValue)
            viewModel.startGame(timerTime.toLong())
        })
        viewModel.gameIsFinished.observe(viewLifecycleOwner, {
            if (it) {
                launchGameResultFragment(viewModel.getGameResult())
            }
        })
        viewModel.gameQuestion.observe(viewLifecycleOwner, {
            binding.gameSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
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
        viewModel.progressString.observe(viewLifecycleOwner,{
            binding.tvProgress.text =it
        })
        viewModel.percentageOfRightAnswers.observe(viewLifecycleOwner,{
            binding.progressBar.progress=it
        })

        binding.tvOption1.setOnClickListener {
            checkAnswer(binding.tvOption1.text)
        }
        binding.tvOption2.setOnClickListener {
            checkAnswer(binding.tvOption2.text)
        }
        binding.tvOption3.setOnClickListener {
            checkAnswer(binding.tvOption3.text)
        }
        binding.tvOption4.setOnClickListener {
            checkAnswer(binding.tvOption4.text)
        }
        binding.tvOption5.setOnClickListener {
            checkAnswer(binding.tvOption5.text)
        }
        binding.tvOption6.setOnClickListener {
            checkAnswer(binding.tvOption6.text)
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