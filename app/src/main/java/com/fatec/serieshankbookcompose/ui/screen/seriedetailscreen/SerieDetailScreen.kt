package com.fatec.serieshankbookcompose.ui.screen.seriedetailscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.R
import com.skydoves.landscapist.glide.GlideImage
import java.lang.Float.min


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SerieDetailScreen(
    navController: NavController,
    id: Int,
    viewModel: SerieDetailViewModel = hiltViewModel()
) {
    val detailSerie by remember {
        viewModel.detail
    }
    viewModel.getDetail(id)
    if (detailSerie != null) {
        var size by remember { mutableStateOf(IntSize.Zero) }

        val id = detailSerie?.id!!
        val nome = detailSerie?.name!!
        val nomeOriginal = detailSerie?.original_name!!
        val imgSRC = "https://image.tmdb.org/t/p/original/" + detailSerie?.poster_path!!
        val lancamento = "" + detailSerie?.first_air_date!!
        val nota = "" + detailSerie?.vote_average!!
        val status = detailSerie?.in_production!!
        val sobre = detailSerie?.overview!!
        val temp = detailSerie?.seasons!!
        val genero = detailSerie?.genres!!
        val criador = detailSerie?.created_by
        val network = detailSerie?.networks!!

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
                        alpha = min(1f, 1 - (scrollState.value / 2000f))
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
                            .align(CenterVertically),
                            horizontalArrangement = Arrangement.End
                        ){
                            Icon(
                                painter = painterResource(R.drawable.ic_share),
                                contentDescription = "Share",
                                modifier = Modifier.clickable {

                                }
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_star_empty),
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


                    Text(text = sobre,textAlign = TextAlign.Justify,fontSize = 16.sp)

                    Text(text = "Disponivel em:")
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