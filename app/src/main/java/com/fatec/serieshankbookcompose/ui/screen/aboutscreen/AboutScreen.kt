package com.fatec.serieshankbookcompose.ui.screen.aboutscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp,start = 16.dp,end = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Sobre o Aplicativo",
            fontSize = 24.sp
        )
        Text(
            text = "TheMovieDB API",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp,bottom = 16.dp)
        )
        SelectionContainer(){
            Text(
                text = "Todos os dados referentes aos filmes e series vem do site https://www.themoviedb.org/ "+
                        "os criadores deste app não tem vinculo nenhum com o mesmo e se por ventura algum dado ou informação "+
                        "disponibilizada pela API do https://www.themoviedb.org/ estiver errada, por favor entrar em contato "+
                        "com os mesmos. Caso o https://www.themoviedb.org/ mude os termos de uso da API, poderemos "+
                        "indisponibilizar o uso do app até nos adequarmos aos novos termos.",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        Divider()
        Text(
            text = "SeriesHandbook",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp,bottom = 16.dp)
        )
        SelectionContainer(){
            Text(
                text = "As informações referentes aos usuarios (listas de filmes/series, historico, etc) "+
                        "estão sendo gerenciadas pelos desenvolvedores desta aplicação. "+
                        "Caso tenham a necessidade de apagar os dados por favor entrar em contato conosco.",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify
            )
        }
    }

}