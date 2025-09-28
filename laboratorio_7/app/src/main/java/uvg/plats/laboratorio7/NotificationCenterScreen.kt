package uvg.plats.laboratorio7

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCenterScreen(
    modifier: Modifier = Modifier,
    notifications: List<Notification> = remember { generateFakeNotifications() }
) {
    var selectedFilter: NotificationType? by rememberSaveable { mutableStateOf(null) }
    val filtered = if (selectedFilter == null) notifications else notifications.filter { it.type == selectedFilter }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Tipos de notificaciones",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                FilterPill(
                    label = "Informativas",
                    selected = selectedFilter == NotificationType.GENERAL,
                    onClick = {
                        selectedFilter =
                            if (selectedFilter == NotificationType.GENERAL) null else NotificationType.GENERAL
                    }
                )
                FilterPill(
                    label = "Capacitaciones",
                    selected = selectedFilter == NotificationType.NEW_MEETING,
                    onClick = {
                        selectedFilter =
                            if (selectedFilter == NotificationType.NEW_MEETING) null else NotificationType.NEW_MEETING
                    }
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered, key = { it.id }) { n ->
                    NotificationCard(n)
                }
            }
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val selectedBg = if (isSystemInDarkTheme()) Color(0xFF4DB6AC) else Color(0xFFB2EBF2)
    val selectedFg = if (selectedBg.luminance() < 0.5f) Color.White else Color.Black
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (selected) { { Icon(Icons.Filled.Check, contentDescription = null) } } else null,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = selectedBg,
            selectedLabelColor = selectedFg,
            selectedLeadingIconColor = selectedFg,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Composable
private fun NotificationCard(n: Notification) {
    val dark = isSystemInDarkTheme()
    val bg = when (n.type) {
        NotificationType.GENERAL -> if (dark) Color(0xFFFFB74D) else Color(0xFFFFE082)   // amarillo
        NotificationType.NEW_MEETING -> if (dark) Color(0xFF4FC3F7) else Color(0xFF80DEEA) // azul/celeste
    }
    val fg = if (bg.luminance() < 0.5f) Color.White else Color.Black
    val icon = when (n.type) {
        NotificationType.GENERAL -> Icons.Filled.Notifications
        NotificationType.NEW_MEETING -> Icons.Filled.CalendarMonth
    }

    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.large)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                color = bg,
                contentColor = fg,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(text = n.title, style = MaterialTheme.typography.titleMedium)
                Text(text = n.body, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = n.sendAt,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
