package com.example.quizapp.feature.data.repository.impl

import com.example.quizapp.feature.data.repository.Constants
import com.example.quizapp.feature.data.repository.Constants.COLLECTION_QUESTIONS
import com.example.quizapp.feature.data.repository.Constants.COLLECTION_QUIZZES
import com.example.quizapp.feature.data.repository.Constants.COLLECTION_USER_QUIZZES
import com.example.quizapp.feature.data.repository.Constants.NO_QUIZ_FOUND
import com.example.quizapp.feature.data.repository.Constants.NULL_FIREBASE_USER
import com.example.quizapp.feature.data.repository.dto.QuestionDto
import com.example.quizapp.feature.domain.model.CreateQuestionToRepositoryData
import com.example.quizapp.feature.domain.model.CreateQuizData
import com.example.quizapp.feature.domain.model.QuestionReturnData
import com.example.quizapp.feature.domain.model.QuestionUpdateToRepositoryData
import com.example.quizapp.feature.data.repository.dto.QuizDto
import com.example.quizapp.feature.data.repository.dto.UserQuizResultDto
import com.example.quizapp.feature.domain.model.QuizWithQuestions
import com.example.quizapp.feature.domain.model.SendQuestionStatsData
import com.example.quizapp.feature.domain.repository.Repository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.time.LocalDateTime
import javax.inject.Inject

class RepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : Repository {

    override suspend fun createQuiz(createQuizData: CreateQuizData): String {
        val authorId =
            firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)
        firebaseAuth.currentUser?.displayName

        val quizId = firebaseFirestore.collection(COLLECTION_QUIZZES).document().id


        val downloadUrl: String? = if (createQuizData.image != null) {
            val imageReference = firebaseStorage.reference
                .child("images/$quizId${createQuizData.image.lastPathSegment}")
            val imageSnapshot = imageReference.putFile(createQuizData.image).await()
            imageSnapshot.storage.downloadUrl.await().toString()
        } else null

        val userName = firebaseAuth.currentUser?.displayName ?: ""

        val newQuizData = hashMapOf(
            "authorId" to authorId,
            "title" to createQuizData.title,
            "description" to createQuizData.description,
            "imageUrl" to downloadUrl,
            "questionCount" to 0,
            "createdAt" to FieldValue.serverTimestamp(),
            "views" to 0,
            "quizId" to quizId,
            "userName" to userName
        )
        firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId).set(newQuizData).await()

        return quizId
    }

    override suspend fun addQuestion(
        createQuestionToRepositoryData: CreateQuestionToRepositoryData,
        quizId: String
    ): QuestionReturnData {

        firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)

        val questionID = firebaseFirestore.collection(COLLECTION_QUIZZES).document(quizId)
            .collection(COLLECTION_QUESTIONS).document().id

        val downloadUrl: String? = if (createQuestionToRepositoryData.image != null) {
            val imageReference = firebaseStorage.reference
                .child(
                    "images/$questionID${
                        createQuestionToRepositoryData
                            .image.lastPathSegment
                    }"
                )
            val imageSnapshot = imageReference.putFile(createQuestionToRepositoryData.image).await()
            imageSnapshot.storage.downloadUrl.await().toString()
        } else null

        val createQuestionData = QuestionDto(
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

    override suspend fun updateQuestion(
        questionData: QuestionUpdateToRepositoryData,
        quizId: String,
        questionId: String
    ): String? {

        firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)

        val questionRef = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .document(quizId)
            .collection(COLLECTION_QUESTIONS)
            .document(questionId)

        val currentData = questionRef.get().await().toObject(QuestionDto::class.java)

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
        if (downloadUrl != null) {
            updates["imageUrl"] = downloadUrl
        }
        if (updates.isNotEmpty()) {
            questionRef.update(updates).await()
        }
        return downloadUrl
    }

    override suspend fun deleteQuestion(quizId: String, questionId: String) {


        val questionDocRef = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .document(quizId)
            .collection(COLLECTION_QUESTIONS)
            .document(questionId)

        val documentSnapshot = questionDocRef.get().await()
        val question = documentSnapshot.toObject(QuestionDto::class.java)
        val resourceUrl = question?.imageUrl

        questionDocRef.delete().await()

        resourceUrl?.let {
            val storageRef = firebaseStorage.getReferenceFromUrl(it)
            storageRef.delete().await()
        }
    }

    override suspend fun getTheLatestQuizzes(): List<QuizDto> {

        val documentSnapshot = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(10)
            .get().await()

        return documentSnapshot.toObjects(QuizDto::class.java)
    }

    override suspend fun getMostViewedQuizzes(): List<QuizDto> {
        val documentSnapshot = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .orderBy("views", Query.Direction.DESCENDING)
            .limit(10)
            .get().await()

        return documentSnapshot.toObjects(QuizDto::class.java)
    }

    override suspend fun getQuiz(quizId: String): QuizDto {
        val documentSnapshot = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .document(quizId).get().await()

        return documentSnapshot.toObject(QuizDto::class.java)
            ?: throw NoSuchElementException(NO_QUIZ_FOUND + quizId)
    }

    override suspend fun getFirstPageOfQuizzes(): List<QuizDto> {
        val documentSnapshot = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .limit(20)
            .get().await()
        return documentSnapshot.toObjects(QuizDto::class.java)
    }

    override suspend fun getAnotherPageOfQuizzes(document: DocumentSnapshot): List<QuizDto> {
        val documentSnapshot = document.let {
            firebaseFirestore
                .collection(COLLECTION_QUIZZES)
                .startAfter(it)
                .limit(20)
                .get().await()
        }
        return documentSnapshot.toObjects(QuizDto::class.java)
    }

    override suspend fun getQuestionsOfQuiz(quizId: String): QuizWithQuestions {
        val quizSnapshot = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .document(quizId)
            .get().await()
        val quizDto = quizSnapshot.toObject(QuizDto::class.java)
            ?: throw NoSuchElementException(NO_QUIZ_FOUND + quizId)

        val questionSnapshot = quizSnapshot.reference
            .collection(COLLECTION_QUESTIONS)
            .get().await()

        val questionDtoList = questionSnapshot.toObjects(QuestionDto::class.java)

        return QuizWithQuestions(quizDto, questionDtoList)
    }

    override suspend fun sendStatsOfQuestionToDb(data: SendQuestionStatsData) {
        val questionRef = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .document(data.quizId)
            .collection(COLLECTION_QUESTIONS)
            .document(data.questionId)

        val updates = mutableMapOf<String, Any>()

        updates["attempts"] = FieldValue.increment(1)

        if (data.isSelectCorrectQuestion) {
            updates["correctAttempts"] = FieldValue.increment(1)
        }
        withTimeout(10000) {
            questionRef.update(updates).await()
        }
    }

    override suspend fun completeQuiz(quizId: String, score: Int) {
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception(Constants.NULL_FIREBASE_USER)

        val userQuizResultData = hashMapOf(
            "userId" to uid,
            "quizId" to quizId,
            "score" to score,
            "completedAt" to FieldValue.serverTimestamp()
        )

        withTimeout(10000) {
            incrementQuizViews(quizId)
            firebaseFirestore
                .collection(COLLECTION_USER_QUIZZES)
                .add(userQuizResultData).await()
        }
    }

    override suspend fun incrementQuizViews(quizId: String) {

        val questionRef = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .document(quizId)

        val updates = mutableMapOf<String, Any>()

        updates["views"] = FieldValue.increment(1)

        questionRef.update(updates).await()
    }

    override suspend fun getMyQuizzes(): List<QuizDto> {
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception(NULL_FIREBASE_USER)

        val documentSnapshot = firebaseFirestore
            .collection(COLLECTION_QUIZZES)
            .whereEqualTo("authorId", uid)
            .get().await()

        return documentSnapshot.toObjects(QuizDto::class.java)
    }
}
