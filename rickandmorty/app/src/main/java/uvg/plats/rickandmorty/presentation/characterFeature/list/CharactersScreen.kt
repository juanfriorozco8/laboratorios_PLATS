@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package uvg.plats.rickandmorty.presentation.characterFeature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uvg.plats.rickandmorty.presentation.model.CharacterUI

@Composable
fun CharactersScreen(
    characters: List<CharacterUI>,
    onOpenDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Characters") }) }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(characters) { ch ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenDetail(ch.id) }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    AsyncImage(
                        model = ch.image,
                        contentDescription = ch.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(ch.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text("${ch.species} - ${ch.status}", style = MaterialTheme.typography.bodyMedium)
                        Text(ch.gender, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Divider()
            }
        }
    }
}
