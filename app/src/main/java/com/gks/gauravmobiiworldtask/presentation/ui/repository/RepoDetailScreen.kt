package com.gks.gauravmobiiworldtask.presentation.ui.repository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gks.gauravmobiiworldtask.presentation.ui.theme.GauravMobiiWorldTaskTheme
import com.gks.gauravmobiiworldtask.presentation.viewmodel.RepositoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repoId = intent.getIntExtra("repoId", -1)

        setContent {
            GauravMobiiWorldTaskTheme {
                RepoDetailScreenContent(repoId = repoId)
            }
        }
    }
}

@Composable
fun RepoDetailScreenContent(
    repoId: Int,
    viewModel: RepositoryViewModel = hiltViewModel()
) {

    LaunchedEffect(repoId) {
        viewModel.loadRepositoryDetail(repoId)
    }

    val user = viewModel.selectedRepo

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (user != null) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Name of Repo: ${user.name}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black).padding(5.dp))
                    Text("Stars: ${user.stargazers_count}")
                    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black).padding(5.dp))
                    Text("Description: ${user.description}")
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
