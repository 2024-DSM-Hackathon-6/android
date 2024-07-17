package com.dsm.hackathon.feature

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dsm.hackathon.databinding.CustomDialogBinding

class CustomDialog(private val customDialogInterface: CustomDialogInterface) : DialogFragment() {
    private lateinit var binding: CustomDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CustomDialogBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.tvDialogOk.setOnClickListener {
            val content = binding.editDialog.text.toString()
            if (content != "") {
                customDialogInterface.onYesButtonClick(content)
                dismiss()
            } else {
                dismiss()
            }
        }
        binding.tvDialogCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

}

interface CustomDialogInterface {
    fun onYesButtonClick(content: String)
}