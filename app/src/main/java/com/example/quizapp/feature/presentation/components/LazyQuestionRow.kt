package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun LazyQuestionRow(
    count: Int,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    lazyListState: LazyListState

) {
//    var count by remember { mutableStateOf(4) } // zaczynamy od 4 elementów
//    var selectedItem by remember { mutableStateOf(1) }


    LazyRow(
        state = lazyListState,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Tworzenie elementów na podstawie zmiennej count
        for (index in 1..count) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (selectedItem == index) Color.Black else Color.Transparent
                        )
                        .clickable {
                            onItemSelected(index)
//                            coroutineScope.launch {
////                                lazyListState.animateScrollToItem(if (index - 5 < 0) 0 else index - 5)
//                            }
                        }
                ) {
                    Text(
                        text = index.toString(),
                        color = if (selectedItem == index) Color.White else Color.Black
                    )
                }
            }
        }
//        item {
//            Box(
//                modifier = Modifier
//                    .clickable {
////                        count++
////                        coroutineScope.launch {
////                            lazyListState.animateScrollToItem(count)
////                            selectedItem = count
////                        }
////                        onAddClicked()
//                    }
//                    .size(36.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = null)
//            }
//        }
    }
}