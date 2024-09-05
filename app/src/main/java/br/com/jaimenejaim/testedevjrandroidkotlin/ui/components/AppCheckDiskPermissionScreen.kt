package br.com.jaimenejaim.testedevjrandroidkotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jaimenejaim.testedevjrandroidkotlin.R
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.PrimaryColor
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SilverBackground

@Composable
fun CheckDiskPermissionScreen(
    modifier: Modifier,
    openPermissionRequest: () -> Unit,
    skipePermission: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            colorFilter = ColorFilter.tint(color = PrimaryColor),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(80.dp),
            painter = painterResource(id = R.drawable.ic_permission),
            contentDescription =  null
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Precisamos de sua permissão para criar e modificar arquivos.",
            modifier = Modifier.padding(bottom = 30.dp, start = 20.dp, end = 20.dp)
        )
        TestButton(
            text = "Permitir",
            modifier = Modifier
        ) {
            openPermissionRequest.invoke()
        }

        TestButton(
            contentColor = Color.Black,
            containerColor = Color.White,
            text = "Pular",
            modifier = Modifier
        ) {
            skipePermission.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckDiskPermissionScreen_Preview() {
    CheckDiskPermissionScreen(modifier = Modifier
        .fillMaxSize()
        .background(SilverBackground),
        openPermissionRequest = {

        },
        skipePermission = {

        }
    )
}