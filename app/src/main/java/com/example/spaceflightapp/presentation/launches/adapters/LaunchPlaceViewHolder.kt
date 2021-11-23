package com.example.spaceflightapp.presentation.launches.adapters

import android.view.View
import android.widget.TextView
import com.example.spaceflightapp.R
import com.example.spaceflightapp.presentation.launches.LaunchUi

class LaunchPlaceViewHolder(view: View) : LaunchDetailViewHolder<LaunchUi.LaunchPlace>(view) {
    private val textView: TextView = itemView.findViewById(R.id.textView)
    override fun bind(model: Any) {
        textView.text =
            itemView.context.getString(R.string.place, (model as LaunchUi.LaunchPlace).value)
    }
}