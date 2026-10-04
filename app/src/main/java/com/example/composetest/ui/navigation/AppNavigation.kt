package com.example.composetest.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.composetest.ui.navigation.graph.homeGraph
import com.example.composetest.ui.navigation.graph.settingsGraph
import com.example.composetest.ui.navigation.keys.HomeKey
import com.example.composetest.ui.navigation.keys.SettingsKey

data class TopLevelDestination<T : NavKey>(
    val key: T,
    val label: String,
    val icon: ImageVector,
)

val topLevelItems = listOf(
    TopLevelDestination(
        key = HomeKey,
        label = "Home",
        icon = Icons.Default.Home,
    ),
    TopLevelDestination(
        key = SettingsKey,
        label = "Settings",
        icon = Icons.Default.Settings,
    ),
)

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(HomeKey)
    val current = backStack.lastOrNull()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                topLevelItems.forEach { item ->
                    NavigationBarItem(
                        selected = current == item.key,
                        onClick = {
                            when {
                                current == item.key -> Unit
                                // Home is the base of the stack: pop back to it
                                item.key == HomeKey -> while (backStack.size > 1) backStack.removeLastOrNull()
                                else -> backStack.add(item.key)
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                homeGraph(modifier = Modifier.padding(innerPadding))
                settingsGraph(modifier = Modifier.padding(innerPadding))
            },
        )
    }
}
