package com.fgm.movies.model.dbHelpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperMainList(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given

        // we are calling sqlite
        // method for executing our query
        val query =
            ("CREATE TABLE $TABLE_NAMES_MAIN_LIST($ID INTEGER, $GENRES TEXT, $URL_IMAGE TEXT, " +
                    "$NAME_FILM TEXT, $LOCAL_NAME TEXT ,$DESCRIPTION TEXT, $RATING DOUBLE, $YEAR INTEGER, $UNIC_GENRE TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES_MAIN_LIST)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addItem(id : Int, genre : String = "", urlImage : String = "", nameFilm : String = "", locName : String = "",
                descrip : String = "", rating : Double = 0.0, year : Int = 0, unicGenre : String = ""){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair

        values.put(ID, id)
        values.put(GENRES, genre)
        values.put(URL_IMAGE, urlImage)
        values.put(NAME_FILM, nameFilm)
        values.put(LOCAL_NAME, locName)
        values.put(DESCRIPTION, descrip)
        values.put(RATING, rating)
        values.put(YEAR, year)
        values.put(UNIC_GENRE, unicGenre)


        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAMES_MAIN_LIST, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getItem(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAMES_MAIN_LIST, null)

    }

    // below is the method for deleting our course.
    fun deleteCourse(courseID: Int) {

        // on below line we are creating
        // a variable to write our database.
        val db = this.writableDatabase

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAMES_MAIN_LIST, "id=?", arrayOf(courseID.toString()))
        db.close()
    }

    fun deleteAll() {

        val db = this.writableDatabase

        db.delete(TABLE_NAMES_MAIN_LIST, null, null)

        db.close()
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "MAIN_LIST" //FREE_ACCOUNT_FOR_NAME

        // below is the variable for database version
        private val DATABASE_VERSION = 4

        // below is the variable for table name
        val TABLE_NAMES_MAIN_LIST = "mainList_table_movie" //productsName_table

        //VALUES
        val GENRES = "genres"
        val UNIC_GENRE = "unic_genre"
        val URL_IMAGE = "url_image"
        val NAME_FILM = "name"
        val DESCRIPTION = "description"
        val ID = "id"
        val LOCAL_NAME = "localized_name"
        val RATING = "rating"
        val YEAR = "year"
    }
}