package uvg.plats.laboratorio_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

private enum class Movimiento { SUMA, RESTA }
private data class Registro(val valor: Int, val tipo: Movimiento)

// Colores clave
private val VerdeAzulado = Color(0xFF009688) // teal
private val RojoHist = Color(0xFFB71C1C)
private val BlancoFondo = Color(0xFFFFFFFF)
private val NegroTexto = Color(0xFF000000)

@Composable
fun App() {
    val scheme = lightColorScheme(
        background = BlancoFondo,
        surface = BlancoFondo,
        onBackground = NegroTexto,
        onSurface = NegroTexto
    )
    MaterialTheme(colorScheme = scheme) {
        Surface(Modifier.fillMaxSize()) {
            Pantalla("Juan Francisco Orozco")
        }
    }
}

@Composable
private fun Pantalla(nombre: String) {
    var numero by remember { mutableStateOf(0) }
    var incs by remember { mutableStateOf(0) }
    var decs by remember { mutableStateOf(0) }
    var maxVal by remember { mutableStateOf(0) }
    var minVal by remember { mutableStateOf(0) }
    val registros = remember { mutableStateListOf<Registro>() }

    fun aplicarCambio(nuevo: Int, tipo: Movimiento) {
        numero = nuevo
        if (registros.isEmpty()) {
            maxVal = nuevo
            minVal = nuevo
        } else {
            maxVal = max(maxVal, nuevo)
            minVal = min(minVal, nuevo)
        }
        if (tipo == Movimiento.SUMA) incs++ else decs++
        registros.add(Registro(nuevo, tipo))
    }

    fun resetear() {
        numero = 0
        incs = 0
        decs = 0
        maxVal = 0
        minVal = 0
        registros.clear()
    }

    Scaffold(
        bottomBar = { BotonInferior("Reiniciar") { resetear() } }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
        ) {
            Titulo(nombre)
            ContadorPrincipal(
                numero,
                { aplicarCambio(numero - 1, Movimiento.RESTA) },
                { aplicarCambio(numero + 1, Movimiento.SUMA) }
            )
            Divider(Modifier.padding(vertical = 8.dp))
            Estadisticas(
                incs,
                decs,
                if (registros.isEmpty()) 0 else maxVal,
                if (registros.isEmpty()) 0 else minVal,
                incs + decs
            )
            Spacer(Modifier.height(8.dp))
            Subtitulo("Historial:")
            Cuadricula(registros)
            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
private fun Titulo(texto: String) {
    Text(
        texto,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        textAlign = TextAlign.Center,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = NegroTexto
    )
}

@Composable
private fun ContadorPrincipal(valor: Int, menos: () -> Unit, mas: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BotonCircular("−") { menos() }
        Text(valor.toString(), fontSize = 64.sp, fontWeight = FontWeight.Bold, color = NegroTexto)
        BotonCircular("+") { mas() }
    }
}

@Composable
private fun BotonCircular(txt: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(64.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(containerColor = VerdeAzulado),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(txt, fontSize = 28.sp, color = Color.White)
    }
}

@Composable
private fun Subtitulo(txt: String) {
    Text(
        txt,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        color = NegroTexto,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun Estadisticas(incs: Int, decs: Int, max: Int, min: Int, cambios: Int) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Fila("Total incrementos", incs)
        Fila("Total decrementos", decs)
        Fila("Valor máximo", max)
        Fila("Valor mínimo", min)
        Fila("Total cambios", cambios)
    }
}

@Composable
private fun Fila(et: String, valr: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(et, fontSize = 20.sp, color = NegroTexto)
        Text(valr.toString(), fontSize = 20.sp, fontWeight = FontWeight.Medium, color = NegroTexto)
    }
}

@Composable
private fun Cuadricula(lista: List<Registro>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lista) { reg ->
            Box(
                Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(if (reg.tipo == Movimiento.SUMA) VerdeAzulado else RojoHist),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    reg.valor.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun BotonInferior(txt: String, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(16.dp)) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(containerColor = VerdeAzulado)
        ) {
            Text(txt, fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VistaPreview() {
    App()
}
