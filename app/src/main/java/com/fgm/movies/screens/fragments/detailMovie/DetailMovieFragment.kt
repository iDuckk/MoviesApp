package com.fgm.movies.screens.fragments.detailMovie

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fgm.movies.R
import com.fgm.movies.screens.main.MainActivity
import com.fgm.movies.screens.main.PresenterActivity
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailMovieFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val DETAIL_LIST = "description_movie"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pres = PresenterActivity(activity as MainActivity).backArrow(true)

        val imPoster = view.findViewById<ImageView>(R.id.imageViewMovieDetail)
        val tvName = view.findViewById<TextView>(R.id.textViewNameDetail)
        val tvYear = view.findViewById<TextView>(R.id.textViewYear)
        val tvRating = view.findViewById<TextView>(R.id.textViewRating)
        val tvDescription = view.findViewById<TextView>(R.id.textViewDescription)
        val  localName : String
        //Receive Array Of Movies Details from Item Fragment
        val details = arguments?.getStringArrayList(DETAIL_LIST)
        // 1. URL 2. LocalName 3. Name 4. Year 5. Rating 6. Description
        //Set Poster
        if(details != null) {
            Picasso.get().load(details?.get(0)!!.toString()).error(R.drawable.ic_launcher_foreground).into(imPoster)
        }
        else {
            Picasso.get().load(R.drawable.ic_launcher_foreground).into(imPoster)
        }
        //Set LocalName checkLength(details?.get(2)!!.toString())
        PresenterActivity(activity as MainActivity).setTitleToolBar(details?.get(1)!!.toString())
        //Set Name
        tvName.text = details?.get(2)!!.toString()
        //Set Year
        tvYear.text = details?.get(3)!!.toString()
        //Set Rating
        tvRating.text = details?.get(4)!!.toString()
        //Set Description
        tvDescription.text = details?.get(5)!!.toString()


    }

override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(androidx.core.R.menu.example_menu, menu);

    super.onCreateOptionsMenu(menu, inflater)
}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                 Navigation.findNavController(requireView()).
            navigate(R.id.action_detailMovieFragment_to_itemFragment)
        }
        return false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailMovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}