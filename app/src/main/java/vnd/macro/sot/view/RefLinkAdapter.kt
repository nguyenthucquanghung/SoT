package vnd.macro.sot.view

import android.R.attr.data
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_reference_link.view.*
import vnd.macro.sot.R
import vnd.macro.sot.model.ReferenceLink
import vnd.macro.sot.util.loadImage


class RefLinkAdapter(var refLinks: ArrayList<ReferenceLink>, context: Context?) :
    RecyclerView.Adapter<RefLinkAdapter.RefLinkViewHolder>() {

    private val mContext: Context? = context
    fun updateRefLinks(newRefLinks: List<ReferenceLink>) {
        refLinks.clear()
        refLinks.addAll(newRefLinks)
        notifyDataSetChanged()
    }
    class RefLinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivThumbnail = view.iv_thumbnail
        private val tvTitle = view.tv_title
        private val tvDesc = view.tv_desc
        val tvUrl = view.tv_url
        fun bind(singleRefLink: ReferenceLink) {
            tvTitle.text = singleRefLink.title
            tvDesc.text = singleRefLink.desc
            tvUrl.text = singleRefLink.url
            ivThumbnail.loadImage(singleRefLink.thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RefLinkViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reference_link, parent, false)
    )

    override fun getItemCount() = refLinks.size

    override fun onBindViewHolder(holder: RefLinkViewHolder, position: Int) {
        holder.bind(refLinks[position])
        holder.tvUrl.setOnClickListener {
            val defaultBrowser =
                Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
            defaultBrowser.data = Uri.parse(refLinks[position].url)
            mContext?.startActivity(defaultBrowser)

        }
    }
}