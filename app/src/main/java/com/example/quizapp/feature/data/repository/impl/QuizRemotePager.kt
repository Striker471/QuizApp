package com.example.quizapp.feature.data.repository.impl

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.quizapp.feature.data.repository.Constants
import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuizRemotePager(
    private val firebaseFirestore: FirebaseFirestore
) : PagingSource<DocumentSnapshot, QuizDto>() {

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, QuizDto> {
        return try {
            val documentSnapshot = params.key?.let {
                firebaseFirestore.collection(Constants.COLLECTION_QUIZZES)
                    .startAfter(it)
                    .limit(20)
                    .get().await()

            } ?: firebaseFirestore.collection(Constants.COLLECTION_QUIZZES)
                .limit(20)
                .get().await()

            val quizList = documentSnapshot.toObjects(QuizDto::class.java)
            val lastVisible = documentSnapshot.documents.lastOrNull()

            return LoadResult.Page(
                data = quizList,
                prevKey = null,
                nextKey = lastVisible
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, QuizDto>): DocumentSnapshot? {
        return null
    }
}