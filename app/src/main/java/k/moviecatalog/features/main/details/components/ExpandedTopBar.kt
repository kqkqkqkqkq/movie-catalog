package k.moviecatalog.features.main.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import k.moviecatalog.R
import k.moviecatalog.features.main.details.state.COLLAPSED_TOP_BAR_HEIGHT
import k.moviecatalog.features.main.details.state.EXPANDED_TOP_BAR_HEIGHT

@Composable
fun ExpandedTopBar(
    icon: Int,
    name: String,
    imageUrl: String,
    onBackClick: () -> Unit,
    onFavouriteClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.error)
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT - COLLAPSED_TOP_BAR_HEIGHT),
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.pic_profile),
            error = painterResource(R.drawable.pic_profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            modifier = Modifier.padding(16.dp),
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 16.dp,
                    end = 16.dp,
                )
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background.copy(0.5f)),
        ) {
            IconButton(
                onClick = onFavouriteClick,
                modifier = Modifier
                    .align(Alignment.Center)
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
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                )
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background.copy(0.5f)),
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}