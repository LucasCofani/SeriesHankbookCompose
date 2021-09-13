package com.fatec.serieshankbookcompose.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun CustomTopAppBar(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    headerContent: @Composable() () -> Unit,
    drawer: Boolean = true,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (drawer)
                    IconButton(
                        onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                headerContent()
            }
        }
    }
}