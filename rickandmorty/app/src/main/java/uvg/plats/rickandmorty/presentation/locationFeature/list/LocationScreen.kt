package uvg.plats.rickandmorty.presentation.locationFeature.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uvg.plats.rickandmorty.presentation.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locations: List<Location>,
    onOpenDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Locations") }) }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(locations) { loc ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onOpenDetail(loc.id) }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(loc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(loc.type, style = MaterialTheme.typography.bodyMedium)
                }
                Divider()
            }
        }
    }
}
