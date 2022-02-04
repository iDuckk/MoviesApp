package com.fgm.movies.adapters


import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.fgm.movies.R
import com.fgm.movies.databinding.FragmentItemBinding
import com.fgm.movies.databinding.FragmentItemImageBinding
import com.fgm.movies.model.placeholder.PlaceholderContent.PlaceholderItem
import com.squareup.picasso.Picasso
import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class MyItemRecyclerViewAdapter(
    private val values: MutableList<PlaceholderItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

//    var localValuesList = values
//    set(value) {
//        val callback = ItemsListCallback(localValuesList, value)
//        val diffResult = DiffUtil.calculateDiff(callback)
//        diffResult.dispatchUpdatesTo(this)
//        field = value
//        notifyDataSetChanged()
//    }


    val GENERS_TITLE = "Жанры"
    val FILM_TITLE = "Фильмы"
    var OLD_VALUES_ITEM = ""
    val COLUMN_LIST_GENRE = 0
    val COLUMN_LIST_IMAGE = 1
    val TYPE_GENRE_LIST = 0
    var pos : Int = 0
    //var previousTextView : TextView? = null
    var onClickItem : ((item : PlaceholderItem) -> Unit)? = null
    var onClickItemImage : ((item : PlaceholderItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if(viewType == TYPE_GENRE_LIST){
            var view = ViewHolder(
                FragmentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            return view
        }else{
            var viewIm = ViewHolder(
                FragmentItemImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            return viewIm
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.tvName.text = item.nameMovie

        pos = position

        //List of Genres
        if(item.type == TYPE_GENRE_LIST) {
            holder.imMovie.visibility = View.GONE
            holder.tvName.visibility = View.GONE
            //Filling RecView
            if (item.unicsGenre == GENERS_TITLE || item.unicsGenre == FILM_TITLE) { //Titles
                holder.tvGenres.visibility = View.GONE
                holder.tvTitle.visibility = View.VISIBLE
                holder.tvGenresClicked.visibility = View.GONE
                holder.tvTitle.text = item.unicsGenre
            }else if(item.unicsGenre == OLD_VALUES_ITEM){
                holder.tvGenresClicked.visibility = View.VISIBLE
                holder.tvGenres.visibility = View.GONE
                holder.tvTitle.visibility = View.GONE
                holder.tvGenresClicked.text = item.unicsGenre
            } else {
                holder.tvGenres.text = item.unicsGenre
                holder.tvTitle.visibility = View.GONE
                holder.tvGenres.visibility = View.VISIBLE
                holder.tvGenresClicked.visibility = View.GONE

                holder.tvGenres.setOnClickListener {
                    onClickItem?.invoke(item) //Send values to ItemFragment
                    OLD_VALUES_ITEM = item.unicsGenre
                }

            }
        }else{ //List of Movie's images
            //Item for Genre GONE
            holder.tvTitle.visibility = View.GONE
            holder.tvGenres.visibility = View.GONE
            holder.tvGenresClicked.visibility = View.GONE
            //Item for Image Visible
            holder.tvName.visibility = View.VISIBLE
            holder.imMovie.visibility = View.VISIBLE

            holder.tvName.text = checkLength(item.nameMovie)

            if(item.urlImage != "") {
                Picasso.get().load(item.urlImage).error(R.drawable.ic_launcher_foreground).into(holder.imMovie)
            }
            else {
                Picasso.get().load(R.drawable.ic_launcher_foreground).into(holder.imMovie)
            }

            holder.imMovie.setOnClickListener{
                onClickItemImage?.invoke(item) //Send values to ItemFragment
            }
        }
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) { //ViewBinding
        //Item for Genre
        val tvGenres: TextView = binding.root.findViewById(R.id.textViewGenre)
        val tvGenresClicked: TextView = binding.root.findViewById(R.id.textViewGenreClicked)
        val tvTitle: TextView = binding.root.findViewById(R.id.TextViewTitle)
        //Item for Image
        val tvName: TextView = binding.root.findViewById(R.id.TextViewName)
        val imMovie: ImageView = binding.root.findViewById(R.id.imageViewMovie)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val gridManager = manager as GridLayoutManager
            gridManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {  //Allows setting how many columns View SPAN
                    val type = getItemViewType(position)
                    return when (type) {
                        COLUMN_LIST_GENRE -> 2  //layout 1 column, Item span 2 place in RecView
                        COLUMN_LIST_IMAGE -> 1  //layout 2 column, Item_image span 1 place in RecView
                        else -> 6
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)

        if(values[position].type == TYPE_GENRE_LIST){
            return COLUMN_LIST_GENRE //layout of genres list
        }else
            return COLUMN_LIST_IMAGE //layout of images list
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
//        if(previousTextView != null && previousTextView?.text == holder.tvGenres.text){
//            previousTextView?.setBackgroundResource(R.drawable.corner_view_title_click)
//        }
        //holder.tvGenres.setBackgroundResource(R.drawable.corner_view)
    }

    fun checkLength(str : String) : String{
        if(str.length > 30){
            var dropValue = str.length - 30
            return "${str.dropLast(dropValue)}..."
        }else return str
    }

}