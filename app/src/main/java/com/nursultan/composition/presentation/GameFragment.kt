package com.nursultan.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nursultan.composition.R
import com.nursultan.composition.databinding.FragmentGameBinding
import com.nursultan.composition.domain.entity.GameResult
import com.nursultan.composition.domain.entity.GameSettings
import com.nursultan.composition.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var level: Level
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
        viewModel.setGameSetting(level)
        viewModel.gameSettings.observe(viewLifecycleOwner, {
            binding.tvTimer.text = it.gameTimeInSeconds.toString()
            binding.tvProgress.text = String.format(
                getString(R.string.game_right_answers_count),
                ZERO_RIGHT_ANSWERS,
                it.minCountRightAnswers
            )
            binding.progressBar.secondaryProgress = it.minPercentOfRightAnswers
            viewModel.generateGameQuestion(it.maxSumValue)
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

        binding.tvOption1.setOnClickListener {

        }
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