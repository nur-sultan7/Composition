package com.nursultan.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nursultan.composition.R
import com.nursultan.composition.databinding.FragmentChooseLevelBinding
import com.nursultan.composition.domain.entity.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLevelClickListener(binding.tvTest, Level.TEST)
        setLevelClickListener(binding.tvEasy, Level.EASY)
        setLevelClickListener(binding.tvNormal, Level.NORMAL)
        setLevelClickListener(binding.tvHard, Level.HARD)
    }

    private fun setLevelClickListener(tv: TextView, level: Level) {
        tv.setOnClickListener {
            findNavController().navigate(
                ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
            )
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.main_container, GameFragment.newInstance(level))
//                .addToBackStack(GameFragment.NAME)
//                .commit()
//
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}