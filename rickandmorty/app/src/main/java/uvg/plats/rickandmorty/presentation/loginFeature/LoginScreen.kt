package uvg.plats.rickandmorty.presentation.loginFeature

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uvg.plats.rickandmorty.R

@Composable
fun LoginScreen(
    onStart: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.rickmorty_logo),
                contentDescription = "Rick and Morty logo",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(2.6f)
            )
            Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
                Text("Entrar")
            }
        }
        Text(
            text = "Juan Francisco Orozco Mijangos — 24647",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}
