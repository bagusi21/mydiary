package org.d3if0060.mydiary

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import org.d3if0060.mydiary.database.DataDiary
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy")
        .format(systemTime).toString()
}

fun formatDiary(diaries: List<DataDiary>, resources: Resources): Spanned {
    val str = StringBuilder()
    str.apply {
        diaries.forEach {
            append("<br>")
            append("\t${convertLongToDateString(it.datetime)}")
            append("<br>")
            append("\t${it.isi_diary}")
            append("<br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(str.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(str.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)