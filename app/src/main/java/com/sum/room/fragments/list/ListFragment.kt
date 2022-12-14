package com.sum.room.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sum.room.ListAdapter
import com.sum.room.R
import com.sum.room.viewmodel.VocabViewModel
import com.sum.room.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var  mVocabViewModel: VocabViewModel
    private lateinit var  adapter: ListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

//Add menu
        val menuHost: MenuHost = requireActivity()
        Log.v("menu", "inside menu")

        menuHost.addMenuProvider(object : MenuProvider, SearchView.OnQueryTextListener {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.search_menu,menu)
                menuInflater.inflate(R.menu.delete_menu, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_delete-> {
                        deleteAllVocab()
                        true
                    }
                    R.id.ic_search->{

                        val searchView = menuItem.actionView as SearchView
                                searchView.setOnQueryTextListener(this)
                        true
                    }

                    else -> false
                }
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    mVocabViewModel.searchDatabase(query)
                    Log.v("Query",query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{
                    mVocabViewModel.searchDatabase(newText)
                    mVocabViewModel.tempList.observe(viewLifecycleOwner){
                        adapter.setData(it)


                    }

                }
                return true
            }


        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        //Recyclerview
        adapter = ListAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter= adapter
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        //VocabViewModel
        mVocabViewModel = ViewModelProvider(this)[VocabViewModel::class.java]
        mVocabViewModel.readAllData.observe(viewLifecycleOwner){vocab->
            adapter.setData(vocab)

        }
    }

    private fun deleteAllVocab() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mVocabViewModel.deleteAllVocab()
            Toast.makeText(requireContext(),
                "Successfully Removed All",
                Toast.LENGTH_SHORT).show()


        }

        builder.setNegativeButton("No"){_, _-> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }








}