package com.intrepid.searchapplication.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intrepid.searchapplication.R
import com.intrepid.searchapplication.activities.WebViewActivity
import com.intrepid.searchapplication.pojo.Page
import com.intrepid.searchapplication.pojo.Terms
import com.intrepid.searchapplication.pojo.Thumbnail
import com.squareup.picasso.Picasso
import java.util.*

class WikiListRecyclerViewAdapter(
    private val context: Context,
    private var pageList: ArrayList<Page>
) :
    RecyclerView.Adapter<WikiListRecyclerViewAdapter.WikiItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiItemViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.wiki_list_item, parent, false)
        return WikiItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: WikiItemViewHolder, position: Int) {
        val value = pageList[position]

        val terms: Terms? = value.terms
        if (!terms?.description.isNullOrEmpty())
            holder.newsItemPublisher?.text = terms?.description?.get(0)
        holder.newsItemTitle?.text = Html.fromHtml(value.title)

        val thumbnail: Thumbnail? = value.thumbnail
        if (thumbnail?.source != null) {
            Picasso.get().load(thumbnail.source)
                .placeholder(R.drawable.place_holder_ic)
                .error(R.drawable.place_holder_ic)
                .into(holder.newsItemImageView)
        } else {
            Picasso.get().load(R.drawable.place_holder_ic)
                .into(holder.newsItemImageView)
        }

        holder.newsItemView?.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.WIKI_DATA, "https://en.wikipedia.org/wiki/Sachin_Tendulkar")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun updateNewsList(list: ArrayList<Page>) {
        if (pageList.isNotEmpty()) {
            pageList.clear()
            pageList.addAll(list)
        } else {
            pageList = list
        }
        notifyDataSetChanged()
    }

    class WikiItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var newsItemImageView: ImageView? = itemView.findViewById(R.id.news_item_ic)
        var newsItemTitle: TextView? = itemView.findViewById(R.id.news_item_title)
        var newsItemPublisher: TextView? = itemView.findViewById(R.id.news_item_publisher)
        var newsItemView: RelativeLayout? = itemView.findViewById(R.id.news_list_item_view)

    }

}