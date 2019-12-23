package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.DataUtils.generateNameList
import `in`.nerd_is.demo.recyclone.DataUtils.generatePersonList
import `in`.nerd_is.demo.recyclone.databinding.ActivityRecyclerViewBinding
import `in`.nerd_is.demo.recyclone.entity.Person
import `in`.nerd_is.recyclone.RecyclerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.action) {
            ACTION_MULTI_TYPE -> {
                initMultiType()
            }
            ACTION_PAGING -> {
                initPaging()
            }
        }
    }

    private fun initMultiType() {
        val adapter = RecyclerAdapter()
        adapter.addRule(Person::class.java, PersonRule)
        adapter.addRule(String::class.java, TextRule)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val personLit = generatePersonList()
        val nameList = generateNameList()
        val data = (personLit + nameList).shuffled()

        adapter.swapData(data)
    }

    private fun initPaging() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        private const val ACTION_MULTI_TYPE = "action_multi_type"
        private const val ACTION_PAGING = "action_paging"

        fun startMultiType(context: Context) {
            context.startActivity(Intent(context, RecyclerViewActivity::class.java).apply {
                action = ACTION_MULTI_TYPE
            })
        }

        fun startPaging(context: Context) {
            context.startActivity(Intent(context, RecyclerViewActivity::class.java).apply {
                action = ACTION_PAGING
            })
        }
    }
}
