package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.DataUtils.generateNameList
import `in`.nerd_is.demo.recyclone.DataUtils.generatePersonList
import `in`.nerd_is.demo.recyclone.entity.Person
import `in`.nerd_is.recyclone.RecyclerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        when (intent.action) {
            ACTION_MULTI_TYPE -> {
                initMultiType()
            }
        }
    }

    private fun initMultiType() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter()
        adapter.addRule(Person::class.java, PersonRule)
        adapter.addRule(String::class.java, TextRule)
        recyclerView.adapter = adapter

        val personLit = generatePersonList()
        val nameList = generateNameList()
        val data = (personLit + nameList).shuffled()

        adapter.swapData(data)
    }

    companion object {
        private const val ACTION_MULTI_TYPE = "action_multi_type"

        fun startMultiType(context: Context) {
            context.startActivity(Intent(context, RecyclerViewActivity::class.java).apply {
                action = ACTION_MULTI_TYPE
            })
        }
    }
}
