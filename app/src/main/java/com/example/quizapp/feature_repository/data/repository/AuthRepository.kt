package com.example.quizapp.feature_repository.data.repository

import com.example.quizapp.domain.model.LoginData
import com.example.quizapp.domain.model.RegisterData
import com.example.quizapp.domain.model.RegisterRepositoryData
import com.example.quizapp.domain.util.error.EmptyUserException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

//            val uid = firebaseAuth.currentUser?.uid ?: throw EmptyUserException()
//
//            firebaseFirestore.collection("Users")
//                .document(uid)
//                .set(registerRepositoryData.userName).await()
    }

    suspend fun login(loginData: LoginData) {
        firebaseAuth.signInWithEmailAndPassword(
            loginData.email,
            loginData.password
        ).await()
    }


    fun logOut() {
        firebaseAuth.signOut()
        firebaseFirestore.clearPersistence()
    }

    fun getSignedInUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }
}