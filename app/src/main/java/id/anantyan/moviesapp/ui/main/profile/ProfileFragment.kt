package id.anantyan.moviesapp.ui.main.profile

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.database.RoomDB
import id.anantyan.moviesapp.databinding.FragmentProfileBinding
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.repository.UsersRepository
import id.anantyan.moviesapp.ui.dialog.ProfileDialog
import id.anantyan.moviesapp.ui.dialog.ProfileDialogHelper
import id.anantyan.moviesapp.ui.dialog.dialog
import id.anantyan.utils.Resource
import id.anantyan.utils.dividerVertical
import id.anantyan.utils.sharedPreferences.preference
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels {
        val usersDao = RoomDB.database(requireContext()).usersDao()
        ProfileViewModelFactory(UsersRepository(usersDao))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        onBindObserver()
    }

    private fun onBindView() {
        binding.rvProfile.setHasFixedSize(true)
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.addItemDecoration(dividerVertical(requireContext(), 32, 32))
        binding.rvProfile.isNestedScrollingEnabled = false
        binding.btnSetProfile.setOnClickListener {
            viewModel.getAccount(requireContext().preference().getUserId())
        }
        binding.fabSetPassword.setOnClickListener {
            requireContext().dialog().dialogSetPassword(requireContext().preference().getUserId()) { item, dialog ->
                viewModel.setPassword(item)
                dialog.dismiss()
            }
        }
    }

    private fun onBindObserver() {
        viewModel.getAccount.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    requireContext().dialog().dialogSetProfile(it.data!!) { item, dialog ->
                        viewModel.setProfile(item)
                        dialog.dismiss()
                    }
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.setPassword.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    onToast("${it.data}")
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.setProfile.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapter.differ(it.data!!)
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.showAccount.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapter.differ(it.data!!)
                    binding.rvProfile.adapter = adapter.init()
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.showAccount(requireContext().preference().getUserId())
    }

    private fun onToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}