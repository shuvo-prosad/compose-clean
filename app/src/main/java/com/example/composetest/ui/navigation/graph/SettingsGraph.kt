package com.example.composetest.ui.navigation.graph

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.composetest.ui.navigation.keys.SettingsKey
import com.example.composetest.ui.screens.settings.SettingsScreen

fun EntryProviderScope<NavKey>.settingsGraph(modifier: Modifier = Modifier) {
    entry<SettingsKey> {
        SettingsScreen(modifier = modifier)
    }
}
