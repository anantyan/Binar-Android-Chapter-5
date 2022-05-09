package id.anantyan.exerciseproject.ui.fragment.messages

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.databinding.FragmentMessagesBinding
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.exerciseproject.repository.MessagesRepository
import id.anantyan.exerciseproject.ui.activity.BaseFragmentActivity
import id.anantyan.exerciseproject.ui.adapter.messages.MessagesAdapter
import id.anantyan.exerciseproject.ui.adapter.messages.MessagesHelper
import id.anantyan.exerciseproject.ui.SharedViewModel
import id.anantyan.exerciseproject.ui.activity.MessagesDetailActivity
import id.anantyan.utils.Constant.PASSING_TO_MESSAGES_ACTIVITY
import id.anantyan.utils.Resource
import id.anantyan.utils.dividerVertical

class MessagesFragment : Fragment() {

    private var position: Int = -1
    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!
    private val list: MutableList<Messages> = mutableListOf()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: MessagesViewModel by viewModels {
        val messagesDao = RoomDB.database(requireContext()).messagesDao()
        MessagesViewModelFactory(MessagesRepository(messagesDao))
    }
    private val adapter: MessagesHelper by lazy { MessagesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBinding()
        onAdapter()
        onObserver()
        onSelectData()
    }

    override fun onResume() {
        super.onResume()
        (activity as BaseFragmentActivity).supportActionBar?.title = "Messages"
    }

    private fun onAdapter() {
        adapter.onClick { i, messages ->
            position = i
            val intent = Intent((activity as BaseFragmentActivity), MessagesDetailActivity::class.java)
            intent.putExtra(PASSING_TO_MESSAGES_ACTIVITY, messages)
            (activity as BaseFragmentActivity).onResultActivity.launch(intent)
        }
        adapter.onLongClick { _, messages ->
            onDeleteData(messages)
            Toast.makeText(
                (activity as BaseFragmentActivity),
                messages.name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onBinding() {
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.layoutManager = LinearLayoutManager((activity as BaseFragmentActivity))
        binding.rvItems.itemAnimator = DefaultItemAnimator()
        binding.rvItems.addItemDecoration(dividerVertical((activity as BaseFragmentActivity), 0, 0))
    }

    /**
     * Observer LiveData
     * */
    private fun onSelectData() {
        /*viewModel.selectLocal().observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            adapter.differ(list)
        }*/
        viewModel.selectApi()
    }

    private fun onInsertData(item: Messages) {
        /*viewModel.insertLocal(item)*/
        viewModel.insertApi(item)
    }

    private fun onUpdateData(item: Messages) {
        /*viewModel.updateLocal(item)*/
        viewModel.updateApi(item)
    }

    private fun onDeleteData(item: Messages) {
        /*viewModel.deleteLocal(item)*/
        viewModel.deleteApi(item)
    }

    private fun onObserver() {
        sharedViewModel.messages.observe(viewLifecycleOwner) { item ->
            item?.let {
                if (position == -1) {
                    onInsertData(it)
                } else {
                    onUpdateData(it)
                }
            }
        }
        viewModel.selectResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    list.clear()
                    list.addAll(it.data!!)
                    adapter.differ(list)
                    binding.rvItems.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.rvItems.adapter = adapter.init()
                }
                is Resource.Loading -> {
                    binding.rvItems.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    binding.rvItems.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        viewModel.insertResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    list.add(it.data!!)
                    adapter.differ(list)
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Tunggu...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.updateResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    list[position] = it.data!!
                    adapter.differ(list)
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    position = -1
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Tunggu...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.deleteResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    list.remove(it.data!!)
                    adapter.differ(list)
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    position = -1
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Tunggu...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        /*viewModel.insertLocal.observe(viewLifecycleOwner) {
            Toast.makeText((activity as BaseFragmentActivity), "Data tersimpan!", Toast.LENGTH_SHORT).show()
        }
        viewModel.updateLocal.observe(viewLifecycleOwner) {
            Toast.makeText((activity as BaseFragmentActivity), "Data terupdate!", Toast.LENGTH_SHORT).show()
        }
        viewModel.deleteLocal.observe(viewLifecycleOwner) {
            Toast.makeText((activity as BaseFragmentActivity), "Data terhapus!", Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}