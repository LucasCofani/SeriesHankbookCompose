package com.fatec.serieshankbookcompose.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.fatec.serieshankbookcompose.util.APICol
import com.fatec.serieshankbookcompose.util.APIDoc
import com.fatec.serieshankbookcompose.util.APIDocCustom
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
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

    // Favorite

    suspend fun getAllFavMov(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("FavMov") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }

    suspend fun getAllFavSerie(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("FavSeries") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }

    suspend fun setFavSerie(id: Int) {
        try {
            val appSettings = db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!)

            val list = getAllFavSerie()
            if (list.contains(id.toString()))
                appSettings.update("FavSeries", FieldValue.arrayRemove(id.toString())).await()
            else
                appSettings.update("FavSeries", FieldValue.arrayUnion(id.toString())).await()

        } catch (e: java.lang.Exception) {
            Log.i("olateste", "setFavSerie: ${e.message}")
        }
    }

    suspend fun setFavMov(id: Int) {
        try {
            val appSettings = db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!)
            val list = getAllFavMov()

            if (list.contains(id.toString()))
                appSettings.update("FavMov", FieldValue.arrayRemove(id.toString())).await()
            else
                appSettings.update("FavMov", FieldValue.arrayUnion(id.toString())).await()

        } catch (e: java.lang.Exception) {
            Log.i("olateste", "setFavMov: ${e.message}")
        }
    }

    // Later

    suspend fun getAllLaterSerie(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("LaterSeries") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }

    suspend fun getAllLaterMov(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("LaterMov") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }

    suspend fun setLaterSerie(id: Int) {
        try {
            val appSettings = db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!)
            val list = getAllLaterSerie()

            if (list.contains(id.toString()))
                appSettings.update("LaterSeries", FieldValue.arrayRemove(id.toString())).await()
            else
                appSettings.update("LaterSeries", FieldValue.arrayUnion(id.toString())).await()

        } catch (e: java.lang.Exception) {
            Log.i("olateste", "setLaterSerie: ${e.message}")
        }
    }

    suspend fun setLaterMov(id: Int) {
        try {
            val appSettings = db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!)
            val list = getAllLaterMov()

            if (list.contains(id.toString()))
                appSettings.update("LaterMov", FieldValue.arrayRemove(id.toString())).await()
            else
                appSettings.update("LaterMov", FieldValue.arrayUnion(id.toString())).await()

        } catch (e: java.lang.Exception) {
            Log.i("olateste", "setLaterSerie: ${e.message}")
        }
    }
    suspend fun createUserDoc(){
        try {
            val newUser = hashMapOf(
                "firstLogin" to "1",
            )
            db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).set(newUser).await()
        }catch (e : java.lang.Exception){
            Log.i("olateste", "createUserDoc: ${e.message}")
        }
    }
}