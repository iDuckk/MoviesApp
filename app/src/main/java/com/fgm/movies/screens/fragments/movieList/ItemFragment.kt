package com.fgm.movies.screens.fragments.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fgm.movies.R
import com.fgm.movies.adapters.MyItemRecyclerViewAdapter
import com.fgm.movies.model.placeholder.PlaceholderContent
import com.fgm.movies.screens.main.MainActivity
import com.fgm.movies.screens.main.PresenterActivity


/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    val MAX_POOL_SIZE_GENRE = 15
    val MAX_POOL_SIZE_IMAGE = 15
    val COLUMN_LIST_GENRE = 0
    val COLUMN_LIST_IMAGE = 1
    val DETAIL_LIST = "description_movie"
    val COLUMN = 2
    lateinit var MyAdapter : MyItemRecyclerViewAdapter

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        PresenterActivity(activity as MainActivity).setTitleToolBar(getString(R.string.mainTitle))
        PresenterActivity(activity as MainActivity).backArrow(false)
//        PresenterActivity(activity as MainActivity).progressBar().visibility = View.VISIBLE

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //Do nothing. That do not return to LoadFragment
                }
            }
        )

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {

                view.layoutManager =  GridLayoutManager(context, COLUMN)


                val presenter = PresenterMovieList(view, context)
                presenter.CreateMovieList() //Get Data from JSON to SQL

                MyAdapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
                adapter = MyAdapter


                view.recycledViewPool.setMaxRecycledViews(COLUMN_LIST_GENRE, MAX_POOL_SIZE_GENRE)
                view.recycledViewPool.setMaxRecycledViews(COLUMN_LIST_IMAGE, MAX_POOL_SIZE_IMAGE)

                //Receive Items
                MyAdapter.onClickItem = {
                    PlaceholderContent.CreateaListImageITEMS(it.unicsGenre)
                    adapter?.notifyDataSetChanged()
                }
                //Send Description
                MyAdapter.onClickItemImage = {
                    // 1. URL 2. LocalName 3. Name 4. Year 5. Rating 6. Description
                    var list = PlaceholderContent.getDetailsOfMovie(it.nameMovie)
                    val bundle = Bundle()
                    bundle.putStringArrayList(DETAIL_LIST, list)

                    Navigation.findNavController(requireView()).
                    navigate(R.id.action_itemFragment_to_detailMovieFragment, bundle)
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        //const val ARG_SAVE_ITEMS = "Save"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    //put(ARG_SAVE_ITEMS, PlaceholderContent.saveParams())
                }
            }
    }
}