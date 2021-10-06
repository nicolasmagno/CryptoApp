package com.mgsolutions.cryptoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.*

class AdapterCoins(private val coinList:ArrayList<Coin>, private val context: Context) : RecyclerView.Adapter<AdapterCoins.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = coinList[position]
        holder.name.text = coin.name
        holder.code.text = coin.code
        holder.price.text = "$${coin.price}"
        holder.code.text = coin.code

        Picasso.get().load(coin.img).into(holder.img)

    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.id_nombre_moneda)
        val price = view.findViewById<TextView>(R.id.id_precio_moneda)
        val code = view.findViewById<TextView>(R.id.id_cod_moneda)
        val img = view.findViewById<ImageView>(R.id.id_img_moneda)

    }

}