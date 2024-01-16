package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.tam.repository.model.Wand
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
                DetailsView(character.name, character.house, character.actor, character.species, character.image, character.wand)
            }
        }
    }
}

@Composable
fun DetailsView(name: String, house: String, actor: String, species: String, image: String, wand: Wand) {
    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    placeholder = painterResource(id = R.drawable.harry),
                    modifier = Modifier.size(200.dp)
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = name, Modifier.padding(bottom = 4.dp), fontSize = 20.sp, color = Color.DarkGray)
        }
        Row(Modifier.fillMaxWidth().padding(start = 20.dp, top = 12.dp)) {
            Column {
                Text(text = "House:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Actor:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Species:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
            }
            Column {
                Text(text = house, Modifier.padding(top = 8.dp), fontSize = 16.sp)
                Text(text = actor, Modifier.padding(top = 8.dp),fontSize = 16.sp)
                Text(text = species, Modifier.padding(top = 8.dp), fontSize = 16.sp)
            }
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Wand information", Modifier.padding(start = 20.dp, top = 20.dp, bottom = 6.dp), fontSize = 18.sp, color = Color.DarkGray)
        }
        Row(Modifier.fillMaxWidth().padding(start = 20.dp)) {
            Column {
                Text(text = "Wood:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Core:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Length:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
            }
            Column {
                Text(text = wand.wood, Modifier.padding(top = 8.dp), fontSize = 16.sp)
                Text(text = wand.core, Modifier.padding(top = 8.dp),fontSize = 16.sp)
                Text(text = wand.length.toString(), Modifier.padding(top = 8.dp),fontSize = 16.sp)
            }
        }
    }
}