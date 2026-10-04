package com.example.composetest.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        modifier = modifier,
    )
}

@Composable
fun HomeContent(
    uiState: HomeState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "Home Screen",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

// ============================================================================
// Previews
// ============================================================================

@Preview(name = "Light Mode - Loading", showBackground = true)
@Preview(
    name = "Dark Mode - Loading",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeContentLoadingPreview() {
    AppTheme {
        HomeContent(
            uiState = HomeState(isLoading = true)
        )
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
        HomeContent(
            uiState = HomeState(isLoading = false)
        )
    }
}

