package com.fatec.serieshankbookcompose.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.fatec.serieshankbookcompose.data.Watched
import com.fatec.serieshankbookcompose.data.WatchedSerie
import com.fatec.serieshankbookcompose.util.APICol
import com.fatec.serieshankbookcompose.util.APIDoc
import com.fatec.serieshankbookcompose.util.APIDocCustom
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.HashMap
import kotlin.system.exitProcess

@Singleton
class FirebaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authUi: AuthUI,
    private val db: FirebaseFirestore,
    @ApplicationContext private val context: Context
) {

    // realiza o logout
    suspend fun logoutFirebase() {
        authUi.signOut(context).await()
        // não podemos sair de imediado se nao o sistema nao reconhece o logout
        Thread.sleep(500)
        exitProcess(0)
    }

    // inicia metodo de login
    fun createLoginIntent(): Intent {
        // define quais provedores iremos aceitar como login
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

    // verifica qual email esta logado no sistema
    fun getUserEmail(): String {
        return try {
            firebaseAuth.currentUser?.email!!
        } catch (e: Exception) {
            "Erro"
        }
    }
    // pega o token da api no firebase
    suspend fun getAPIKeyAsync(): String {
        return try {
            val appSettings = db.collection(APICol).document(APIDoc).get().await()
            appSettings.getString("Key")!!
        } catch (e: java.lang.Exception) {
            "Erro"
        }
    }
    /*
        firebaseAuth.currentUser.uid seria o codigo unico do usuario, ele é usado
        como chave para todas as listas do sistema
     */
    // pega as informações de favoritos de filmes no firebase
    suspend fun getAllFavMov(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("FavMov") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }
    // pega as informações de favoritos de filmes no firebase
    suspend fun getAllFavSerie(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("FavSeries") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }
    // adiciona novos favoritos de series no firebase
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

    // adiciona novos favoritos de filmes no firebase
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

    // pega lista assistir mais tarde de series no firebase
    suspend fun getAllLaterSerie(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("LaterSeries") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }
    // pega lista assistir mais tarde de filmes no firebase
    suspend fun getAllLaterMov(): List<String> {
        return try {
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get().await()
            appSettings.get("LaterMov") as List<String>
        } catch (e: java.lang.Exception) {
            listOf()
        }
    }
    // adiciona novos assistir mais tarde de series no firebase
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

    // adiciona novos assistir mais tarde de filmes no firebase
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

    // função para verificao de primeiro login e criação do banco com a chave do usuario
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
    suspend fun getAllSeriesWatched():List<WatchedSerie>{
        try {
            var saida : MutableList<WatchedSerie> = mutableListOf<WatchedSerie>()
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get()
                    .await()
            var list = appSettings.get("WatchedSeries") as List<HashMap<String,Any>>
            list.forEach {
                val id = it.get("id").toString().toInt()
                val date = it.get("date").toString().toLong()
                val season = it.get("season").toString().toInt()
                saida!!.add(
                    WatchedSerie(id,date,season)
                )
            }
            return saida!!
        } catch (e: Exception) {
            Log.i("olateste", "getAllSeriesWatched: ${e.message}")
            return listOf()
        }
    }

    suspend fun setSeriesWatched(id:Int,date: Date, season : Int){
        try {
            val appSettings = db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!)
            val list = getAllSeriesWatched()
            var inList = false
            var listValue : HashMap<String,Any>? = null
            val insertData = WatchedSerie(
                id,
                date.time,
                season
            )
            list.forEach {
                if (it.equals(insertData)) {
                    inList = true
                    listValue = hashMapOf(
                        "date" to it.date,
                        "id" to it.id,
                        "season" to it.season
                    )
                }
            }
            if (inList)
                appSettings.update("WatchedSeries", FieldValue.arrayRemove(listValue)).await()
            else
                appSettings.update("WatchedSeries", FieldValue.arrayUnion( insertData )).await()
        }catch (e: java.lang.Exception){
            Log.i("olateste", "setLaterSerie: ${e.message}")
        }
    }

    suspend fun getAllMoviesWatched():List<Watched>{
        try {
            var saida : MutableList<Watched> = mutableListOf<Watched>()
            val appSettings =
                db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!).get()
                    .await()
            var list = appSettings.get("WatchedMovie") as List<HashMap<String,Any>>
            list.forEach {
                val id = it.get("id").toString().toInt()
                val date = it.get("date").toString().toLong()
                saida!!.add(
                    Watched(id,date)
                )
            }
            return saida!!
        } catch (e: Exception) {
            Log.i("olateste", "getAllSeriesWatched: ${e.message}")
            return listOf()
        }
    }

    suspend fun setMoviesWatched(id:Int,date: Date){
        try {
            val appSettings = db.collection(APIDocCustom).document(firebaseAuth.currentUser?.uid!!)
            val list = getAllMoviesWatched()
            var inList = false
            var listValue : HashMap<String,Any>? = null
            val insertData = Watched(
                id,
                date.time
            )
            list.forEach {
                if (it.equals(insertData)) {
                    inList = true
                    listValue = hashMapOf(
                        "date" to it.date,
                        "id" to it.id
                    )
                }
            }
            if (inList)
                appSettings.update("WatchedMovie", FieldValue.arrayRemove(listValue)).await()
            else
                appSettings.update("WatchedMovie", FieldValue.arrayUnion( insertData )).await()
        }catch (e: java.lang.Exception){
            Log.i("olateste", "setLaterSerie: ${e.message}")
        }
    }


}