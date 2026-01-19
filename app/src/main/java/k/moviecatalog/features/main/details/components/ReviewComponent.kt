package k.moviecatalog.features.main.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import k.moviecatalog.R
import k.moviecatalog.repositories.models.Profile
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.themes.GrayBackground
import k.moviecatalog.themes.GrayForeground
import k.moviecatalog.themes.RedBackground
import k.moviecatalog.utils.ui.ColorConverter
import k.moviecatalog.utils.ui.DateFormatter

@Composable
fun ReviewComponent(
    review: Review,
    currentUserProfile: Profile,
    onUpdateReview: (Review) -> Unit,
    onDeleteReview: (Review) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val blended = ColorConverter.calculateColor(
        rating = review.rating.toDouble(),
        lowColor = colorResource(R.color.ratingLow).toArgb(),
        midColor = colorResource(R.color.ratingMedium).toArgb(),
        highColor = colorResource(R.color.ratingHigh).toArgb(),
    )
    val ratingColor = Color(blended)
    val authorNickName = review.author
        ?.nickName
        ?.takeUnless { review.isAnonymous }
        ?: stringResource(R.string.anonymous_user)
    val imageUrl = review.author
        ?.avatar
        ?.takeUnless { review.isAnonymous }
        .orEmpty()

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
                        model = imageUrl,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.pic_profile),
                        error = painterResource(R.drawable.pic_profile),
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Column {
                    Text(
                        text = authorNickName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (review.author?.userId == currentUserProfile.id) {
                        Text(
                            text = stringResource(R.string.my_review),
                            style = MaterialTheme.typography.bodySmall,
                            color = GrayBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                        .background(ratingColor),
                ) {
                    Text(
                        text = review.rating.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }
            }
            Text(
                text = review.reviewText.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = DateFormatter.dateToString(review.createDateTime),
                    style = MaterialTheme.typography.bodySmall,
                    color = GrayBackground,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (review.author?.userId == currentUserProfile.id) {
                    IconButton(
                        onClick = {
                            showDialog = true
                        },
                        modifier = Modifier
                            .size(24.dp),
                        shape = CircleShape,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = GrayBackground.copy(0.25f),
                            contentColor = GrayForeground,
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_edit),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            onDeleteReview(review)
                        },
                        modifier = Modifier
                            .size(24.dp),
                        shape = CircleShape,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = RedBackground.copy(0.25f),
                            contentColor = GrayForeground,
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_close),
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                        )
                    }
                }
            }
        }
    }
    if (showDialog) {
        ReviewDialog(
            currentUser = review.author,
            review = review,
            onSaveReview = onUpdateReview,
            onDismissRequest = { showDialog = false }
        )
    }
}
