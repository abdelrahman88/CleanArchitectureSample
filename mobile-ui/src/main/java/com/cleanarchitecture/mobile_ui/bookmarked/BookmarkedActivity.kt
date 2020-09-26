package com.cleanarchitecture.mobile_ui.bookmarked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cleanarchitecture.mobile_ui.R
import com.cleanarchitecture.mobile_ui.injection.ViewModelFactory
import com.cleanarchitecture.mobile_ui.mapper.ProjectViewMapper
import com.cleanarchitecture.presentation.BrowseBookmarkedProjectsViewModel
import com.cleanarchitecture.presentation.model.ProjectView
import com.cleanarchitecture.presentation.state.Resource
import com.cleanarchitecture.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BookmarkedActivity: AppCompatActivity() {

    @Inject
    lateinit var adapter: BookmarkedAdapter
    @Inject
    lateinit var mapper: ProjectViewMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var browseViewModel: BrowseBookmarkedProjectsViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, BookmarkedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)
        AndroidInjection.inject(this)

        browseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseBookmarkedProjectsViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getBookmarkedProjects().observe(this,
            Observer<Resource<List<ProjectView>>> {
                it?.let {
                    handleDataState(it)
                }
            })
        browseViewModel.fetchBookmarkedProjects()
    }

    private fun setupBrowseRecycler() {
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                progress.visibility = View.GONE
                recycler_projects.visibility = View.VISIBLE
                resource.data?.let {
                    adapter.projects = it.map { mapper.mapToView(it) }
                    adapter.notifyDataSetChanged()
                }
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
        }
    }
}