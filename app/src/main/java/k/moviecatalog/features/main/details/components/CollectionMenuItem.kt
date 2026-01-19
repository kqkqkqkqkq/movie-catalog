package k.moviecatalog.features.main.details.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CollectionMenuItem(
    title: String,
    onClick: () -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        onClick = onClick,
        colors = MenuDefaults.itemColors(
            textColor = MaterialTheme.colorScheme.onBackground,
        ),
        trailingIcon = trailingIcon,
    )
}