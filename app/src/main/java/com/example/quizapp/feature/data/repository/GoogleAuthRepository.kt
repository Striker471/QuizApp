package com.example.quizapp.feature.data.repository

import android.content.Intent
import android.content.IntentSender
import com.example.quizapp.feature.data.repository.Constants.COLLECTION_USER_PROFILES
import com.example.quizapp.feature.data.repository.Constants.NULL_EMAIL_FROM_GOOGLE
import com.example.quizapp.feature.data.repository.Constants.NULL_FIREBASE_USER
import com.example.quizapp.feature.data.repository.Constants.NULL_USERNAME_FROM_GOOGLE
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val oneTapClient: SignInClient,
    private val googleSignInClient: GoogleSignInClient,
    private val signInRequest: BeginSignInRequest,
    private val firebaseFirestore: FirebaseFirestore,
) {

    suspend fun beginOneTapSignIn(): IntentSender? {
        val result = oneTapClient.beginSignIn(
            signInRequest
        ).await()
        return result?.pendingIntent?.intentSender
    }

    suspend fun handleOneTapSignInWithIntent(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        firebaseAuth.signInWithCredential(googleCredentials).await()

        val isNewUser =
            firebaseAuth.currentUser?.metadata?.
                creationTimestamp == firebaseAuth.currentUser?.metadata?.lastSignInTimestamp
        if (isNewUser) {
            val username = credential.displayName ?: throw IllegalArgumentException(
                NULL_USERNAME_FROM_GOOGLE
            )
            createUserProfile(username)
        }
    }

    fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    suspend fun signInWithGoogleAccount(intent: Intent) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            .getResult(ApiException::class.java)
        val googleIdToken = task.idToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        firebaseAuth.signInWithCredential(googleCredentials).await()

        val isNewUser =
            firebaseAuth.currentUser?.metadata?.
            creationTimestamp == firebaseAuth.currentUser?.metadata?.lastSignInTimestamp
        if (isNewUser) {
            val username = task.displayName ?: throw IllegalArgumentException(
                NULL_USERNAME_FROM_GOOGLE
            )
            createUserProfile(username)
        }
    }


    private suspend fun createUserProfile(userName: String) {

        val userProfileMap = hashMapOf(
            "userName" to userName,
            "createdAt" to FieldValue.serverTimestamp(),
        )

        val uid = firebaseAuth.currentUser?.uid ?: throw Exception(NULL_FIREBASE_USER)
        firebaseFirestore.collection(COLLECTION_USER_PROFILES).document(uid).set(userProfileMap)
            .await()
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            firebaseAuth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }


//    fun getSignedInUser(): UserData? = firebaseAuth.currentUser?.run {
//        UserData(
//            userId = uid,
//            username = displayName,
//            profilePictureUrl = photoUrl?.toString()
//        )
//    }

}