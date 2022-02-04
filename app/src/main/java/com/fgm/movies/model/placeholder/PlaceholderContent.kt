package com.fgm.movies.model.placeholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.fgm.movies.api.ApiFactory
import com.fgm.movies.api.MovieService
import com.fgm.movies.model.Movie
import com.fgm.movies.model.dbHelpers.DBHelperMainList
import com.fgm.movies.screens.fragments.movieList.PresenterMovieList
import com.fgm.movies.screens.main.MainActivity
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    var ITEMS: MutableList<PlaceholderItem> = ArrayList()
    //var ITEMS_GENERS: MutableList<PlaceholderItem> = ArrayList()
    val ITEMS_All: MutableList<PlaceholderItemAll> = ArrayList()

    val TYPE_LIST_GENRE = 0
    val TYPE_LIST_IMAGE = 1
    val GENERS_TITLE = "Жанры"
    val FILM_TITLE = "Фильмы"

//    var SIZE_LIST_GENRE = 0

    /**
     * A map of sample (placeholder) items, by ID.
     */


//    fun CreateaListGenreITEMS(){
//        if(!ITEMS.isEmpty()) {
//            ITEMS.clear()
//        }
////        //Title
//        ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE,"",
//            "", "", GENERS_TITLE))
//        //Get Only Genres
//        ITEMS_All.forEach {
//            if(it.unicGenre != ""){
//                ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE, "", "", "", it.unicGenre))
//            }
//        }
//        //Save size for NotifyChanges
////        SIZE_LIST_GENRE = ITEMS.size
//    }


    fun CreateaListMain(recV : RecyclerView){
        ITEMS.clear()
        var listForSorting: MutableList<PlaceholderItem> = ArrayList()
        //Title
        ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE,"",
            "", "", GENERS_TITLE))
        ITEMS_All.forEach {
            if(it.unicGenre != ""){
                //Get Only Genres
                ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE, "", "", "",
                    it.unicGenre, it.id))
            }else{
                    listForSorting.add(
                        PlaceholderItem(
                            TYPE_LIST_IMAGE,
                            it.genre,
                            it.urlImage,
                            it.locName,
                            "",
                            it.id
                        )
                    )
            }
        }
        recV.adapter?.notifyDataSetChanged()
        listForSorting.sortBy { it.nameMovie } //Sort ABC
        //Title
        ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE, "", "", "",FILM_TITLE))
        ITEMS.addAll(listForSorting)
        //        ITEMS.sortBy { it.unicsGenre }
    }

    fun CreateaListImageITEMS(genre : String){
        ITEMS.clear()
        var listForSorting: MutableList<PlaceholderItem> = ArrayList()
//        var titleBoolean = true
        //Title
        ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE,"",
            "", "", GENERS_TITLE))
        ITEMS_All.forEach {
            if(it.unicGenre != ""){
                //Get Only Genres
                ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE, "", "", "",
                    it.unicGenre, it.id))
            }else{
                //Choose category movies
                if(it.genre.indexOf(genre) >= 0) {
                    //titleMovie(titleBoolean)
//                    titleBoolean = false
                    listForSorting.add(
                        PlaceholderItem(
                            TYPE_LIST_IMAGE,
                            it.genre,
                            it.urlImage,
                            it.locName,
                            "",
                            it.id
                        )
                    )
                }
            }
        }
        listForSorting.sortBy { it.nameMovie } //Sort ABC
        //Title
        ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE, "", "", "",FILM_TITLE))
        ITEMS.addAll(listForSorting)
        //        ITEMS.sortBy { it.unicsGenre }
    }

    fun getDetailsOfMovie(name : String)  : ArrayList<String>{
        var list = arrayListOf<String>()
        if(ITEMS_All != null){
            ITEMS_All.forEach {
                // 1. URL 2. LocalName 3. Name 4. Year 5. Rating 6. Description
                if(name == it.locName){
                    list.add(it.urlImage)
                    list.add(it.locName)
                    list.add(it.nameFilm)
                    list.add(it.year.toString())
                    list.add(it.rating.toString())
                    list.add(it.descrip)
                }
            }
        }

        return list
    }

//    fun titleMovie(b : Boolean){
//        if(b){
//            //Title
//            ITEMS.add(PlaceholderItem(TYPE_LIST_GENRE,"",
//                "", "", FILM_TITLE))
//        }
//    }

    fun saveParams() : ArrayList<PlaceholderItem> {
        var list = arrayListOf<PlaceholderItem>()
        ITEMS.forEachIndexed{index, it ->
            list[index] = PlaceholderItem(it.type , it. genere, it.urlImage,
                it.nameMovie, it.unicsGenre)
        }
        return list
    }

    fun delITEMS(){
        ITEMS.clear()
    }

    /**
     * A placeholder item representing a piece of content.
     */

    data class PlaceholderItem(var type : Int, var genere : String, var urlImage : String,
                               var nameMovie : String, var unicsGenre : String, var id : Int = 0) {
        //override fun toString(): String = content
    }

    data class PlaceholderItemAll(var id : Int, var genre : String = "", var urlImage : String = "",
                                  var nameFilm : String = "", var locName : String = "",
                               var descrip : String = "", var rating : Double = 0.0, var year : Int = 0,
                                  var unicGenre : String = "") {
        //override fun toString(): String = content
    }
}