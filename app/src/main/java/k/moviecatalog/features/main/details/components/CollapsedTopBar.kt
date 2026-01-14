package k.moviecatalog.features.main.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import k.moviecatalog.R
import k.moviecatalog.features.main.details.state.COLLAPSED_TOP_BAR_HEIGHT

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsedTopBar(
    icon: Int,
    name: String,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(COLLAPSED_TOP_BAR_HEIGHT)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(24.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_back),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = onFavouriteClick,
            modifier = Modifier
                .size(24.dp),
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}