package k.moviecatalog.features.main.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import k.moviecatalog.R
import k.moviecatalog.constants.UiConstants
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.themes.RatingHigh
import k.moviecatalog.themes.RatingLow
import k.moviecatalog.themes.RatingMedium
import k.moviecatalog.themes.RatingUnknown
import java.time.format.DateTimeFormatter

@Composable
fun ReviewComponent(review: Review) {
    //
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                ) {
                    AsyncImage(
                        model = review,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.pic_profile),
                        error = painterResource(R.drawable.pic_profile),
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Text(
                    text = review.author.nickName ?: stringResource(R.string.anonymous_user),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                        .background(getColor(review.rating)),
                ) {
                    Text(
                        text = review.rating.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }
            }
            if (review.reviewText != null) {
                Text(
                    text = review.reviewText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                val dateTimeString = review.createDateTime.format(formatter)
                Text(
                    text = dateTimeString,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.icon_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground.copy(0.2f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            showDialog = true
                        },
                    tint = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.icon_add),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error.copy(0.2f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            TODO("something to do, ya ne znay??????")
                        }
                        .rotate(45f),
                    tint = MaterialTheme.colorScheme.onError,
                )
            }
        }
    }
    if (showDialog) {
        ReviewDialog(
            review = review,
            onSaveReview = { review ->
            },
            onDismissRequest = { showDialog = false }
        )
    }
}

private fun getColor(rating: Int): Color {
    return when {
        rating >= UiConstants.HIGH_RATING -> RatingHigh
        rating > UiConstants.MEDIUM_RATING -> RatingMedium
        rating > UiConstants.LOW_RATING -> RatingLow
        else -> RatingUnknown
    }
}