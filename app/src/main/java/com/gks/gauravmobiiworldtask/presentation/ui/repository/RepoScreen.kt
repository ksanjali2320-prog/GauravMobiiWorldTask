package com.gks.gauravmobiiworldtask.presentation.ui.repository

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gks.gauravmobiiworldtask.domain.model.UiState


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                itemsIndexed (state.repos) { index,repo ->
                    Card(modifier = Modifier.height(70.dp).fillMaxWidth().clickable{
                        val intent = Intent(context, RepoDetailScreen::class.java)
                        intent.putExtra("repoId", repo.id)
                        context.startActivity(intent)
                    },
                        shape = RoundedCornerShape(10.dp),) {
                        Column {
                            Text(text = (1+index).toString()+" Name of Repo : "+repo.name,
                                modifier = Modifier.padding(5.dp))
                            Text(text = "Repo Star : "+repo.stargazers_count,modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(1.dp).padding(top = 10.dp).background(androidx.compose.ui.graphics.Color.Black).fillMaxWidth())
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text("Error: ${state.message}", color = Color.RED)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Button(onClick = { viewModel.loadMore() }) {
//                        Text("Retry")
//                    }
                }
            }
        }
    }
}