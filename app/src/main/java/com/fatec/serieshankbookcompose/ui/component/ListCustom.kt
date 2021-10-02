package com.fatec.serieshankbookcompose.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.screen.Screen
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalMaterialApi
@Composable
fun ListCustom(
    navController: NavController,
    id: Int,
    imgSRC: String,
    nome: String,
    nomeOriginal: String,
    media: String,
    lancamento: String,
    nota: String
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(140.dp),
        elevation = 5.dp,
        onClick = {
            if (media == "tv")
                navController.navigate(Screen.SerieDetailScreen.route + "/$id")
            else if (media == "movie")
                navController.navigate(Screen.MovieDetailScreen.route + "/$id")
        }
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.secondary)
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .width(140.dp)
                    .padding(10.dp),
            ) {
                GlideImage(
                    imageModel = imgSRC,
                    contentScale = ContentScale.Crop,
                    contentDescription = nome,
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = nome,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Text(
                            text = nomeOriginal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (media == "tv") "Serie" else "Filme",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = lancamento,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = nota,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}