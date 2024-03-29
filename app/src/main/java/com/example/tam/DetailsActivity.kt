package com.example.tam

import android.os.Bundle
import android.util.Log
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
    val uiState by viewModel.immutableCharacterDetails.observeAsState(UiState())

    when {
        uiState.isLoading -> {
            Log.e("test", "test")
            LoadingView()
        }
        uiState.error != null -> {
            ErrorView()
        }
        else -> {
            uiState.data?.let {
                val character = it[0]
                DetailsView(character.name, character.house, character.actor, character.species, character.image, character.wand, character.ancestry, character.dateOfBirth)
            }
        }
    }
}

@Composable
fun DetailsView(name: String, house: String, actor: String, species: String, image: String, wand: Wand, ancestry: String, dateOfBirth: String?) {
    val birth = dateOfBirth ?: "-"

    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = image.ifBlank { R.drawable.empty_image },
                    contentDescription = name,
                    placeholder = painterResource(id = R.drawable.empty_image),
                    modifier = Modifier.size(200.dp)
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = name, fontSize = 20.sp, color = Color.DarkGray)
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "General information", Modifier.padding(start = 24.dp, top = 24.dp, bottom = 6.dp), fontSize = 18.sp, color = Color.DarkGray)
        }
        Row(Modifier.fillMaxWidth().padding(start = 24.dp)) {
            Column(Modifier.padding(end = 8.dp)) {
                Text(text = "House:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Species:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Birth:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Ancestry:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Actor:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
            }
            Column {
                Text(text = house.ifBlank { "-" }, Modifier.padding(top = 8.dp), fontSize = 16.sp)
                Text(text = species, Modifier.padding(top = 8.dp), fontSize = 16.sp)
                Text(text = birth, Modifier.padding(top = 8.dp),fontSize = 16.sp)
                Text(text = ancestry.ifBlank { "-" }, Modifier.padding(top = 8.dp), fontSize = 16.sp)
                Text(text = actor.ifBlank { "-" }, Modifier.padding(top = 8.dp),fontSize = 16.sp)
            }
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Wand information", Modifier.padding(start = 24.dp, top = 24.dp, bottom = 6.dp), fontSize = 18.sp, color = Color.DarkGray)
        }
        Row(Modifier.fillMaxWidth().padding(start = 24.dp)) {
            Column(Modifier.padding(end = 8.dp)) {
                Text(text = "Wood:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Core:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
                Text(text = "Length:", Modifier.padding(top = 8.dp), fontSize = 16.sp, color = Color.Gray)
            }
            Column {
                Text(text = wand.wood.ifBlank { "-" }, Modifier.padding(top = 8.dp), fontSize = 16.sp)
                Text(text = wand.core.ifBlank { "-" }, Modifier.padding(top = 8.dp),fontSize = 16.sp)
                Text(text = wand.length?.toString() ?: "-", Modifier.padding(top = 8.dp),fontSize = 16.sp)
            }
        }
    }
}