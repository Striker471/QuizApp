package com.example.quizapp.feature.presentation.menu_graph.solve_quiz.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.quizapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerButton(
    index: Int,
    text: String,
    answerState: AnswerState = AnswerState.Unselected,
    onClick: (Int) -> Unit
    ) {
    ElevatedCard(
        onClick = { onClick(index) },
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = when (answerState) {
                AnswerState.Unselected -> colorResource(R.color.grey)
                AnswerState.Correct -> colorResource(R.color.green_card)
                AnswerState.Incorrect -> colorResource(R.color.red)
            }
        ),
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }

}

enum class AnswerState {
    Unselected,
    Correct,
    Incorrect
}