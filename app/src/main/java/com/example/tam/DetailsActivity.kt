package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.TAMTheme

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("CUSTOM_ID")

        viewModel.getData(id!!)

        setContent {
            TAMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Showcase(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Showcase(viewModel: DetailsViewModel) {
    val uiState by viewModel.immutableCharacterData.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            LoadingView()
        }
        uiState.error != null -> {
            ErrorView()
        }
        else -> {
            uiState.data?.let {
                val character = it[0]
                DetailsView(character.id, character.name, character.house, character.actor, character.species, character.image)
            }
        }
    }
}

@Composable
fun DetailsView(id: String, name: String, house: String, actor: String, species: String, image: String) {
    Column(modifier = Modifier.padding(start = 6.dp, end = 6.dp)) {
        Text(text = house, Modifier.padding(top = 2.dp), fontSize = 8.sp)
        Text(text = actor, Modifier.padding(top = 2.dp),fontSize = 8.sp)
        Text(text = species, Modifier.padding(top = 2.dp), fontSize = 8.sp)
    }
}