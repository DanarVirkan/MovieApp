package com.example.core.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.data.remote.ApiService.Companion.IMG_URL
import com.example.core.domain.Item
import com.example.core.utils.Constant.ITEM
import com.example.core.utils.Constant.MOVIE
import com.example.core.utils.Constant.TV_SHOW
import com.example.core.utils.Constant.TYPE
import java.util.*

class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.ListViewHolder>() {
    private var listData = ArrayList<Item>()

    fun setData(newListData: List<Item>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.poster_image)
        val item: ConstraintLayout = itemView.findViewById(R.id.item)
        fun bind(trending: Item) {
            val itemType = if (trending.name.isNullOrEmpty()) MOVIE else TV_SHOW
            Glide.with(itemView.context).load(IMG_URL + trending.poster)
                .error(R.drawable.error_placeholder)
                .placeholder(R.drawable.loading_placeholder).into(img)
            item.setOnClickListener {
                val uri = Uri.parse("moviedb://detail")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.putExtra(TYPE, itemType)
                intent.putExtra(ITEM, trending)
                ContextCompat.startActivity(it.context, intent, null)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}