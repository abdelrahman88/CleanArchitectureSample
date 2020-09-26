package com.cleanarchitecture.mobile_ui.browse

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cleanarchitecture.mobile_ui.R
import com.cleanarchitecture.mobile_ui.bookmarked.BookmarkedActivity
import com.cleanarchitecture.mobile_ui.injection.ViewModelFactory
import com.cleanarchitecture.mobile_ui.mapper.ProjectViewMapper
import com.cleanarchitecture.mobile_ui.model.Project
import com.cleanarchitecture.presentation.BrowseProjectsViewModel
import com.cleanarchitecture.presentation.model.ProjectView
import com.cleanarchitecture.presentation.state.Resource
import com.cleanarchitecture.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject
    lateinit var browseAdapter: BrowseAdapter
    @Inject
    lateinit var mapper: ProjectViewMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var browseViewModel: BrowseProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)

        browseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseProjectsViewModel::class.java)
        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getProjects().observe(this,
            Observer<Resource<List<ProjectView>>> {
                it?.let {
                    handleDataState(it)
                }
            })
        browseViewModel.fetchProjects()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bookmarked -> {
                startActivity(BookmarkedActivity.getStartIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun setupBrowseRecycler(){
        browseAdapter.projectListener = projectListener
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = browseAdapter
    }

    private fun handleDataState(resource: Resource<List<ProjectView>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map {
                    mapper.mapToView(it)
                })
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        progress.visibility = View.GONE
        projects?.let {
            browseAdapter.projects = it
            browseAdapter.notifyDataSetChanged()
            recycler_projects.visibility = View.VISIBLE
        } ?: run {

        }
    }
//
    private val projectListener = object : ProjectListener {
        override fun onBookmarkedProjectClicked(projectId: String) {
            browseViewModel.unBookmarkProject(projectId)
        }

        override fun onProjectClicked(projectId: String) {
            browseViewModel.bookmarkProject(projectId)
        }
    }

}
