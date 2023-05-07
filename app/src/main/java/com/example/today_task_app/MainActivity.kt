package com.example.tasktodayapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState){
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    var scope  =  rememberCoroutineScope()
    var tabIndex = remember{ mutableStateOf(0) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar =  {
            TopAppBar(
                title = { Text(text = "TaskAppBar")},
                navigationIcon = {
                    IconButton(onClick = {
                       scope.launch {
                        scaffoldState.drawerState.open()
                    } }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer Menu")
                    }
                }
            ) },
        drawerBackgroundColor = Color.Red,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Box(modifier = Modifier
                .background(Color.Magenta)
                .height(16.dp)){
                Text(text = "Opções: ")
            }
            Column() {
                Text(text = "Opção de Menu - 1")
                Text(text = "Opção de Menu - 2")
                Text(text = "Opção de Menu - 3")
            }
        },
        content = {
                paddingValues -> Log.i("paddingValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(Color.Yellow)
                    .fillMaxSize()
            ) {
                MySearchField(modificador = Modifier.fillMaxWidth())
                val calendar = Calendar.getInstance()
                listOf<Tarefa>(Tarefa("Estudar Prova de Cálculo", "Capítulo 1 do Livro ABCD", Date(), Date(), 0.0))

                val tProvaDeCalculo = Tarefa(
                    "Estudar Prova de Cálculo",
                    "Capítulo 1 do Livro ABCD",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val tProvaDeKotlin = Tarefa(
                    "Estudar Prova de Kotlin",
                    "Capítulo 1 do Livri EFGH",
                    Date(),
                    Date(),
                    status = 0.0
                )

                val minhaListaDeTarefas = listOf<Tarefa>(tProvaDeCalculo,tProvaDeKotlin)

                MyTaskWidgetList(minhaListaDeTarefas)

            }
        },
        bottomBar = {
            BottomAppBar(
                content = { Text(text = "Donick")}
            )
        },
        isFloatingActionButtonDocked = false,
        floatingActionButton = { ExtendedFloatingActionButton(
            icon = {
                   Icon(imageVector = Icons.Default.AddCircle,
                       contentDescription = "ADD Task")

            },
            text = { Text(text = "Add")},
            onClick = { /*TODO*/ })}
    )
}

@Composable
fun MyTaskWidgetList(ListaDeTarefas: List<Tarefa>){
    ListaDeTarefas.forEach(action = {Log.i("donick", "$it.nome")})
}

@Composable
fun MySearchField(modificador: Modifier){
    TextField(value = "",
        onValueChange = {},
        modifier = modificador,
        placeholder = { Text(text = "Pesquisar Tarefas")},
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search Icon")
        })
}

@Composable
fun MyTaskWidget(modificador: Modifier,
                 tarefaASerMostrada: Tarefa
){
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())
    Row(modifier = modificador) {
        Column() {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icons of a pendent task"
            )
            Text(
                text = dateFormatter.format(dateFormatter.format(tarefaASerMostrada.pzoFinal)),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp
            )
        }
        Column(
            modifier = modificador
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)
        ) {
            Text(
                text = tarefaASerMostrada.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = tarefaASerMostrada.detalhes,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
}