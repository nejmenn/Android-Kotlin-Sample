package br.com.jaimenejaim.testedevjrandroidkotlin.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.PrimaryColor

@Composable
fun AppCircularProgressIndicator(
    color: Color,
    modifier: Modifier
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun AppCircularProgressIndicator_Preview() {
    AppCircularProgressIndicator(
        modifier = Modifier,
        color = PrimaryColor
    )
}