package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.domain.util.error.ExceptionHandler
import com.example.quizapp.domain.util.error.createDefaultHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideExceptionHandler(): ExceptionHandler = createDefaultHandler()

    @Provides
    @Singleton
    fun provideAuthRepository(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}