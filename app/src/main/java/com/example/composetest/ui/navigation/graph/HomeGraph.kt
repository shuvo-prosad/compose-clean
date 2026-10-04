package com.example.composetest.ui.navigation.graph

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.composetest.ui.navigation.keys.HomeKey
import com.example.composetest.ui.screens.home.HomeScreen

fun EntryProviderScope<NavKey>.homeGraph() {
    entry<HomeKey> {
        HomeScreen()
    }
}
