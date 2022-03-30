package com.example.core.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.data.remote.ApiService
import com.example.core.domain.Item
import com.example.core.utils.Constant.ITEM
import com.example.core.utils.Constant.MOVIE
import com.example.core.utils.Constant.TV_SHOW
import com.example.core.utils.Constant.TYPE

class BookmarkedAdapter(private val bookmarked: List<Item>) :
    RecyclerView.Adapter<BookmarkedAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.poster_image)
        val name: TextView = itemView.findViewById(R.id.name)
        val rating: TextView = itemView.findViewById(R.id.rating_text)
        val type: TextView = itemView.findViewById(R.id.type)
        val item: ConstraintLayout = itemView.findViewById(R.id.item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val itemType =
            if (bookmarked[position].name.isNullOrEmpty()) MOVIE else TV_SHOW
        Glide.with(holder.itemView.context).load(ApiService.IMG_URL + bookmarked[position].poster)
            .error(R.drawable.error_placeholder)
            .placeholder(R.drawable.loading_placeholder).into(holder.img)
        holder.name.text = bookmarked[position].title ?: bookmarked[position].name
        holder.type.text = itemType
        holder.type.setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (itemType == MOVIE) R.drawable.movie else R.drawable.tv_shows,
            0,
            0,
            0
        )
        holder.rating.text = bookmarked[position].vote
        holder.item.setOnClickListener {
            val uri = Uri.parse("moviedb://detail")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra(TYPE, itemType)
            intent.putExtra(ITEM, bookmarked[position])
            startActivity(it.context, intent, null)
        }
    }

    override fun getItemCount(): Int = bookmarked.size
}