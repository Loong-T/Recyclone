package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.DataUtils.generateNameList
import `in`.nerd_is.demo.recyclone.DataUtils.generatePersonList
import `in`.nerd_is.demo.recyclone.databinding.ActivityRecyclerViewBinding
import `in`.nerd_is.demo.recyclone.entity.Person
import `in`.nerd_is.demo.recyclone.paging.NumberDataSource
import `in`.nerd_is.recyclone.RecyclerAdapter
import `in`.nerd_is.recyclone.Rule
import `in`.nerd_is.recyclone.RuleManager
import `in`.nerd_is.recyclone.paging.PagingAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.concurrent.Executors

class RecyclerViewActivity : AppCompatActivity() {

  private lateinit var binding: ActivityRecyclerViewBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    when (intent.action) {
      ACTION_MULTI_TYPE -> initMultiType()
      ACTION_PAGING -> initPaging()
    }
  }

  private fun initMultiType() {
    val adapter = RecyclerAdapter()
    adapter.addRule(Person::class.java, PersonRule)
    adapter.addRule(String::class.java, StringRule)
    adapter.setNullRule(object : Rule<RuleManager.NullType, TitleHolder>() {
      override fun onCreateHolder(inflater: LayoutInflater, parent: ViewGroup): TitleHolder {
        return TitleHolder(inflater.inflate(R.layout.item_title, parent, false))
      }

      override fun onBindHolder(holder: TitleHolder, item: RuleManager.NullType) {
        holder.bindTo("Data MISSING!")
      }
    })
    with(binding.recyclerView) {
      this.adapter = adapter
      layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
    }

    val personLit = generatePersonList()
    val nameList = generateNameList()
    val nullList = (0..5).map { null }
    val data = (personLit + nameList + nullList).shuffled()

    adapter.submitList(data)
  }

  private fun initPaging() {
    val adapter = PagingAdapter(object : DiffUtil.ItemCallback<Any?>() {
      override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem === newItem
      }

      @SuppressLint("DiffUtilEquals")
      override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.toString() == newItem.toString()
      }
    })
    adapter.addRule(String::class.java, StringRule)
    adapter.setNullRule(object : Rule<RuleManager.NullType, TitleHolder>() {
      override fun onCreateHolder(inflater: LayoutInflater, parent: ViewGroup): TitleHolder {
        return TitleHolder(inflater.inflate(R.layout.item_title, parent, false))
      }

      override fun onBindHolder(holder: TitleHolder, item: RuleManager.NullType) {
        holder.bindTo("Data MISSING")
      }
    })

    val config = PagedList.Config.Builder()
      .setInitialLoadSizeHint(20)
      .setPageSize(20)
      .setPrefetchDistance(10)
      .build()
    val pagedList = PagedList.Builder<Int, String>(NumberDataSource(), config)
      .setNotifyExecutor {
        runOnUiThread(it)
      }.setFetchExecutor(
        Executors.newSingleThreadExecutor()
      )
      .build()
    binding.recyclerView.adapter = adapter
    adapter.submitTypedList(pagedList)
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
