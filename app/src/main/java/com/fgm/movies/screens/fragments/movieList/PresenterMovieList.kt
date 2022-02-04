package com.fgm.movies.screens.fragments.movieList

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fgm.movies.api.ApiFactory
import com.fgm.movies.api.MovieService
import com.fgm.movies.model.Movie
import com.fgm.movies.model.dbHelpers.DBHelperMainList
import com.fgm.movies.model.placeholder.PlaceholderContent
import com.fgm.movies.screens.main.MainActivity
import com.fgm.movies.screens.main.PresenterActivity
import retrofit2.Call
import retrofit2.Response

class PresenterMovieList(val recV : RecyclerView, val context : Context){

    val TYPE_LIST_GENRE = 0
    val TYPE_LIST_IMAGE = 1
    val GENERS_TITLE = "Жанры"
    val FILM_TITLE = "Фильмы"

    fun CreateMovieList(){
        val movieService : MovieService = ApiFactory.getInstance().create(MovieService::class.java)
        movieService.getAllData().enqueue(object : retrofit2.Callback<Movie?> {
            override fun onResponse(call: Call<Movie?>, response: Response<Movie?>) {
                val responseBody = response.body()!!.films //Take all Film values
                var genres = ArrayList<String>()
                val dbMainList = DBHelperMainList(context, null)

                //Create List of genres
                for (Data in responseBody) {     //In each
                    Data.genres.forEach {  //Take all Genres from each movies
                        genres.add(it)
                    }
                }

                //Save Values in SQL unicsGenres
                genres.distinct().forEach {
                    dbMainList.addItem(0, "", "", "", "", "", 0.0, 0, it)
//                    Log.d("TEG: ", it)
                }

                //Add List of image
                for (Data in responseBody) {     //In each
                    dbMainList.addItem(
                        if (Data.id != null) Data.id else 0,
                        if (Data.genres != null) Data.genres.toString() else "",
                        if (Data.image_url != null) Data.image_url else "",
                        if (Data.name != null) Data.name else "",
                        if (Data.localized_name != null) Data.localized_name else "",
                        if (Data.description != null) Data.description else "",
                        if (Data.rating != null) Data.rating else 0.0,
                        if (Data.year != null) Data.year else 1900,
                        ""
                    )
                }
                //When we received Data
                if(getAllData()) {
                    PlaceholderContent.CreateaListMain(recV) // Set Item on list
                    //PresenterActivity(context.pa).progressBar().visibility = View.VISIBLE
                }
            }
            override fun onFailure(call: Call<Movie?>, t: Throwable) {
                Log.d("TEG", "Error: " + t.message.toString())
            }
                //Sort list for unics values
//                for (i in 0..genres.lastIndex){
//                    var j = i+1     //Index of Item which we`ll be compare
//                    while (j <= genres.lastIndex){
//                        if(genres[i] == genres[j]) {
//                            genres.removeAt(j)
//                            j-- //Because we can miss the Item which replaced the removed Item
//                        }
//                        j++     //next item
//                    }
//                }

        })
    }
    //Add to Items List
    fun createList(id : Int, genre : String = "", urlImage : String = "", nameFilm : String = "", locName : String = "",
                   descrip : String = "", rating : Double = 0.0, year : Int = 0, unicGenre : String = "") {

        PlaceholderContent.ITEMS_All.add(PlaceholderContent.PlaceholderItemAll(id,genre,
            urlImage, nameFilm, locName, descrip, rating,year, unicGenre))
    }
    //Take Data From SQL
    fun getAllData() : Boolean{ //recV : RecyclerView
        val dbMainListAll = DBHelperMainList(context, null)
        PlaceholderContent.ITEMS_All.clear()
//        PlaceholderContent.ITEMS.clear()

        val cursor = dbMainListAll.getItem()
        //First element
        cursor!!.moveToFirst()
        if(cursor.count != 0) {
            if(cursor.getString(cursor.getColumnIndex(DBHelperMainList.UNIC_GENRE)).toString() == "") {
                createList(
                    cursor.getInt(cursor.getColumnIndex(DBHelperMainList.ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.GENRES)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.URL_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.NAME_FILM)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.LOCAL_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(DBHelperMainList.RATING)),
                    cursor.getInt(cursor.getColumnIndex(DBHelperMainList.YEAR)),
                    ""
                )

//                PlaceholderContent.ITEMS.add(PlaceholderContent.PlaceholderItem(TYPE_LIST_IMAGE,
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.GENRES)),
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.URL_IMAGE)),
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.LOCAL_NAME)),
//                    ""
//                    ))
            }else{
                createList(
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    0.0,
                    0,
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.UNIC_GENRE))
                )
//                PlaceholderContent.ITEMS.add(PlaceholderContent.PlaceholderItem(TYPE_LIST_GENRE,
//                    "",
//                    "",
//                    "",
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.UNIC_GENRE))
//                ))
            }
        }
        //Next elements
        while (cursor.moveToNext()) {
            if(cursor.getString(cursor.getColumnIndex(DBHelperMainList.UNIC_GENRE)).toString() == "") {
                createList(
                    cursor.getInt(cursor.getColumnIndex(DBHelperMainList.ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.GENRES)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.URL_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.NAME_FILM)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.LOCAL_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(DBHelperMainList.RATING)),
                    cursor.getInt(cursor.getColumnIndex(DBHelperMainList.YEAR)),
                    ""
                )
//                PlaceholderContent.ITEMS.add(PlaceholderContent.PlaceholderItem(TYPE_LIST_IMAGE,
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.GENRES)),
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.URL_IMAGE)),
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.LOCAL_NAME)),
//                    ""
//                ))
            }else{
                createList(
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    0.0,
                    0,
                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.UNIC_GENRE))
                )
//                PlaceholderContent.ITEMS.add(PlaceholderContent.PlaceholderItem(TYPE_LIST_GENRE,
//                    "",
//                    "",
//                    "",
//                    cursor.getString(cursor.getColumnIndex(DBHelperMainList.UNIC_GENRE))
//                ))
            }
        }
        cursor.close()
        dbMainListAll.deleteAll()
        //PlaceholderContent.CreateaListMain(recV)
    return true
    }
}