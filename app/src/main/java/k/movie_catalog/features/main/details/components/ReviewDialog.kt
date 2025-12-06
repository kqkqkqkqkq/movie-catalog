package k.movie_catalog.features.main.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import k.movie_catalog.R
import k.movie_catalog.repositories.models.Review
import k.movie_catalog.repositories.models.UserShort
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun ReviewDialog(
    review: Review? = null,
    onSaveReview: (Review) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var rating by remember { mutableIntStateOf(review?.rating ?: 0) }
    var reviewText by remember { mutableStateOf(review?.reviewText ?: "") }
    var isAnonymous by remember { mutableStateOf(review?.isAnonymous ?: false) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.make_review),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(10) { index ->
                        val starIndex = index + 1
                        val icon = if (starIndex <= rating) {
                            R.drawable.icon_favorite_filled
                        } else {
                            R.drawable.icon_star
                        }
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "Star $starIndex",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { rating = starIndex }
                        )
                    }
                }

                TextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    modifier = Modifier
                        .height(128.dp)
                        .fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.write_review)) }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.anonymous_review),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Checkbox(
                        checked = isAnonymous,
                        onCheckedChange = { isAnonymous = it }
                    )
                }

                Button(
                    onClick = {
                        onSaveReview(
                            Review(
                                id = UUID.randomUUID(),
                                rating = rating,
                                reviewText = reviewText,
                                isAnonymous = isAnonymous,
                                createDateTime = LocalDateTime.now(),
                                author = UserShort(
                                    userId = UUID.randomUUID(),
                                    nickName = "",
                                    avatar = "",
                                ),
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                    )
                }
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}