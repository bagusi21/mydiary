package org.d3if0060.mydiary.fragmentlistdiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if0060.mydiary.R
import org.d3if0060.mydiary.convertLongToDateString
import org.d3if0060.mydiary.database.DataDiary
import org.d3if0060.mydiary.databinding.ListDiaryBinding

class DiaryAdapter(private val diary: List<DataDiary>): RecyclerView.Adapter<DiaryAdapter.ViewHolder>(){
    var listener: RecyclerViewClickListener? = null


    override fun getItemCount() = diary.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_diary, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = diary[position]
        holder.itemList.tvDateTime.text = convertLongToDateString(item.datetime)
        holder.itemList.tvIsiDiary.text = item.isi_diary

        holder.itemList.listItem.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, item)
        }
    }

    class ViewHolder(val itemList: ListDiaryBinding): RecyclerView.ViewHolder(itemList.root)
//        val isiDiary: TextView = itemView.findViewById(R.id.tv_isi_diary)
//        val time: TextView = itemView.findViewById(R.id.tv_date_time)

//        fun bind(item: DataDiary) {
//            isiDiary.text = item.isi_diary
//            time.text = convertLongToDateString(item.datetime)
//        }

}