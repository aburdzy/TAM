package com.example.tam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tam.repository.model.Character
import com.example.tam.ui.theme.TAMTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData()

        setContent {
            TAMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Showcase(viewModel = viewModel, onClick = { id -> navigateToDetailsActivity((id))})
                }
            }
        }
    }

    private fun navigateToDetailsActivity(id: String) {
        val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra("CUSTOM_ID", id)
        startActivity((detailsIntent))
    }
}

@Composable
fun Showcase(viewModel: MainViewModel, onClick: (String) -> Unit) {
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
                ListView(characters = it, onClick = { id -> onClick.invoke(id)})
            }
        }
    }
}
@Composable
fun ListView(characters: List<Character>, onClick: (String) -> Unit) {
    LazyColumn {
        items(characters) { character ->
            Log.d("Main", "${character.name}, ${character.actor}")
            TileView(id = character.id, name= character.name, house= character.house, actor = character.actor, species= character.species, image = character.image, onClick = { id -> onClick.invoke(id) })
        }
    }
}

@Composable
fun ErrorView() {
    Text(text = "Wystąpił błąd.")
}

@Composable
fun LoadingView() {
    CircularProgressIndicator(modifier = Modifier.width(64.dp))
}

@Composable
fun TileView(id: String, name: String, house: String, actor: String, species: String, image: String, onClick: (String) -> Unit) {
    Box {
        Column(
            modifier = Modifier.clickable { onClick.invoke(id)  }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    placeholder = painterResource(id = R.drawable.harry),
                    modifier = Modifier.size(120.dp)
                )
                Column {
                    Text(text = name, Modifier.padding(top = 6.dp, start = 6.dp, bottom = 4.dp, end = 6.dp), fontSize = 10.sp, color = Color.DarkGray)
                    Row {
                        Column(modifier = Modifier.padding(start = 6.dp, end = 6.dp)) {
                            Text(text = "House:", Modifier.padding(top = 2.dp), fontSize = 8.sp, color = Color.Gray)
                            Text(text = "Actor:", Modifier.padding(top = 2.dp), fontSize = 8.sp, color = Color.Gray)
                            Text(text = "Species:", Modifier.padding(top = 2.dp), fontSize = 8.sp, color = Color.Gray)
                        }
                        Column(modifier = Modifier.padding(start = 6.dp, end = 6.dp)) {
                            Text(text = house, Modifier.padding(top = 2.dp), fontSize = 8.sp)
                            Text(text = actor, Modifier.padding(top = 2.dp),fontSize = 8.sp)
                            Text(text = species, Modifier.padding(top = 2.dp), fontSize = 8.sp)
                        }
                    }

                }
            }
        }
    }
}