package com.segunfamisa.zeitung.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.util.viewmodel.ViewModelFactory
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory<NewsViewModel>
    private val viewModel by viewModels<NewsViewModel> { factory }

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
