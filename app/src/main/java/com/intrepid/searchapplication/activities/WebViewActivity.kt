package com.intrepid.searchapplication.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.intrepid.searchapplication.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView1?.settings?.javaScriptEnabled = true
        webView1?.requestFocus(View.FOCUS_DOWN)
        webView1?.settings?.builtInZoomControls = true
        webView1?.settings?.builtInZoomControls = false

        webView1?.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> if (!v.hasFocus()) {
                    v.requestFocus()
                }
            }
            false
        })

        val webSettings: WebSettings? = webView1?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.setSupportZoom(false)
        webSettings?.useWideViewPort = true
        webSettings?.loadWithOverviewMode = true
        webSettings?.domStorageEnabled = true
        webSettings?.builtInZoomControls = true
        webSettings?.cacheMode = WebSettings.LOAD_NO_CACHE
        webView1?.clearHistory()
        webView1?.clearCache(true)
        webSettings?.javaScriptEnabled = true
        webSettings?.useWideViewPort = false
        webSettings?.loadWithOverviewMode = false
        webSettings?.allowFileAccess = true
        webSettings?.setEnableSmoothTransition(true)

        val url =
            intent.getStringExtra(WIKI_DATA)

        webView1?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                webView1_pb?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView1_pb?.visibility = View.GONE
            }
        }
        webView1?.loadUrl(url)
    }

    companion object {
        val WIKI_DATA = "WIKI_DATA"
    }
}
