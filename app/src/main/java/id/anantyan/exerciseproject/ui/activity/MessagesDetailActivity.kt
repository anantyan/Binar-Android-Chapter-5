package id.anantyan.exerciseproject.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.anantyan.exerciseproject.databinding.ActivityMessagesDetailBinding
import id.anantyan.exerciseproject.model.Messages
import id.anantyan.utils.Constant.PASSING_TO_MESSAGES_ACTIVITY

class MessagesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.title = "Set Data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        onBinding()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun onBinding() {
        var messages = intent.extras?.getParcelable<Messages>(PASSING_TO_MESSAGES_ACTIVITY)
        if (intent.hasExtra(PASSING_TO_MESSAGES_ACTIVITY)) {
            binding.txtInputSendTo.setText(messages?.name)
            binding.txtInputMessages.setText(messages?.message)
            binding.btnSubmit.setOnClickListener {
                messages = Messages(
                    messages?.id!!,
                    messages?.image,
                    binding.txtInputSendTo.text.toString(),
                    binding.txtInputMessages.text.toString()
                )
                val intent = Intent()
                intent.putExtra(PASSING_TO_MESSAGES_ACTIVITY, messages)
                setResult(RESULT_OK, intent)
                finish()
            }
        } else {
            binding.btnSubmit.setOnClickListener {
                messages = Messages(
                    System.currentTimeMillis().toString(),
                    "https://asset.kompas.com/crops/y-WWkS0Pf0xss9ilSe531dt3d-Y=/0x25:1280x878/750x500/data/photo/2022/01/07/61d80c31006ac.jpg",
                    binding.txtInputSendTo.text.toString(),
                    binding.txtInputMessages.text.toString()
                )
                val intent = Intent()
                intent.putExtra(PASSING_TO_MESSAGES_ACTIVITY, messages)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}