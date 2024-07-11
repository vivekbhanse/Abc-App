package com.example.myassignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myassignment.data.models.BannerContentList
import com.example.myassignment.databinding.ItemListBannerconttentBinding
import java.util.Locale


class TitleAdpater(private val dataList: List<BannerContentList>) :
    RecyclerView.Adapter<TitleAdpater.ViewHolder>(), Filterable {
    private var filteredItems: List<BannerContentList> = dataList

    class ViewHolder(val binding: ItemListBannerconttentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBannerconttentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            filteredItems.get(position).subBannerImage?.let {
                binding.recImageView.setImageResource(
                    it
                )
            }
            binding.txtTitle.text = filteredItems.get(position).title
            binding.txtSubTitle.text = filteredItems.get(position).subTitle

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""
                filteredItems = if (query.isEmpty()) {
                    dataList
                } else {
                    dataList.filter {
                        it.title?.lowercase(Locale.getDefault())?.contains(query) == true
                    }
                }
                return FilterResults().apply { values = filteredItems }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as List<BannerContentList>
                notifyDataSetChanged()
            }
        }
    }
}
