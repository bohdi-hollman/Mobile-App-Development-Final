package com.sum.room.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sum.room.R
import com.sum.room.databinding.FragmentUpdateBinding
import com.sum.room.model.Vocab
import com.sum.room.viewmodel.VocabViewModel


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mVocabViewModel: VocabViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mVocabViewModel = ViewModelProvider(this)[VocabViewModel::class.java]

        binding.updateVocabWord.setText(args.currentVocab.vocabWord)
        binding.updateVocabDef.setText(args.currentVocab.vocabDef)

        binding.updateButton.setOnClickListener {
            updateItem()

        }
        //Add menu
        val menuHost: MenuHost = requireActivity()
        Log.v("menu", "insede menu")
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        deleteVocab()

                        true
                    }
                    else -> false
                }
            }


        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }


    private fun updateItem() {
        val vocabWord = binding.updateVocabWord.text.toString()
        val vocabDef = binding.updateVocabDef.text.toString()

        if (inputCheck(vocabWord, vocabDef)) {
            //Create Vocab Object
            val updateVocab = Vocab(args.currentVocab.id, vocabWord, vocabDef)
            //Update Current Vocab
            mVocabViewModel.updateVocab(updateVocab)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()

        }
    }


    private fun inputCheck(vocabWord: String, vocabDef: String): Boolean {
        return !(TextUtils.isEmpty(vocabWord) && TextUtils.isEmpty(vocabDef))

    }


    private fun deleteVocab() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mVocabViewModel.deleteVocab(args.currentVocab)
            Toast.makeText(requireContext(),
                "Successfully removed ${args.currentVocab.vocabWord}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentVocab.vocabWord}?")
        builder.setMessage("Are you sure you want to delete ${args.currentVocab.vocabWord}")
        builder.create().show()
    }


}

