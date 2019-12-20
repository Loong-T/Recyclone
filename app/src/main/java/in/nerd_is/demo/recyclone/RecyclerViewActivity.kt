package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.recyclone.RecyclerAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlin.random.Random

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
        adapter.addRule(String::class.java, TextRule)
        recyclerView.adapter = adapter
        adapter.swapData(generateRandomStringList())
    }

    private fun generateRandomStringList(): List<String> {
        return (0..20).map { Random.nextInt(100).toString() }
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
