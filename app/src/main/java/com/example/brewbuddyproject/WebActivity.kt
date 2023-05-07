//cs414s23, (Accessed: May 5, 2023)WebViewExample, (Version: April 29, 2023)https://github.com/cs414s23/WebViewExample
//cs414s23, (Accessed: May 5, 2023)Android Jetpack Library, https://learn-us-east-1-prod-fleet01-xythos.content.blackboardcdn.com/blackboard.learn.xythos.prod/57853691332dd/14446061?X-Blackboard-S3-Bucket=blackboard.learn.xythos.prod&X-Blackboard-Expiration=1683428400000&X-Blackboard-Signature=7muS7XaDRN7AHbHjBwxzP14J6c5kr5jUzZ1QA9xgcKs%3D&X-Blackboard-Client-Id=302355&X-Blackboard-S3-Region=us-east-1&response-cache-control=private%2C%20max-age%3D21600&response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27Lecture18.pdf&response-content-type=application%2Fpdf&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEB8aCXVzLWVhc3QtMSJHMEUCIA9JbT46SSi0buBx6XX%2FEdlCi5bC3PWIMi%2Bg5Io3GvQDAiEA5CmiKdgdNaVz2O%2FpYv4p0CG%2B1752IuA331RuYqklIHgqswUIOBAAGgw1NTY5MDM4NjEzNjEiDDu3xHcz9hDDoe9DsiqQBU1lr5M1E7y5lqJla9RmTGMOl%2Fau0OF7Kj8whnier4b9KIntxdWuhI3%2F2vuF0xPKeiV7sr4NRD7xQfWk8qlepZQqukNMH%2Ft1i5SNmV6KEDRsL5%2F%2Fhg9zbeFgZJyGioI2Rppb7kQeYxwmd%2F3%2B1JcaOyznPTH7UuYiMtCXi%2F5yM1wvd2ZKU4lKl6vF90RRtuQxd8VfZt3s0cEaKvOXlBpkZcHBcSCiOLtmbOmRCspmnUCHNCrvUeC%2FJcDpZ5xFL27CrO04lrO7yPNuEZGZxu%2FE%2BpRn1hGC23jusp2OnY1xT9dNmOKJ6bG2EMg2QHDWguiTL1DvuKXrUnG5AqGVSyomeTw%2FLiLy3cTMtM4nVW7bg4fOGrYc89C2G9iY9WnHfk2hyN2TWiAH0Cd1%2BnCflPsfyeNmH%2BQRNOWO6XdZub%2FBmSrN7ustO3iyTj%2Fmn7C5K6rq50KL2NpVM2EWC2QjM7G6yWDgGu%2BEoz%2B%2B5LutoYfeV3aJTpeVrr4AbIj8DvUsEiBWubGXgDYug0P0biUdlLuPzcK1fLABKhGYs7zutuVReHG0qIBAsAlvYpuqo%2Fcx%2Fh7JI3EnxlCFcX4EJCQzVwYf2LwGxSmwTP8xzxtedkWY2BEyd1c5wPyH9%2Bue9yKFW1OC9oJAG9UXMm2mpFQH7WkqjB6kwo3QqzY7BeeDkMlR5H3IB9pIWDvfZ9TFUbkYemgn6toKu1JT7SA2Cht3Rc1ceWKb6sXZ6jIWK3yXPAOZnout03NbnCnm2ZYBdHIx5A9js4I1Js%2B16Z7HvpQNdap4b4VtTWIfrlypFP8mhnOQ%2FDrte%2Bwg2VoQW5pJPlFXYJ4bHYk6iXd03YC2tI6Rb3Wa8zRONVYY4S%2FFwJJ8%2Bpfa4lhGMICw26IGOrEBvXzeiVt1AXlU7JnXO8cByi9gDtoqHqBiL0uQyf8sm1ety5Id4V47uk6%2F2VCV4D%2FNc3Z6oUOpfs736B1s8sTdmyJJJ76r6p%2BGBDsgiEvfso1EnjGMKwu%2F%2F9m4j%2FCucngL6RlaMfprbdRjftxie5EuKSZCvyMHxPe%2BCEFJ3OnRGg6kZtzUMqYz%2FX8mK4FuxmsuFQPasvaL9HRrWkqVUSNUYo2Jl%2FxtwZpU3KQOoEfi8vkb&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230506T210000Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=ASIAYDKQORRYSMJG4C5N%2F20230506%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=7e8c4e8e61f20a547feac85696a361d03691a3ad21c9b9bd572111c25f2afd34

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
    //cs414s23, (Accessed: May 5, 2023)Android Jetpack Library, https://learn-us-east-1-prod-fleet01-xythos.content.blackboardcdn.com/blackboard.learn.xythos.prod/57853691332dd/14446061?X-Blackboard-S3-Bucket=blackboard.learn.xythos.prod&X-Blackboard-Expiration=1683428400000&X-Blackboard-Signature=7muS7XaDRN7AHbHjBwxzP14J6c5kr5jUzZ1QA9xgcKs%3D&X-Blackboard-Client-Id=302355&X-Blackboard-S3-Region=us-east-1&response-cache-control=private%2C%20max-age%3D21600&response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27Lecture18.pdf&response-content-type=application%2Fpdf&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEB8aCXVzLWVhc3QtMSJHMEUCIA9JbT46SSi0buBx6XX%2FEdlCi5bC3PWIMi%2Bg5Io3GvQDAiEA5CmiKdgdNaVz2O%2FpYv4p0CG%2B1752IuA331RuYqklIHgqswUIOBAAGgw1NTY5MDM4NjEzNjEiDDu3xHcz9hDDoe9DsiqQBU1lr5M1E7y5lqJla9RmTGMOl%2Fau0OF7Kj8whnier4b9KIntxdWuhI3%2F2vuF0xPKeiV7sr4NRD7xQfWk8qlepZQqukNMH%2Ft1i5SNmV6KEDRsL5%2F%2Fhg9zbeFgZJyGioI2Rppb7kQeYxwmd%2F3%2B1JcaOyznPTH7UuYiMtCXi%2F5yM1wvd2ZKU4lKl6vF90RRtuQxd8VfZt3s0cEaKvOXlBpkZcHBcSCiOLtmbOmRCspmnUCHNCrvUeC%2FJcDpZ5xFL27CrO04lrO7yPNuEZGZxu%2FE%2BpRn1hGC23jusp2OnY1xT9dNmOKJ6bG2EMg2QHDWguiTL1DvuKXrUnG5AqGVSyomeTw%2FLiLy3cTMtM4nVW7bg4fOGrYc89C2G9iY9WnHfk2hyN2TWiAH0Cd1%2BnCflPsfyeNmH%2BQRNOWO6XdZub%2FBmSrN7ustO3iyTj%2Fmn7C5K6rq50KL2NpVM2EWC2QjM7G6yWDgGu%2BEoz%2B%2B5LutoYfeV3aJTpeVrr4AbIj8DvUsEiBWubGXgDYug0P0biUdlLuPzcK1fLABKhGYs7zutuVReHG0qIBAsAlvYpuqo%2Fcx%2Fh7JI3EnxlCFcX4EJCQzVwYf2LwGxSmwTP8xzxtedkWY2BEyd1c5wPyH9%2Bue9yKFW1OC9oJAG9UXMm2mpFQH7WkqjB6kwo3QqzY7BeeDkMlR5H3IB9pIWDvfZ9TFUbkYemgn6toKu1JT7SA2Cht3Rc1ceWKb6sXZ6jIWK3yXPAOZnout03NbnCnm2ZYBdHIx5A9js4I1Js%2B16Z7HvpQNdap4b4VtTWIfrlypFP8mhnOQ%2FDrte%2Bwg2VoQW5pJPlFXYJ4bHYk6iXd03YC2tI6Rb3Wa8zRONVYY4S%2FFwJJ8%2Bpfa4lhGMICw26IGOrEBvXzeiVt1AXlU7JnXO8cByi9gDtoqHqBiL0uQyf8sm1ety5Id4V47uk6%2F2VCV4D%2FNc3Z6oUOpfs736B1s8sTdmyJJJ76r6p%2BGBDsgiEvfso1EnjGMKwu%2F%2F9m4j%2FCucngL6RlaMfprbdRjftxie5EuKSZCvyMHxPe%2BCEFJ3OnRGg6kZtzUMqYz%2FX8mK4FuxmsuFQPasvaL9HRrWkqVUSNUYo2Jl%2FxtwZpU3KQOoEfi8vkb&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230506T210000Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=ASIAYDKQORRYSMJG4C5N%2F20230506%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=7e8c4e8e61f20a547feac85696a361d03691a3ad21c9b9bd572111c25f2afd34
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
    //cs414s23, (Accessed: May 5, 2023)Android Jetpack Library, https://learn-us-east-1-prod-fleet01-xythos.content.blackboardcdn.com/blackboard.learn.xythos.prod/57853691332dd/14446061?X-Blackboard-S3-Bucket=blackboard.learn.xythos.prod&X-Blackboard-Expiration=1683428400000&X-Blackboard-Signature=7muS7XaDRN7AHbHjBwxzP14J6c5kr5jUzZ1QA9xgcKs%3D&X-Blackboard-Client-Id=302355&X-Blackboard-S3-Region=us-east-1&response-cache-control=private%2C%20max-age%3D21600&response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27Lecture18.pdf&response-content-type=application%2Fpdf&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEB8aCXVzLWVhc3QtMSJHMEUCIA9JbT46SSi0buBx6XX%2FEdlCi5bC3PWIMi%2Bg5Io3GvQDAiEA5CmiKdgdNaVz2O%2FpYv4p0CG%2B1752IuA331RuYqklIHgqswUIOBAAGgw1NTY5MDM4NjEzNjEiDDu3xHcz9hDDoe9DsiqQBU1lr5M1E7y5lqJla9RmTGMOl%2Fau0OF7Kj8whnier4b9KIntxdWuhI3%2F2vuF0xPKeiV7sr4NRD7xQfWk8qlepZQqukNMH%2Ft1i5SNmV6KEDRsL5%2F%2Fhg9zbeFgZJyGioI2Rppb7kQeYxwmd%2F3%2B1JcaOyznPTH7UuYiMtCXi%2F5yM1wvd2ZKU4lKl6vF90RRtuQxd8VfZt3s0cEaKvOXlBpkZcHBcSCiOLtmbOmRCspmnUCHNCrvUeC%2FJcDpZ5xFL27CrO04lrO7yPNuEZGZxu%2FE%2BpRn1hGC23jusp2OnY1xT9dNmOKJ6bG2EMg2QHDWguiTL1DvuKXrUnG5AqGVSyomeTw%2FLiLy3cTMtM4nVW7bg4fOGrYc89C2G9iY9WnHfk2hyN2TWiAH0Cd1%2BnCflPsfyeNmH%2BQRNOWO6XdZub%2FBmSrN7ustO3iyTj%2Fmn7C5K6rq50KL2NpVM2EWC2QjM7G6yWDgGu%2BEoz%2B%2B5LutoYfeV3aJTpeVrr4AbIj8DvUsEiBWubGXgDYug0P0biUdlLuPzcK1fLABKhGYs7zutuVReHG0qIBAsAlvYpuqo%2Fcx%2Fh7JI3EnxlCFcX4EJCQzVwYf2LwGxSmwTP8xzxtedkWY2BEyd1c5wPyH9%2Bue9yKFW1OC9oJAG9UXMm2mpFQH7WkqjB6kwo3QqzY7BeeDkMlR5H3IB9pIWDvfZ9TFUbkYemgn6toKu1JT7SA2Cht3Rc1ceWKb6sXZ6jIWK3yXPAOZnout03NbnCnm2ZYBdHIx5A9js4I1Js%2B16Z7HvpQNdap4b4VtTWIfrlypFP8mhnOQ%2FDrte%2Bwg2VoQW5pJPlFXYJ4bHYk6iXd03YC2tI6Rb3Wa8zRONVYY4S%2FFwJJ8%2Bpfa4lhGMICw26IGOrEBvXzeiVt1AXlU7JnXO8cByi9gDtoqHqBiL0uQyf8sm1ety5Id4V47uk6%2F2VCV4D%2FNc3Z6oUOpfs736B1s8sTdmyJJJ76r6p%2BGBDsgiEvfso1EnjGMKwu%2F%2F9m4j%2FCucngL6RlaMfprbdRjftxie5EuKSZCvyMHxPe%2BCEFJ3OnRGg6kZtzUMqYz%2FX8mK4FuxmsuFQPasvaL9HRrWkqVUSNUYo2Jl%2FxtwZpU3KQOoEfi8vkb&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230506T210000Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=ASIAYDKQORRYSMJG4C5N%2F20230506%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=7e8c4e8e61f20a547feac85696a361d03691a3ad21c9b9bd572111c25f2afd34
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
    //cs414s23, (Accessed: May 5, 2023)Android Jetpack Library, https://learn-us-east-1-prod-fleet01-xythos.content.blackboardcdn.com/blackboard.learn.xythos.prod/57853691332dd/14446061?X-Blackboard-S3-Bucket=blackboard.learn.xythos.prod&X-Blackboard-Expiration=1683428400000&X-Blackboard-Signature=7muS7XaDRN7AHbHjBwxzP14J6c5kr5jUzZ1QA9xgcKs%3D&X-Blackboard-Client-Id=302355&X-Blackboard-S3-Region=us-east-1&response-cache-control=private%2C%20max-age%3D21600&response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27Lecture18.pdf&response-content-type=application%2Fpdf&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEB8aCXVzLWVhc3QtMSJHMEUCIA9JbT46SSi0buBx6XX%2FEdlCi5bC3PWIMi%2Bg5Io3GvQDAiEA5CmiKdgdNaVz2O%2FpYv4p0CG%2B1752IuA331RuYqklIHgqswUIOBAAGgw1NTY5MDM4NjEzNjEiDDu3xHcz9hDDoe9DsiqQBU1lr5M1E7y5lqJla9RmTGMOl%2Fau0OF7Kj8whnier4b9KIntxdWuhI3%2F2vuF0xPKeiV7sr4NRD7xQfWk8qlepZQqukNMH%2Ft1i5SNmV6KEDRsL5%2F%2Fhg9zbeFgZJyGioI2Rppb7kQeYxwmd%2F3%2B1JcaOyznPTH7UuYiMtCXi%2F5yM1wvd2ZKU4lKl6vF90RRtuQxd8VfZt3s0cEaKvOXlBpkZcHBcSCiOLtmbOmRCspmnUCHNCrvUeC%2FJcDpZ5xFL27CrO04lrO7yPNuEZGZxu%2FE%2BpRn1hGC23jusp2OnY1xT9dNmOKJ6bG2EMg2QHDWguiTL1DvuKXrUnG5AqGVSyomeTw%2FLiLy3cTMtM4nVW7bg4fOGrYc89C2G9iY9WnHfk2hyN2TWiAH0Cd1%2BnCflPsfyeNmH%2BQRNOWO6XdZub%2FBmSrN7ustO3iyTj%2Fmn7C5K6rq50KL2NpVM2EWC2QjM7G6yWDgGu%2BEoz%2B%2B5LutoYfeV3aJTpeVrr4AbIj8DvUsEiBWubGXgDYug0P0biUdlLuPzcK1fLABKhGYs7zutuVReHG0qIBAsAlvYpuqo%2Fcx%2Fh7JI3EnxlCFcX4EJCQzVwYf2LwGxSmwTP8xzxtedkWY2BEyd1c5wPyH9%2Bue9yKFW1OC9oJAG9UXMm2mpFQH7WkqjB6kwo3QqzY7BeeDkMlR5H3IB9pIWDvfZ9TFUbkYemgn6toKu1JT7SA2Cht3Rc1ceWKb6sXZ6jIWK3yXPAOZnout03NbnCnm2ZYBdHIx5A9js4I1Js%2B16Z7HvpQNdap4b4VtTWIfrlypFP8mhnOQ%2FDrte%2Bwg2VoQW5pJPlFXYJ4bHYk6iXd03YC2tI6Rb3Wa8zRONVYY4S%2FFwJJ8%2Bpfa4lhGMICw26IGOrEBvXzeiVt1AXlU7JnXO8cByi9gDtoqHqBiL0uQyf8sm1ety5Id4V47uk6%2F2VCV4D%2FNc3Z6oUOpfs736B1s8sTdmyJJJ76r6p%2BGBDsgiEvfso1EnjGMKwu%2F%2F9m4j%2FCucngL6RlaMfprbdRjftxie5EuKSZCvyMHxPe%2BCEFJ3OnRGg6kZtzUMqYz%2FX8mK4FuxmsuFQPasvaL9HRrWkqVUSNUYo2Jl%2FxtwZpU3KQOoEfi8vkb&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230506T210000Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=ASIAYDKQORRYSMJG4C5N%2F20230506%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=7e8c4e8e61f20a547feac85696a361d03691a3ad21c9b9bd572111c25f2afd34
    //Searches based on url in edit text whe search button is clicked
    fun search(view: View){
      searchFunctionality(findViewById<EditText>(R.id.web_url_edit_text).editableText.toString())
    }

    //cs414s23, (Accessed: May 5, 2023)WebViewExample, (Version: April 29, 2023)https://github.com/cs414s23/WebViewExample
    //cs414s23, (Accessed: May 5, 2023)Android Jetpack Library, https://learn-us-east-1-prod-fleet01-xythos.content.blackboardcdn.com/blackboard.learn.xythos.prod/57853691332dd/14446061?X-Blackboard-S3-Bucket=blackboard.learn.xythos.prod&X-Blackboard-Expiration=1683428400000&X-Blackboard-Signature=7muS7XaDRN7AHbHjBwxzP14J6c5kr5jUzZ1QA9xgcKs%3D&X-Blackboard-Client-Id=302355&X-Blackboard-S3-Region=us-east-1&response-cache-control=private%2C%20max-age%3D21600&response-content-disposition=inline%3B%20filename%2A%3DUTF-8%27%27Lecture18.pdf&response-content-type=application%2Fpdf&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEB8aCXVzLWVhc3QtMSJHMEUCIA9JbT46SSi0buBx6XX%2FEdlCi5bC3PWIMi%2Bg5Io3GvQDAiEA5CmiKdgdNaVz2O%2FpYv4p0CG%2B1752IuA331RuYqklIHgqswUIOBAAGgw1NTY5MDM4NjEzNjEiDDu3xHcz9hDDoe9DsiqQBU1lr5M1E7y5lqJla9RmTGMOl%2Fau0OF7Kj8whnier4b9KIntxdWuhI3%2F2vuF0xPKeiV7sr4NRD7xQfWk8qlepZQqukNMH%2Ft1i5SNmV6KEDRsL5%2F%2Fhg9zbeFgZJyGioI2Rppb7kQeYxwmd%2F3%2B1JcaOyznPTH7UuYiMtCXi%2F5yM1wvd2ZKU4lKl6vF90RRtuQxd8VfZt3s0cEaKvOXlBpkZcHBcSCiOLtmbOmRCspmnUCHNCrvUeC%2FJcDpZ5xFL27CrO04lrO7yPNuEZGZxu%2FE%2BpRn1hGC23jusp2OnY1xT9dNmOKJ6bG2EMg2QHDWguiTL1DvuKXrUnG5AqGVSyomeTw%2FLiLy3cTMtM4nVW7bg4fOGrYc89C2G9iY9WnHfk2hyN2TWiAH0Cd1%2BnCflPsfyeNmH%2BQRNOWO6XdZub%2FBmSrN7ustO3iyTj%2Fmn7C5K6rq50KL2NpVM2EWC2QjM7G6yWDgGu%2BEoz%2B%2B5LutoYfeV3aJTpeVrr4AbIj8DvUsEiBWubGXgDYug0P0biUdlLuPzcK1fLABKhGYs7zutuVReHG0qIBAsAlvYpuqo%2Fcx%2Fh7JI3EnxlCFcX4EJCQzVwYf2LwGxSmwTP8xzxtedkWY2BEyd1c5wPyH9%2Bue9yKFW1OC9oJAG9UXMm2mpFQH7WkqjB6kwo3QqzY7BeeDkMlR5H3IB9pIWDvfZ9TFUbkYemgn6toKu1JT7SA2Cht3Rc1ceWKb6sXZ6jIWK3yXPAOZnout03NbnCnm2ZYBdHIx5A9js4I1Js%2B16Z7HvpQNdap4b4VtTWIfrlypFP8mhnOQ%2FDrte%2Bwg2VoQW5pJPlFXYJ4bHYk6iXd03YC2tI6Rb3Wa8zRONVYY4S%2FFwJJ8%2Bpfa4lhGMICw26IGOrEBvXzeiVt1AXlU7JnXO8cByi9gDtoqHqBiL0uQyf8sm1ety5Id4V47uk6%2F2VCV4D%2FNc3Z6oUOpfs736B1s8sTdmyJJJ76r6p%2BGBDsgiEvfso1EnjGMKwu%2F%2F9m4j%2FCucngL6RlaMfprbdRjftxie5EuKSZCvyMHxPe%2BCEFJ3OnRGg6kZtzUMqYz%2FX8mK4FuxmsuFQPasvaL9HRrWkqVUSNUYo2Jl%2FxtwZpU3KQOoEfi8vkb&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230506T210000Z&X-Amz-SignedHeaders=host&X-Amz-Expires=21600&X-Amz-Credential=ASIAYDKQORRYSMJG4C5N%2F20230506%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=7e8c4e8e61f20a547feac85696a361d03691a3ad21c9b9bd572111c25f2afd34
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