package com.example.quizapp.feature_repository.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.quizapp.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val googleSignInClient: GoogleSignInClient
) {

    suspend fun beginOneTapSignIn(): IntentSender? {
        val result = oneTapClient.beginSignIn(
            buildSignInRequest()
        ).await()
        return result?.pendingIntent?.intentSender
    }

    suspend fun handleOneTapSignInWithIntent(intent: Intent) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        firebaseAuth.signInWithCredential(googleCredentials).await()
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

//    fun getSignedInUser(): UserData? = auth.currentUser?.run {
//        UserData(
//            userId = uid,
//            username = displayName,
//            profilePictureUrl = photoUrl?.toString()
//        )
//    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }


}