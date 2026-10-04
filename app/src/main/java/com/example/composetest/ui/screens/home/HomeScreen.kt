package com.example.composetest.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.composetest.domain.entity.PhotoEntity
import com.example.composetest.ui.screens.home.viewModel.HomeState
import com.example.composetest.ui.screens.home.viewModel.HomeViewModel
import com.example.composetest.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onRetry = viewModel::loadPhotos,
        modifier = modifier,
    )
}

@Composable
fun HomeContent(
    uiState: HomeState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        when {
            uiState.isLoading -> { /* unchanged */
            }

            uiState.errorMessage != null -> { /* unchanged */
            }

            else -> PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize(),
            ) {
                PhotoGrid(photos = uiState.photos)
            }
        }
    }
}

@Composable
private fun PhotoGrid(
    photos: List<PhotoEntity>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = photos, key = { it.id }) { photo ->
            PhotoItem(photo)
        }
    }
}

@Composable
private fun PhotoItem(
    photo: PhotoEntity,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            AsyncImage(
                model = photo.thumbnailUrl,
                contentDescription = photo.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Text(
                text = photo.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

// ============================================================================
// Previews
// ============================================================================

private val previewPhotos = List(12) {
    PhotoEntity(
        albumId = 1,
        id = it.toLong(),
        title = "Sample photo title $it",
        url = "",
        thumbnailUrl = "",
    )
}

@Preview(name = "Light Mode - Loading", showBackground = true)
@Preview(
    name = "Dark Mode - Loading",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeContentLoadingPreview() {
    AppTheme {
        HomeContent(uiState = HomeState(isLoading = true))
    }
}

@Preview(name = "Light Mode - Content", showBackground = true)
@Preview(
    name = "Dark Mode - Content",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeContentPreview() {
    AppTheme {
        HomeContent(uiState = HomeState(photos = previewPhotos))
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
private fun HomeContentErrorPreview() {
    AppTheme {
        HomeContent(uiState = HomeState(errorMessage = "No internet connection"))
    }
}