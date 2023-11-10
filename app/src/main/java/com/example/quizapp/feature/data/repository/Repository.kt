package com.example.quizapp.feature.data.repository

import com.example.quizapp.feature.data.repository.Constants.COLLECTION_QUESTIONS
import com.example.quizapp.feature.data.repository.Constants.COLLECTION_QUIZZES
import com.example.quizapp.feature.domain.model.CreateQuestionData
import com.example.quizapp.feature.domain.model.CreateQuestionToRepositoryData
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.model.QuestionReturnData
import com.example.quizapp.feature.domain.model.QuestionUpdateToRepositoryData
import com.example.quizapp.feature.domain.model.QuizData
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

        val quizId = firebaseFirestore.collection(COLLECTION_QUIZZES).document().id


        val downloadUrl: String? = if (createQuizData.image != null) {
            val imageReference = firebaseStorage.reference
                .child("images/$quizId${createQuizData.image.lastPathSegment}")
            val imageSnapshot = imageReference.putFile(createQuizData.image).await()
            imageSnapshot.storage.downloadUrl.await().toString()
        } else null

        val newQuizData = QuizData(
            authorId = authorId,
            title = createQuizData.title,
            description = createQuizData.description,
            imageUrl = downloadUrl,
            questionCount = 0,
            createdAt = FieldValue.serverTimestamp(),
            views = 0,
            quizId = quizId
        )
        firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId).set(newQuizData).await()

        return quizId
    }

    suspend fun addQuestion(
        createQuestionToRepositoryData: CreateQuestionToRepositoryData,
        quizId: String
    ): QuestionReturnData {

        firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)

        val questionID = firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId)
            .collection(COLLECTION_QUESTIONS).document().id

        val downloadUrl: String? = if (createQuestionToRepositoryData.image != null) {
            val imageReference = firebaseStorage.reference
                .child(
                    "images/$quizId${
                        createQuestionToRepositoryData
                            .image.lastPathSegment
                    }"
                )
            val imageSnapshot = imageReference.putFile(createQuestionToRepositoryData.image).await()
            imageSnapshot.storage.downloadUrl.await().toString()
        } else null

        val createQuestionData = CreateQuestionData(
            imageUrl = downloadUrl,
            questionDescription = createQuestionToRepositoryData.questionDescription,
            answers = createQuestionToRepositoryData.answers,
            correctAnswerIndex = createQuestionToRepositoryData.correctAnswerIndex,
            selectedTime = createQuestionToRepositoryData.selectedTime,
            questionId = questionID
        )
        firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId)
            .collection(COLLECTION_QUESTIONS).document(questionID).set(createQuestionData).await()
        return QuestionReturnData(
            questionId = questionID,
            imageUrl = downloadUrl
        )
    }

    suspend fun updateQuestion(
        questionData: QuestionUpdateToRepositoryData,
        quizId: String,
        questionId: String
    ): String? {

        firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)

        val questionRef = firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId)
            .collection(COLLECTION_QUESTIONS).document(questionId)

        val currentData = questionRef.get().await().toObject(CreateQuestionData::class.java)

        val downloadUrl: String? = if (questionData.image != null) {
            val imageReference = firebaseStorage.reference
                .child(
                    "images/$quizId${
                        questionData
                            .image.lastPathSegment
                    }"
                )
            val imageSnapshot = imageReference.putFile(questionData.image).await()
            imageSnapshot.storage.downloadUrl.await().toString()
        } else null

        downloadUrl?.let {
            currentData?.imageUrl?.let { url ->
                val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url)
                storageReference.delete().await()
            }
        }
        val updates = mutableMapOf<String, Any>()

        if (questionData?.questionDescription != null) {
            updates["questionDescription"] = questionData.questionDescription
        }
        if (questionData?.answers != null) {
            updates["answers"] = questionData.answers
        }
        if (questionData?.correctAnswerIndex != null) {
            updates["correctAnswerIndex"] = questionData.correctAnswerIndex
        }
        if (questionData?.selectedTime != null) {
            updates["selectedTime"] = questionData.selectedTime
        }
        if(downloadUrl != null){
            updates["imageUrl"] = downloadUrl
        }
        if (updates.isNotEmpty()) {
            questionRef.update(updates).await()
        }
        return downloadUrl
    }

//
//        val textId = fireStoreDatabase.collection("Text").document().id
//        val pdfReference = Firebase.storage.reference.child("pdfs/${textId}_${fileName}")
//
//        val pdfSnapshot = pdfReference.putFile(uri).await()
//        val downloadUrl = pdfSnapshot.storage.downloadUrl.await()
}
