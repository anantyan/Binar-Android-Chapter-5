package id.anantyan.exerciseproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.ui.activity.BaseFragmentActivity
import id.anantyan.exerciseproject.databinding.FragmentDoctorBinding

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseFragmentActivity).supportActionBar?.title = getString(R.string.txt_title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}