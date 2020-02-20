package org.ieselcaminas.daniel.proyectoclase

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.ieselcaminas.daniel.proyectoclase.data.Species
import org.ieselcaminas.daniel.proyectoclase.data.Stats
import org.ieselcaminas.daniel.proyectoclase.dex.DexAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_anim)
                .error(R.drawable.broken))
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Species>?) {
    val adapter = recyclerView.adapter as DexAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

@BindingAdapter("idString")
fun TextView.setIdString(id: Long) {
    id.let {
        text = "#" + id
    }
}

@BindingAdapter("setBaseStat")
fun TextView.setBaseStat(data: List<Stats>, string: String, type: String) {
    data.let {
        for (i in it) {
            if (i.species == string) {
                when(type) {
                    "atk" -> {text = i.atk.toString()}
                    "def" -> {text = i.dfs.toString()}
                    "spatk" -> {text = i.sp_atk.toString()}
                    "spdef" -> {text = i.sp_dfs.toString()}
                    "spd" -> {text = i.spd.toString()}
                }
            }
        }
    }

}