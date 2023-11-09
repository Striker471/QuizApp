package com.example.quizapp.feature.presentation.menu_graph.create_question

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.components.AddAnswerDialog
import com.example.quizapp.feature.presentation.components.AddImageCard
import com.example.quizapp.feature.presentation.components.AddStraightAnswerCard
import com.example.quizapp.feature.presentation.components.CenterTopAppBar
import com.example.quizapp.feature.presentation.components.LabelWithTextField
import com.example.quizapp.feature.presentation.components.LazyQuestionRow
import com.example.quizapp.feature.presentation.components.MainActionButton
import com.example.quizapp.feature.presentation.components.SelectableTimerCard
import kotlinx.coroutines.launch


@Composable
fun CreateQuestionScreen(
    navController: NavController,
    viewModel: CreateQuestionViewModel = hiltViewModel()
) {
    val listTime = listOf(5, 10, 15, 20)
    val state by viewModel.currentQuestionState
    val questionsState by viewModel.questionListState
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    if (showDialog) {
        AddAnswerDialog(onDismissRequest = { showDialog = false })
    }

    val cropImageLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            viewModel.onEvent(CreateQuestionEvent.AddImage(result.uriContent))
        }
    }

    val singlePhotoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                it?.let {
                    cropImageLauncher.launch(
                        CropImageContractOptions(
                            uri = it, CropImageOptions(
                                fixAspectRatio = true,
                                aspectRatioX = 4,
                                aspectRatioY = 3,
                                guidelines = CropImageView.Guidelines.ON_TOUCH,
                                outputCompressFormat = Bitmap.CompressFormat.WEBP_LOSSY
                            )
                        )
                    )
                }
            })

    Scaffold(
        topBar = {
            CenterTopAppBar(
                titleText = stringResource(R.string.create_quiz),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
                .background(colorResource(R.color.surface)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            LazyQuestionRow(
                count = questionsState.amountQuestions,
                selectedItem = questionsState.currentQuestion,
                onItemSelected = { viewModel.onEvent(CreateQuestionEvent.SelectedQuestion(it)) },
                onAddClicked = { viewModel.onEvent(CreateQuestionEvent.OnAddedQuestion) },
                lazyListState = lazyListState
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                if (state.image != null) {
                    AsyncImage(
                        model = state.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(320.dp, 240.dp)
                            .clickable {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.FillBounds,
                        alignment = Alignment.Center
                    )
                }
                if (state.image == null) {
                    AddImageCard(onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    SelectableTimerCard(
                        amount = state.selectedTime,
                        onAmountChange = { viewModel.onEvent(CreateQuestionEvent.CheckTime(it)) },
                        options = listTime,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LabelWithTextField(
                    title = stringResource(R.string.add_question),
                    value = state.addQuestion,
                    onValueChange = { viewModel.onEvent(CreateQuestionEvent.EnteredAddQuestion(it)) },
                    placeHolder = stringResource(R.string.enter_your_question),
                    modifier = Modifier.height(74.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    for (i in 0..3) {
                        AddStraightAnswerCard(
                            index = i,
                            correctAnswer = i == state.correctAnswerIndex
                        ) {
                            selectedIndex = it
                            showDialog = true
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                MainActionButton(
                    onClick = { },
                    text = stringResource(R.string.add_question),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


