package com.nursultan.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nursultan.composition.R
import com.nursultan.composition.databinding.FragmentGameResultBinding
import com.nursultan.composition.domain.entity.GameResult

class GameResultFragment : Fragment() {
    private lateinit var result: GameResult
    private var _binding: FragmentGameResultBinding? = null
    private val binding: FragmentGameResultBinding
        get() = _binding ?: throw RuntimeException("FragmentGameResultBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseGameResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivGameResult.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                getDrawable(result.winner)
            )
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_right_answers_percentage),
            result.gameSettings.minPercentOfRightAnswers
        )

        binding.tvRequiredScore.text = String.format(
            getString(R.string.required_right_answers),
            result.gameSettings.minCountRightAnswers
        )

        binding.tvRightPercentage.text = String.format(
            getString(R.string.right_answers_percentage),
                result.percentOfRightAnswers.toString()
        )
        binding.tvScore.text = String.format(
            getString(R.string.score_answers),
            result.countOfRightAnswers
        )
        binding.btnRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }

            })
    }

    private fun getDrawable(isWinner: Boolean) = if (isWinner) {
        R.drawable.happiness
    } else {
        R.drawable.sad
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseGameResult() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            result = it
        }
    }

    companion object {
        private const val KEY_GAME_RESULT = "game_result"
        fun newInstance(gameResult: GameResult): GameResultFragment {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}