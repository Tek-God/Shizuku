package moe.shizuku.manager.home

import android.content.Intent
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import moe.shizuku.manager.R
import moe.shizuku.manager.ShizukuSettings
import moe.shizuku.manager.databinding.HomeStartRootBinding
import moe.shizuku.manager.ktx.toHtml
import moe.shizuku.manager.starter.StarterActivity
import rikka.html.text.HtmlCompat
import rikka.recyclerview.BaseViewHolder
import rikka.recyclerview.BaseViewHolder.Creator

class StartRootViewHolder(private val binding: HomeStartRootBinding) : BaseViewHolder<Boolean>(binding.root) {

    companion object {
        val CREATOR = Creator<Boolean> { inflater: LayoutInflater, parent: ViewGroup? -> StartRootViewHolder(HomeStartRootBinding.inflate(inflater, parent, false)) }
    }

    private inline val start get() = binding.button1
    private inline val restart get() = binding.button2

    private var alertDialog: AlertDialog? = null

    init {
        val listener = View.OnClickListener { v: View -> onStartClicked(v) }
        start.setOnClickListener(listener)
        restart.setOnClickListener(listener)
        binding.text1.movementMethod = LinkMovementMethod.getInstance()
        binding.text1.text = context.getString(R.string.home_root_description, "<b><a href=\"https://dontkillmyapp.com/\">Don\'t kill my app!</a></b>").toHtml(HtmlCompat.FROM_HTML_OPTION_TRIM_WHITESPACE)
    }

    private fun onStartClicked(v: View) {
        val context = v.context
        val intent = Intent(context, StarterActivity::class.java).apply {
            putExtra(StarterActivity.EXTRA_IS_ROOT, true)
        }
        context.startActivity(intent)
    }

    override fun onBind() {
        start.isEnabled = true
        restart.isEnabled = true
        if (data!!) {
            start.visibility = View.GONE
            restart.visibility = View.VISIBLE
        } else {
            start.visibility = View.VISIBLE
            restart.visibility = View.GONE
        }
    }

    override fun onRecycle() {
        super.onRecycle()
        alertDialog = null
    }
}