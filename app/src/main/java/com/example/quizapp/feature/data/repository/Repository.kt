package com.example.quizapp.feature.data.repository

import com.example.quizapp.feature.data.repository.Constants.COLLECTION_QUIZZES
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.model.Quiz
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage

) {

    suspend fun createQuiz(createQuizData: CreateQuizData): String {

        val authorId =
            firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)

        val quizId = firebaseFirestore.collection("quizzes").document().id


        val downloadUrl: String? = if (createQuizData.image != null) {
            val imageReference = firebaseStorage.reference
                .child("images/$quizId${createQuizData.image.lastPathSegment}")
            val imageSnapshot = imageReference.putFile(createQuizData.image).await()
            imageSnapshot.storage.downloadUrl.await().toString()
        } else null

        val newQuiz = Quiz(
            authorId = authorId,
            title = createQuizData.title,
            description = createQuizData.description,
            imageUrl = downloadUrl,
            questionCount = 0,
            createdAt = FieldValue.serverTimestamp(),
            views = 0
        )
        firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId).set(newQuiz).await()

        return quizId
    }
//
//        val textId = fireStoreDatabase.collection("Text").document().id
//        val pdfReference = Firebase.storage.reference.child("pdfs/${textId}_${fileName}")
//
//        val pdfSnapshot = pdfReference.putFile(uri).await()
//        val downloadUrl = pdfSnapshot.storage.downloadUrl.await()


}
