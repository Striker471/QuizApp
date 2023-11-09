package com.example.quizapp.feature.data.repository

import com.example.quizapp.feature.data.repository.Constants.COLLECTION_USER_PROFILES
import com.example.quizapp.feature.data.repository.Constants.NULL_FIREBASE_USER
import com.example.quizapp.feature.domain.model.LoginData
import com.example.quizapp.feature.domain.model.RegisterRepositoryData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun register(registerRepositoryData: RegisterRepositoryData) {
        firebaseAuth.createUserWithEmailAndPassword(
            registerRepositoryData.email,
            registerRepositoryData.password
        ).await()
        val userNameUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(registerRepositoryData.userName)
            .build()

        val user = firebaseAuth.currentUser ?: throw Exception(NULL_FIREBASE_USER)
        user.updateProfile(userNameUpdate).await()
        createUserProfile(registerRepositoryData.userName)
    }

    suspend fun login(loginData: LoginData) {
        firebaseAuth.signInWithEmailAndPassword(
            loginData.email,
            loginData.password
        ).await()
    }

    fun logOut() {
        firebaseAuth.signOut()
//        firebaseFirestore.clearPersistence()
    }

    fun getSignedInUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    suspend fun createUserProfile(userName: String) {
        val userProfileMap = hashMapOf(
            "userName" to userName,
            "createdAt" to FieldValue.serverTimestamp()
        )

        val uid = firebaseAuth.currentUser?.uid ?: throw Exception(NULL_FIREBASE_USER)
        firebaseFirestore.collection(COLLECTION_USER_PROFILES)
            .document(uid).set(userProfileMap).await()
    }


}

