package `in`.nerd_is.demo.recyclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BasicAdapter(generateRandomStringList())
    }

    private fun generateRandomStringList(): List<String> {
        return (0..20).map { Random.nextInt(100).toString() }
    }
}

class TextHolder(view: View) : RecyclerView.ViewHolder(view)

class BasicAdapter(private val data: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TextHolder(inflater.inflate(R.layout.item_text, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TextHolder) {
            (holder.itemView as TextView).text = data[position]
        }
    }
}
