package com.pmdm.casino.ui.features.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.github.pmdmiesbalmis.components.ui.icons.Filled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    comportamientoAnteScroll: TopAppBarScrollBehavior,
    onNavegarAtras: (() -> Unit)? = null,
) = CenterAlignedTopAppBar(
    title = {
        Text("Stock Farmacia", maxLines = 1, overflow = TextOverflow.Ellipsis)
    },
    navigationIcon = {
        onNavegarAtras?.run {
            IconButton(onClick = onNavegarAtras) {
                Icon(painter = Filled.getArrowBackIosIcon(), contentDescription = null)
            }
        }
    },
    scrollBehavior = comportamientoAnteScroll
)
