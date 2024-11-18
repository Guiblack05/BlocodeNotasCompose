package com.example.blocodenotascompose

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ContentView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blocodenotascompose.datastore.StoreAnotacao
import com.example.blocodenotascompose.ui.theme.BlocoDeNotasComposeTheme
import com.example.blocodenotascompose.ui.theme.GOLD
import com.example.blocodenotascompose.ui.theme.BLACK
import com.example.blocodenotascompose.ui.theme.WHITE
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlocoDeNotasComposeTheme {
            BlocoDeNotasComposable()
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlocoDeNotasComposable() {

    val context = LocalContext.current
    val  scope= rememberCoroutineScope()

    val storeAnotacao =StoreAnotacao(context)
    val anotacaoSalva = storeAnotacao.getAnotacao.collectAsState(initial = "")

    var anotacao by remember {
        mutableStateOf("")
    }
    anotacao = anotacaoSalva.value
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = GOLD),

                title = {
                    Text(
                        text = "Bloco de Notas",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = BLACK
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnotacao.salvarAnotacao(anotacao)
                        Toast.makeText(context,"Anotação Salva com Sucesso!", Toast.LENGTH_SHORT).show()
                    }
                },
                containerColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(
                    8.dp
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id=R.drawable.ic_salvar),
                    contentDescription = "Icone de Salvar Anitação"
                )
            }
        }
    ){ Column(){
            TextField(
                value = anotacao,
                onValueChange = {
                    anotacao = it
                },
                label = {
                    Text(text = "Digite a sua anotação")
                },
                modifier = Modifier.fillMaxWidth().padding(0.dp,60.dp,0.dp,0.dp).fillMaxHeight(),

                colors = TextFieldDefaults.colors(
                    cursorColor = GOLD,
                    focusedLabelColor = WHITE,
                    focusedContainerColor = WHITE,
                    focusedTextColor = BLACK,


                )
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlocoDeNotasComposeTheme {
    BlocoDeNotasComposable()
    }
}