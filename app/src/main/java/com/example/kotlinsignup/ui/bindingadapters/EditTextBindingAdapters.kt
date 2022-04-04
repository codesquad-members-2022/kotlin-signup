package com.example.kotlinsignup.ui

import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textChangedListener")
fun bindTextWatcher(view: EditText, textWatcher: TextWatcher?) {
    textWatcher?.let {
        view.addTextChangedListener(it)
    }
}