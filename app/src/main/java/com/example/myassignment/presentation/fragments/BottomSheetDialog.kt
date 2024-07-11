package com.example.myassignment.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myassignment.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(val finalString: String) : BottomSheetDialogFragment() {
    private var binding: BottomSheetDialogBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = BottomSheetDialogBinding.inflate(
            LayoutInflater.from(context), container, false
        )
        binding?.idBtnDismiss?.setOnClickListener {
            dialog?.dismiss()
        }
        binding?.txtFirst?.text = finalString

        return binding!!.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }


        }

        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val TAG = "BottomSheetDialog"
    }

}