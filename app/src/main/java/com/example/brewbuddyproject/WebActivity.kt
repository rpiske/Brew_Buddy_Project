//cs414s23, (Accessed: May 5, 2023)WebViewExample, (Version: April 29, 2023)https://github.com/cs414s23/WebViewExample

package com.example.brewbuddyproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast

class WebActivity : AppCompatActivity() {

    private val TAG = "WebActivity"
    //Stack of URLs for back functionality
    private val urlStack = ArrayDeque<String>()
    lateinit var webView: WebView

    //cs414s23, (Accessed: May 5, 2023)WebViewExample, (Version: April 29, 2023)https://github.com/cs414s23/WebViewExample
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        webView = findViewById(R.id.web_web_view)

        //load the url passed from the details page
        val url = intent.getStringExtra("brewWebsite")
        searchFunctionality(url)

        //Code to make it so that you can click links within the WebView
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
    }

    //cs414s23, (Accessed: May 5, 2023)WebViewExample, (Version: April 29, 2023)https://github.com/cs414s23/WebViewExample
    //Functionality for webview search
    fun searchFunctionality(url: String?){
        //checks if url is empty or null
        if (url != null || url == "") {
            webView.loadUrl(url)
            urlStack.addLast(url)
            findViewById<EditText>(R.id.web_url_edit_text).setText(url)
        }
        else{
            //Toast to ask the user to enter a url
            Toast.makeText(this, "Please Enter A Url", Toast.LENGTH_SHORT).show()
        }
    }

    //cs414s23, (Accessed: May 5, 2023)WebViewExample, (Version: April 29, 2023)https://github.com/cs414s23/WebViewExample
    //Searches based on url in edit text whe search button is clicked
    fun search(view: View){
      searchFunctionality(findViewById<EditText>(R.id.web_url_edit_text).editableText.toString())
    }

    //functionality for back button
    fun back(view: View){
        if(urlStack.isEmpty()){
            //kill the activity when stack is empty
            finish()
        }
        else{
            //Remove the url that is being loaded from the stack
            val url = urlStack.removeLast()
            findViewById<EditText>(R.id.web_url_edit_text).setText(url)
            webView.loadUrl(url.toString())
        }
    }


}