package com.fatec.serieshankbookcompose.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.fatec.serieshankbookcompose.data.remote.ResultX
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalMaterialApi
@Composable
fun ImageCard(
    item : ResultX,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    mediaInfo: String? = null
){
    val id = item.id
    val media = item.media_type ?: mediaInfo
    val nome = "" + if (media == "movie") item.title else item.name
    val nomeOriginal = "" + if (media == "movie") item.original_title else item.original_name
    val imgSRC = "https://image.tmdb.org/t/p/w185/" + item.poster_path
    val lancamento = "" + if (media == "movie") item.release_date else (if (media == "tv") item.first_air_date else "")
    val nota = "" + item.vote_average

//    var size by remember { mutableStateOf(Size.Zero) }


    Card(
        modifier = modifier
            .height(200.dp)
            .width(130.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 3.dp,
        onClick = { onClick(id) }
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(1f)
//                .onGloballyPositioned { coord ->
//                    size = coord.size.toSize()
//                }
        ){
            GlideImage(
                imageModel = imgSRC,
                contentScale = ContentScale.Crop,
                contentDescription = nome,
                modifier = Modifier.fillMaxSize(1f)
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                ))
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomStart
            ){
//                Log.i("olateste", "ImageCard: ${size.width.dp}")
                Text(
                    modifier = Modifier
                        .width(130.dp)
                        .wrapContentWidth(Alignment.Start),
                    text = nome,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
            }

        }
    }
}