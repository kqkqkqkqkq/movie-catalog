package k.moviecatalog.features.main.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import k.moviecatalog.R

@Composable
fun RatingStars(
    rating: Int,
    onRatingChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(10) { index ->
            val starIndex = index + 1
            val icon =
                if (starIndex <= rating) R.drawable.icon_favorite_filled else R.drawable.icon_star
            val starColor =
                if (starIndex <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            Icon(
                painter = painterResource(icon),
                contentDescription = "Star",
                tint = starColor,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onRatingChange(starIndex) },
            )
        }
    }
}