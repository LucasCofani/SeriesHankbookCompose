package com.fatec.serieshankbookcompose.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.fatec.serieshankbookcompose.util.APICol
import com.fatec.serieshankbookcompose.util.APIDoc
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.exitProcess

@Singleton
class FirebaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authUi: AuthUI,
    private val db: FirebaseFirestore,
    @ApplicationContext private val context: Context
) {
    private var key = ""

    suspend fun logoutFirebase() {
        authUi.signOut(context).await()
        Thread.sleep(500)
        exitProcess(0)
    }


    fun createLoginIntent(): Intent {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.GitHubBuilder().build(),
            //AuthUI.IdpConfig.TwitterBuilder().build(),
        )

        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false, false)
            .setAlwaysShowSignInMethodScreen(true)
            .build()
    }

    fun getUserEmail(): String {
        return try {
            firebaseAuth.currentUser?.email!!
        } catch (e: Exception) {
            "Erro"
        }
    }

    suspend fun getAPIKeyAsync(): String {
        return try {
            val appSettings = db.collection(APICol).document(APIDoc).get().await()
            appSettings.getString("Key")!!
        } catch (e: java.lang.Exception) {
            "Erro"
        }
    }
}