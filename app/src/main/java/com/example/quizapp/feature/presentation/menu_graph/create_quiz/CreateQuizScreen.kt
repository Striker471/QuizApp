package com.example.quizapp.feature.presentation.menu_graph.create_quiz

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.quizapp.feature.presentation.components.AddImageCard
import com.example.quizapp.feature.presentation.components.CenterTopAppBar
import com.example.quizapp.feature.presentation.components.MainActionButton
import com.example.quizapp.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CreateQuizScreen(
    navController: NavController,
    viewModel: CreateQuizViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val selectedImage by viewModel.image
    val snackbarHostState = remember { SnackbarHostState() }

    val cropImageLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            viewModel.onEvent(CreateQuizScreenEvent.AddImage(result.uriContent))
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

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is CreateQuizViewModel.UiEvent.CreateQuestionNavigate -> {
                    navController.navigate(
                        Screen.CreateQuestionScreen.route +
                                "?quizId=${it.quizId}"
                    ) {
                        popUpTo(Screen.MenuScreen.route)
                    }
                }

                is CreateQuizViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }



    Scaffold(topBar = {
        CenterTopAppBar(titleText = stringResource(R.string.create_quiz), navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null
                )
            }
        })
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
                .background(colorResource(R.color.surface))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            if (selectedImage != null) {
                AsyncImage(
                    model = selectedImage,
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
            if (selectedImage == null) {
                AddImageCard(onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                })
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onEvent(CreateQuizScreenEvent.EnteredTitle(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = stringResource(R.string.enter_quiz_title))
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.onEvent(CreateQuizScreenEvent.EnteredDescription(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = stringResource(R.string.description))
                    },
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            MainActionButton(
                onClick = { viewModel.onEvent(CreateQuizScreenEvent.CreateQuizOnClick) },
                text = stringResource(R.string.add_question),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}