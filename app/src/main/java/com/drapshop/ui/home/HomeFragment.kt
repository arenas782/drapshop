package com.drapshop.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drapshop.R
import com.drapshop.data.Piece
import com.drapshop.databinding.FragmentHomeBinding
import com.drapshop.utils.Utilities
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding

    private lateinit var piecesList : List<Piece>
    private var matchedPieces : ArrayList<Piece> = arrayListOf()
    private lateinit var piecesAdapter : MyAdapter




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )

        val jsonFileString = Utilities.getJsonDataFromAsset(requireContext(), "pieces.json")
        val gson = Gson()
        val listPieceType = object : TypeToken<List<Piece>>() {}.type
        piecesList  = gson.fromJson(jsonFileString, listPieceType)



        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        piecesAdapter = MyAdapter(piecesList.toArrayList())

        binding.recyclerView.adapter = piecesAdapter
        binding.searchView.isSubmitButtonEnabled = true


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("Submitted", query!!)
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.equals("")){
                    this.onQueryTextSubmit("");
                }
                return true
            }
        })
        Handler().postDelayed(
            {
            if(isAdded){


                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE


            }
                // This method will be executed once the timer is over
            },
            2000 // value in milliseconds
        )







        binding.lifecycleOwner = this
        return binding.root
    }

    private fun search(text: String?) {
        matchedPieces = arrayListOf()
        Log.e("Searching for", text!!)

        text.let {
            if (it.isEmpty()){
                matchedPieces=piecesList.toArrayList()

            }
            else{
                piecesList.forEach { piece ->
                    Log.e("Piece",piece.title)
                    if (piece.title.contains(text, true)) {
                        matchedPieces.add(piece)

                    }
                }

            }

            if (matchedPieces.isEmpty()) {
                Utilities.showSnackbar(requireActivity(),"No matches found")
            }
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
            binding.recyclerView.apply {
                piecesAdapter.dataSet = matchedPieces
                piecesAdapter.notifyDataSetChanged()
        }
    }

    private fun <T> List<T>.toArrayList(): ArrayList<T>{
        return ArrayList(this)
    }
}