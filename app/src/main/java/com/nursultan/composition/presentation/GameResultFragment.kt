package com.nursultan.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nursultan.composition.R
import com.nursultan.composition.databinding.FragmentGameResultBinding
import com.nursultan.composition.domain.entity.GameResult
import java.lang.RuntimeException

class GameResultFragment : Fragment() {
    private lateinit var gameResult: GameResult
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
        binding.btnRetry.setOnClickListener {
            retryGame()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }

        })
    }
    private fun retryGame()
    {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME,FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun parseGameResult()
    {
        gameResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }

    companion object
    {
        private const val KEY_GAME_RESULT ="game_result"
        fun newInstance(gameResult: GameResult): GameResultFragment
        {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}