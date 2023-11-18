package com.example.quizapp.di

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.quizapp.R
import com.example.quizapp.feature.data.repository.Pagers
import com.example.quizapp.feature.data.repository.impl.QuizMostViewedRemotePager
import com.example.quizapp.feature.data.repository.impl.QuizRemotePager
import com.example.quizapp.feature.data.repository.impl.QuizTheLatestRemotePager
import com.example.quizapp.feature.data.repository.impl.RepositoryImpl
import com.example.quizapp.feature.domain.model.QuizItem
import com.example.quizapp.feature.domain.repository.Repository
import com.example.quizapp.feature.domain.util.error.ExceptionHandler
import com.example.quizapp.feature.domain.util.error.createDefaultHandler
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ): Repository = RepositoryImpl(firebaseAuth, firebaseFirestore, firebaseStorage)

    @Provides
    @Singleton
    fun provideExceptionHandler(): ExceptionHandler = createDefaultHandler()

    @Provides
    @Singleton
    fun provideAuthRepository(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    @Singleton
    @Named(Pagers.QUIZ_PAGER)
    fun provideQuizPager(firebaseFirestore: FirebaseFirestore): Pager<DocumentSnapshot, QuizItem> {
        return Pager(PagingConfig(pageSize = 20)) {
            QuizRemotePager(firebaseFirestore)
        }
    }

    @Provides
    @Singleton
    @Named(Pagers.LATEST_QUIZ_PAGER)
    fun provideLatestQuizPager(firebaseFirestore: FirebaseFirestore): Pager<DocumentSnapshot, QuizItem> {
        return Pager(PagingConfig(pageSize = 20)) {
            QuizTheLatestRemotePager(firebaseFirestore)
        }
    }

    @Provides
    @Singleton
    @Named(Pagers.MOST_VIEWED_QUIZ_PAGER)
    fun provideMostViewedtQuizPager(firebaseFirestore: FirebaseFirestore): Pager<DocumentSnapshot, QuizItem> {
        return Pager(PagingConfig(pageSize = 20)) {
            QuizMostViewedRemotePager(firebaseFirestore)
        }
    }


    @Provides
    @Singleton
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .build()
        return GoogleSignIn.getClient(context, signInOptions)
    }

    @Provides
    @Singleton
    fun buildSignInRequest(context: Context): BeginSignInRequest {
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