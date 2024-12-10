package com.example.accescontrolingles.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.accescontrolingles.network.LoginRepoAuth
import com.example.accescontrolingles.network.LoginRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(auth: FirebaseAuth): LoginRepository {
        return LoginRepoAuth(auth)
    }
}