package com.fatec.serieshankbookcompose.ui.screen.moviedetailscreen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.R
import com.skydoves.landscapist.glide.GlideImage
import java.lang.Float

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    id: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val detailMovie by remember {
        viewModel.detail
    }

    val favoriteMovie by remember {
        viewModel.favorite
    }

    val activity = LocalContext.current as AppCompatActivity

    viewModel.getDetail(id)
    if (detailMovie != null) {
        var size by remember { mutableStateOf(IntSize.Zero) }

        val id = detailMovie?.id!!
        val nome = detailMovie?.title!!
        val nomeOriginal = detailMovie?.original_title!!
        val imgSRC = "https://image.tmdb.org/t/p/original/" + detailMovie?.poster_path!!
        val lancamento = "" + detailMovie?.release_date!!
        val nota = "" + detailMovie?.vote_average!!
        val status = detailMovie?.status!!
        val sobre = detailMovie?.overview!!
        val genero = detailMovie?.genres!!
        val network = detailMovie?.production_companies!!

        val scrollState = rememberScrollState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .verticalScroll(scrollState)
        ) {
            GlideImage(
                imageModel = imgSRC,
                contentScale = ContentScale.FillHeight,
                contentDescription = nome,
                modifier = Modifier
                    .height(600.dp)
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = Float.min(1f, 1 - (scrollState.value / 2000f))
                        translationY = -scrollState.value * 0.1f
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 500.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .background(MaterialTheme.colors.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            size = it.size
//                            Log.i("olateste", "${size.width.dp} ")
                        },
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(text = nome,
                            modifier= Modifier
                                .width(( (size.width.dp-204.dp) /(3f).dp).dp)
                            //.background(Color.White)
//                                .onGloballyPositioned{
//                                    Log.i("olateste", "Title: ${it.size.width.dp} ")
//                                }
                            ,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            fontSize = 28.sp,
                            softWrap = true,

                            )
                        Row(modifier = Modifier
                            .width(90.dp)
                            .padding(end = 16.dp)
//                            .background(Color.Black)
                            .align(Alignment.CenterVertically),
                            horizontalArrangement = Arrangement.End
                        ){
                            Icon(
                                painter = painterResource(R.drawable.ic_share),
                                contentDescription = "Share",
                                modifier = Modifier.clickable {
                                    val type = "text/plain"
                                    val subject = "Compartilhar"
                                    val extraText = "Veja o filme $nome"
                                    val shareWith = "Compartilhar com..."

                                    val intent = Intent(Intent.ACTION_SEND)
                                    intent.type = type
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                                    intent.putExtra(Intent.EXTRA_TEXT, extraText)

                                    ContextCompat.startActivity(
                                        activity,
                                        Intent.createChooser(intent, shareWith),
                                        null
                                    )
                                }
                            )
                            Icon(
                                painter = if (favoriteMovie) painterResource(R.drawable.ic_baseline_favorite) else painterResource(R.drawable.ic_baseline_favorite_border),
                                contentDescription = "Favorite",
                                modifier = Modifier.clickable {
                                    viewModel.setFavorite(id)
                                }
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_later),
                                contentDescription = "Later",
                                modifier = Modifier.clickable {
                                    viewModel.setLater(id)
                                }
                            )
                        }
                    }

                    Text(text = sobre, textAlign = TextAlign.Justify, fontSize = 16.sp)

                    Text(text = "Produzido por:")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        network.forEach {
                            GlideImage(
                                imageModel = "https://image.tmdb.org/t/p/w185/" + it.logo_path,
                                contentScale = ContentScale.Fit,
                                contentDescription = it.name,
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp)
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}