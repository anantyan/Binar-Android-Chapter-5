package id.anantyan.exerciseproject.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.databinding.FragmentDialogExampleBinding

class DialogExampleFragment : DialogFragment() {

    private var _binding: FragmentDialogExampleBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_DIALOG_EXAMPLE: String = "ARG_DIALOG_EXAMPLE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDialogExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        dialog?.window?.let {
            it.setBackgroundDrawableResource(R.drawable.bg_dialog_example)
            it.setGravity(Gravity.CENTER)
        }
        binding.txtView.text = "Selamat datang di Dialog Fragment!"
        binding.btnNegatif.setOnClickListener {
            dismiss()
        }
        binding.btnPositif.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}