package com.fatec.serieshankbookcompose.ui.screen.seriedetailscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.R
import com.fatec.serieshankbookcompose.ui.showDatePicker
import com.skydoves.landscapist.glide.GlideImage
import java.lang.Float.min
import java.text.SimpleDateFormat
import java.util.*


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
    val openDialog = remember { mutableStateOf(false) }

    val selectedDate = remember { mutableStateOf(Date(1)) }

    val activity = LocalContext.current as AppCompatActivity

    val selectedSeason = remember { mutableStateOf<Int?>(null) }

    val df = SimpleDateFormat("dd/MM/yyyy")
    df.timeZone = TimeZone.getTimeZone("UTC")

    if (detailSerie != null) {
        var size by remember { mutableStateOf(IntSize.Zero) }

        val id = detailSerie?.id!!
        val nome = detailSerie?.name ?: ""
        val nomeOriginal = detailSerie?.original_name ?: ""
        val imgSRC = "https://image.tmdb.org/t/p/original/" + detailSerie?.poster_path ?: ""
        val lancamento = detailSerie?.first_air_date ?: ""
        val nota = "" + detailSerie?.vote_average ?: ""
        val status = detailSerie?.in_production ?: false
        val sobre = detailSerie?.overview ?: ""
        val temp = detailSerie?.seasons!!
        val genero = detailSerie?.genres!!
        val criador = detailSerie?.created_by!!
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                size = it.size
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            text = nome,
                            modifier = Modifier
                                .width(((size.width.dp - 204.dp) / (3f).dp).dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            fontSize = 28.sp,
                            softWrap = true,

                            )
                        Row(
                            modifier = Modifier
                                .width(90.dp)
                                .padding(end = 16.dp)
                                .align(CenterVertically),
                            horizontalArrangement = Arrangement.End
                        ) {
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
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            fontStyle = FontStyle.Italic,
                            text = if (status) "Em produção" else "Finalizado",
                        )
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = lancamento.substring(0, 4),
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Text(text = sobre, textAlign = TextAlign.Justify, fontSize = 16.sp)

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )

                    if (temp != null) {
                        LazyRow {
                            itemsIndexed(items = temp) { _, item ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentSize(Alignment.Center)
                                        .clickable {
                                            selectedSeason.value = item.season_number
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colors.primaryVariant),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item.season_number.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .defaultMinSize(20.dp)
                                        )
                                    }
                                }

                            }
                        }
                    }

                    if (selectedSeason.value != null) {
                        val seasonInfo = temp.firstOrNull {
                            it.season_number == selectedSeason.value
                        }
                        if (seasonInfo != null) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )

                            Text(text = "Temporada ${seasonInfo!!.season_number}")
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        openDialog.value = true
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Marcar temporada como assistida")
                                Icon(
                                    painter = painterResource(R.drawable.ic_eye),
                                    contentDescription = "Watched"
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Text(text = "Episodios: ${seasonInfo!!.episode_count}")
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Text(text = "Inicio: ${seasonInfo!!.air_date}")
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Text(
                                text = seasonInfo!!.overview,
                                textAlign = TextAlign.Justify,
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
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

        if (openDialog.value) {
            selectedDate.value = Date(0)
            AlertDialog(onDismissRequest = { openDialog.value = false },
                title = {
                    Text(text = "Assistido")
                },
                text = {
                    Column {
                        Text(
                            text = "Quando você assistiu?"
                        )
                        if (selectedDate.value == Date(0))
                            Text(
                                text = "Toque aqui para adicionar uma data!",
                                modifier = Modifier.clickable {
                                    showDatePicker(activity) { selDate ->
                                        selectedDate.value = selDate
                                    }
                                }
                            )
                        else
                            Text(text = "${df.format(selectedDate.value)}",
                                modifier = Modifier.clickable {
                                    showDatePicker(activity) { selDate ->
                                        selectedDate.value = selDate
                                    }
                                })
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
                    ) {
                        Text(
                            text = "Salvar"
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(
                            text = "Cancelar"
                        )
                    }
                })
        }
    } else {
        viewModel.getDetail(id)
    }
}