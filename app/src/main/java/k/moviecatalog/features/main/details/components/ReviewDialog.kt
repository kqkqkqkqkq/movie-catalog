package k.moviecatalog.features.main.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import k.moviecatalog.R
import k.moviecatalog.repositories.models.Review
import k.moviecatalog.repositories.models.UserShort
import java.time.LocalDateTime

@Composable
fun ReviewDialog(
    review: Review? = null,
    currentUser: UserShort? = null,
    onSaveReview: (Review) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var rating by remember { mutableIntStateOf(review?.rating ?: 0) }
    var reviewText by remember { mutableStateOf(review?.reviewText ?: "") }
    var isAnonymous by remember { mutableStateOf(review?.isAnonymous ?: false) }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.make_review),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )

                RatingStars(rating) { rating = it }

                TextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    modifier = Modifier
                        .height(128.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.write_review),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.background.copy(0.5f),
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.background.copy(0.5f),
                        focusedTextColor = MaterialTheme.colorScheme.background,
                        unfocusedTextColor = MaterialTheme.colorScheme.background,
                    ),
                    shape = RoundedCornerShape(6.dp),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.anonymous_review),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Checkbox(
                        checked = isAnonymous,
                        onCheckedChange = { isAnonymous = it },
                        modifier = Modifier.size(24.dp),
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(
                        onClick = {
                            currentUser?.let { profile ->
                                val newReview =
                                    createReview(profile, rating, reviewText, isAnonymous)
                                onSaveReview(newReview)
                            }
                            onDismissRequest()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
            }
        }
    }
}

private fun createReview(
    user: UserShort,
    rating: Int,
    reviewText: String,
    isAnonymous: Boolean,
) = Review(
    id = user.userId,
    rating = rating,
    reviewText = reviewText,
    isAnonymous = isAnonymous,
    createDateTime = LocalDateTime.now(),
    author = user,
)
