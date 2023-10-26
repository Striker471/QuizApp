package com.example.quizapp.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.presentation.components.MainActionButton

@Composable
fun StartScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.surface)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.start_icon),
            contentDescription = null,
            modifier = Modifier.size(280.dp, 200.dp)
        )

        Text(
            text = stringResource(R.string.quizmania),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.rubik_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                color = colorResource(R.color.black)
            )
        )

        Card(
            modifier = Modifier
                .height(276.dp)
                .padding(horizontal = 8.dp)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.login_or_sign_up),
                    style = MaterialTheme.typography.headlineMedium,
                    color = colorResource(R.color.black)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.login_or_create),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(R.color.grey_text),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                MainActionButton(
                    onClick = {},
                    text = stringResource(R.string.login)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(0.83f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.neutral_grey),
                        contentColor = colorResource(R.color.purple)
                    ),
                    shape = RoundedCornerShape(20.dp),

                    ) {
                    Text(
                        text = stringResource(R.string.create_an_account),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }

}