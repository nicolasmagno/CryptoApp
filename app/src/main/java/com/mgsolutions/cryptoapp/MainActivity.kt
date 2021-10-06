package com.mgsolutions.cryptoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.util.*

class MainActivity : AppCompatActivity() {

    private var coinList: ArrayList<Coin> = ArrayList()
    private var filterList: ArrayList<Coin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        loadCoinsData()
        setUpRecycler()
        loadCoinsData()

    }

    private fun loadCoinsData() {

        val stringRequest = StringRequest(Request.Method.GET, "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false", { response ->

            coinList.clear()
            filterList.clear()

            val jsonArray = JSONArray(response)

            for(i in 0 until jsonArray.length()){

                val coin = Coin()
                val jsonObject = jsonArray.getJSONObject(i)

                coin.name = jsonObject.optString("name")
                coin.price = jsonObject.optDouble("current_price")
                coin.img = jsonObject.optString("image")
                coin.code = jsonObject.optString("symbol")

                coinList.add(coin)
                filterList.add(coin)

                id_recycler_monedas.adapter?.notifyDataSetChanged()

            }

        }, {e ->
            Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_SHORT).show()
            Log.e("ERROR RESPONSE: ", e.message)
            e.printStackTrace()
        })

        Volleyhelper.getInstance(this)!!.addToRequestQueue(stringRequest)

    }


    private fun setUpToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.id_toolbar_main)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        toolbar.title = resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)
    }


    private fun setUpRecycler(){
        id_recycler_monedas.adapter = AdapterCoins(coinList, this@MainActivity)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val iconSearch = menu!!.findItem(R.id.id_menu_search).icon
        iconSearch.setTint(ContextCompat.getColor(this, android.R.color.white))

        val search = menu!!.findItem(R.id.id_menu_search).actionView as SearchView
        search.queryHint = "Buscar..."
        search.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).setTextColor(ContextCompat.getColor(this, android.R.color.white))
        search.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).setHintTextColor(ContextCompat.getColor(this, android.R.color.white))


        search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                coinList.clear()
                for (coin in filterList) {
                    if (coin.name.toLowerCase().contains(newText!!.toLowerCase().trim())) {
                        coinList.add(coin)
                    }

                    if(coinList.size == 0){
                        id_layout_noResults.visibility = View.VISIBLE
                        id_recycler_monedas.visibility = View.GONE
                    }else{
                        id_layout_noResults.visibility = View.GONE
                        id_recycler_monedas.visibility = View.VISIBLE
                    }
                }

                id_recycler_monedas.adapter!!.notifyDataSetChanged()

                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }


}