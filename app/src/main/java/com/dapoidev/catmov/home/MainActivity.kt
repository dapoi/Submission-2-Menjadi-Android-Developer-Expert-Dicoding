package com.dapoidev.catmov.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapoidev.catmov.core.source.Resource
import com.dapoidev.catmov.core.ui.MovieAdapter
import com.dapoidev.catmov.databinding.ActivityMainBinding
import com.dapoidev.catmov.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private val movieViewModel: MovieViewModel by viewModel()
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { movie ->
            Intent(this, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.DATA, movie)
                startActivity(it)
            }
        }

        movieViewModel.movie.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> true.progressbar()
                    is Resource.Success -> {
                        false.progressbar()
                        movieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        false.progressbar()
                        binding.viewError.apply {
                            root.visibility = View.INVISIBLE
                            tvError.text = it.message
                        }

                    }
                }
            }
        })

        setRecyclerView()

        val uri = Uri.parse("catmov://favorite")
        binding.fab.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun setRecyclerView() {
        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = movieAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0 && binding.fab.visibility == View.VISIBLE) {
                        binding.fab.hide()
                    } else {
                        binding.fab.show()
                    }
                }
            })
        }
    }

    private fun Boolean.progressbar() {
        binding.progressBar.visibility = if (this) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}