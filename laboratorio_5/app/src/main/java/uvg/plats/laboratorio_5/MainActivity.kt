package uvg.plats.laboratorio_5

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uvg.plats.laboratorio_5.ui.theme.AppTheme
import java.util.Locale

import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeScreen()
                }
            }
        }
    }

}

@Composable
fun HomeScreen() {
    val ctx = LocalContext.current
    val context = remember { ctx }

    val fullName = "Juan Francisco Orozco Mijangos"

    val dateText = "Viernes"
    val subDateText = "25 de abril"

    val restaurantName = "Donde Joselito"
    val restaurantAddress = "Zona 10, Ciudad de Guatemala"
    val openHour = "12:00PM"
    val closeHour = "10:00PM"

    val foodType = "Tipo: Steak House"
    val priceTier = "Precio: QQ"

    val mapsLat = 14.6042
    val mapsLng = -90.5225
    val mapsLabel = restaurantName

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val isDark = androidx.compose.foundation.isSystemInDarkTheme()
        Spacer(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .background(
                    if (isDark) Color.Transparent
                    else MaterialTheme.colorScheme.surface
                )
        )
        Spacer(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .background(Color.White)
        )

        Spacer(Modifier.height(20.dp))

        UpdateBanner(
            onDownload = {
                val pkg = "com.whatsapp"
                val market = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg"))
                val web = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
                try {
                    context.startActivity(market)
                } catch (e: ActivityNotFoundException) {
                    context.startActivity(web)
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dateText.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = subDateText.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            FilledTonalButton(
                onClick = {
                    Toast.makeText(context, "Jornada terminada", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Terminar jornada")
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(restaurantName, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            restaurantAddress,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(6.dp))
                        Text("$openHour $closeHour", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                val uri = Uri.parse("geo:0,0?q=$mapsLat,$mapsLng(${Uri.encode(mapsLabel)})")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                intent.setPackage("com.google.android.apps.maps")
                                context.startActivity(intent)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Directions, contentDescription = "Directions",
                            tint = MaterialTheme.colorScheme.secondary)
                    }
                }

                Spacer(Modifier.height(20.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            Toast.makeText(context, fullName, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp)
                    ) { Text("Iniciar") }

                    Spacer(Modifier.width(16.dp))

                    TextButton(
                        onClick = {
                            val msg = "$foodType\n$priceTier"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Detalles") }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
            )
        }
    }
}

@Composable
fun UpdateBanner(onDownload: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text("↻", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.width(12.dp))

        Text(
            "Actualización disponible",
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium
        )

        TextButton(onClick = onDownload) { Text("Descargar") }
    }
}

