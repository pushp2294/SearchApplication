package com.intrepid.searchapplication.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.intrepid.searchapplication.R
import com.intrepid.searchapplication.WikiSearchApplication
import com.intrepid.searchapplication.adapter.WikiListRecyclerViewAdapter
import com.intrepid.searchapplication.mvp.presenters.WikiSearchPresenter
import com.intrepid.searchapplication.mvp.views.IWikiSearchView
import com.intrepid.searchapplication.pojo.Page
import kotlinx.android.synthetic.main.activity_wiki_search.*
import java.util.*
import javax.inject.Inject

class WikiSearchActivity : AppCompatActivity(), IWikiSearchView {

    @Inject
    lateinit var wikiSearchPresenter: WikiSearchPresenter

    var wikiListRecyclerViewAdapter: WikiListRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki_search)
        initDependecies()
        initRecycler()
        wikiSearchPresenter.setiWikiSearchView(this)

        news_search_et.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (isNetworkConeected()) {
                    wikiSearchPresenter.getQueriedWikiSearchResult(editable.toString())
                } else {
                    wikiSearchPresenter.getQueriedWikiSearchFromLocal(editable.toString())
                }
            }
        })
    }

    private fun initDependecies() {
        (application as WikiSearchApplication).applicationComponent.inject(this)
    }

    private fun initRecycler() {
        wikiListRecyclerViewAdapter = WikiListRecyclerViewAdapter(this, ArrayList<Page>())
        news_search_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        news_search_rv.adapter = wikiListRecyclerViewAdapter
    }

    override fun showLoading() {
        news_search_pb?.visibility = View.VISIBLE
        news_search_rv.visibility = View.GONE
        news_search_no_result?.visibility = View.GONE
    }

    override fun hideLoading() {
        news_search_pb?.visibility = View.GONE
    }

    override fun showWikiListing(pageList: List<Page?>?) {
        news_search_rv.visibility = View.VISIBLE
        news_search_no_result?.visibility = View.GONE
        wikiListRecyclerViewAdapter?.updateNewsList(pageList as ArrayList<Page>)
    }

    override fun showErrorView() {
        news_search_pb?.visibility = View.GONE
        news_search_rv.visibility = View.GONE
        news_search_no_result?.visibility = View.VISIBLE
    }

    private fun isNetworkConeected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
