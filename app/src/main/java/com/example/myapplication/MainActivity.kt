package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.models.Planet
import com.example.myapplication.ui.theme.telaDetalhes

class MainActivity : ComponentActivity() {

    val planetas = listOf<Planet>(
        Planet("Terra", "Nosso planeta padrão.", R.drawable.planeta_terra),
        Planet("Marte", "Um dia iremos lá...", R.drawable.planeta_marte),
        Planet("Plutão", "Eterno inverno...", R.drawable.planeta_plutao)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "telaListagem") {
                        composable("telaListagem") {
                            telaInicial(navController, planetas = planetas)
                        }
                        composable(
                            "telaDetalhesPlaneta/{index}",
                            arguments = listOf(navArgument("index") { type = NavType.StringType })
                        ) {
                            navController.currentBackStackEntry?.arguments?.getString("index")
                                ?.let { it1 ->
                                    telaDetalhes(
                                        navController,
                                        it1
                                    )
                                }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun telaInicial(
    navController: NavHostController,
    planetas: List<Planet>
) {
    LazyColumn {
        itemsIndexed(planetas) { index, item ->
            cardPlaneta(navController, index, item)
        }
    }
}

@Composable
fun cardPlaneta(navController: NavHostController, index: Int, planeta: Planet) {
    Card(elevation = 4.dp,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, color = Color(0x77f5f5f5)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(120.dp)
            .clickable {
                val dest = "telaDetalhesPlaneta/" + index
                navController.navigate(dest)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = painterResource(id = planeta.image), contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp),
                text = planeta.name,
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {}
}