package vnd.macro.sot.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.view.*
import vnd.macro.sot.R
import vnd.macro.sot.model.SelectRequestBody
import vnd.macro.sot.util.AppPreferences
import vnd.macro.sot.util.Const
import vnd.macro.sot.viewmodel.ListViewModel
import java.lang.Appendable

private const val SELECTED_TEXT = "selectedText"

class SelectFragment : Fragment() {
    lateinit var viewModel: ListViewModel
    private var selectedText: String? = null
    private val refLinkAdapter = RefLinkAdapter(arrayListOf(), context)
    private var intro = SpannableStringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedText = it.getString(SELECTED_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select, container, false)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        val selectRequestBody = SelectRequestBody(Const.LENGTH_PARAM, selectedText)
        viewModel.getRefLinks(selectRequestBody, AppPreferences.accessToken)
        view.rv_ref.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = refLinkAdapter
            observeViewModel(view)
        }

        val head = "Results from the most Trusted and Unbiased news outlets for: \""
        intro = SpannableStringBuilder()
            .append(head)
            .bold { append(selectedText) }
            .append("\"")
        view.tv_intro.text = intro
        view.tv_back.setOnClickListener {
            activity?.onBackPressed()
        }
        return view
    }

    private fun observeViewModel(view: View) {
        viewModel.refLinks.observe(this, Observer { singleRefLinks ->
            singleRefLinks?.let {
                view.rv_ref.visibility = View.VISIBLE
                refLinkAdapter.updateRefLinks(it)
            }
        })

        viewModel.serverError.observe(this, Observer { isError ->
            isError?.let {
                view.tv_intro.text = if (!it)
                    intro
                else "No result found!"
                view.tv_intro.visibility = View.VISIBLE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                view.pb_loading.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    view.tv_intro.visibility = View.GONE
                    view.rv_ref.visibility = View.GONE
                }
            }
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(selectedText: String) =
            SelectFragment().apply {
                arguments = Bundle().apply {
                    putString(SELECTED_TEXT, selectedText)
                }
            }
    }
}