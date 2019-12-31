package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {
      btnMultiType.setOnClickListener {
        RecyclerViewActivity.startMultiType(this@MainActivity)
      }
      btnPaging.setOnClickListener {
        RecyclerViewActivity.startPaging(this@MainActivity)
      }
      btnLoadMore.setOnClickListener {
        startActivity(Intent(this@MainActivity, LoadMoreActivity::class.java))
      }
    }
  }
}
