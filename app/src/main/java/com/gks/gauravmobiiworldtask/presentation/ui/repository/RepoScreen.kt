package com.gks.gauravmobiiworldtask.presentation.ui.repository

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.gks.gauravmobiiworldtask.presentation.viewmodel.RepositoryViewModel
import com.gks.gauravmobiiworldtask.presentation.ui.theme.GauravMobiiWorldTaskTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gks.gauravmobiiworldtask.domain.model.UiState


@AndroidEntryPoint
class RepoScreen : ComponentActivity() {

    private val vm: RepositoryViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        Log.e("onResume clal","refresh call")
        vm.refreshVisibleRepos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GauravMobiiWorldTaskTheme {
                val context = LocalContext.current
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RepositoryList(
                        modifier = Modifier.padding(innerPadding),
                        context = context
                    )
                }
            }
        }
    }
}

@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    viewModel: RepositoryViewModel = hiltViewModel(),
    context: Context
) {


    when (val state = viewModel.uiState) {
        is UiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            LazyColumn(modifier = modifier.fillMaxSize().padding(10.dp)) {
                itemsIndexed(state.repos) { index, repo ->
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .clickable {
                                val intent = Intent(context, RepoDetailScreen::class.java)
                                intent.putExtra("repoId", repo.id)
                                context.startActivity(intent)
                            },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column {
                            Text(
                                text = "${index + 1} Name of Repo : ${repo.name}",
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = "Repo Star : ${repo.stargazers_count}",
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val text = if (repo.addToBookMark) "Bookmarked" else "Add to Bookmark"
                                Text(text = text, modifier = Modifier.padding(horizontal = 5.dp))
                                Checkbox(
                                    checked = repo.addToBookMark,
                                    onCheckedChange = {
                                        viewModel.onBookmarkToggleMain(repo.id, it)
                                    }
                                )
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .padding(top = 10.dp)
                            .background(androidx.compose.ui.graphics.Color.Black)
                            .fillMaxWidth()
                    )
                }

                item {
                    Button(
                        onClick = { viewModel.loadMore() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text("Load More")
                    }
                }
            }
        }

        is UiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading data", color = androidx.compose.ui.graphics.Color.Red)
            }
        }
    }
}