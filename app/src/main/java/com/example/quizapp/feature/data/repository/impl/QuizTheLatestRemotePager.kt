package com.example.quizapp.feature.data.repository.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.quizapp.feature.data.mappers.toQuizItem
import com.example.quizapp.feature.data.repository.Constants.COLLECTION_QUIZZES
import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.domain.model.QuizItem
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class QuizTheLatestRemotePager (
    private val firebaseFirestore: FirebaseFirestore
) : PagingSource<DocumentSnapshot, QuizItem>() {

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, QuizItem> {
        return try {
            val documentSnapshot = params.key?.let {
                firebaseFirestore.collection(COLLECTION_QUIZZES)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .startAfter(it)
                    .limit(50)
                    .get().await()

            } ?: firebaseFirestore.collection(COLLECTION_QUIZZES)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(50)
                .get().await()

            val quizList = documentSnapshot.toObjects(QuizDto::class.java)
            val lastVisible = documentSnapshot.documents.lastOrNull()

            val quizItemList = quizList.map {
                it.toQuizItem()
            }

            return LoadResult.Page(
                data = quizItemList,
                prevKey = null,
                nextKey = lastVisible
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, QuizItem>): DocumentSnapshot? {
        return null
    }
}