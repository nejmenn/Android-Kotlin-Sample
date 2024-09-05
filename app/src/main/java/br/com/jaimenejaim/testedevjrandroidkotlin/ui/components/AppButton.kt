package br.com.jaimenejaim.testedevjrandroidkotlin.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.PrimaryColor

@Composable
fun TestButton(
    contentColor: Color = Color.White,
    containerColor: Color = PrimaryColor,
    modifier: Modifier,
    text: String,
    action: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(20),
        colors = ButtonColors(
            contentColor = contentColor,
            containerColor = containerColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        onClick = {
            action.invoke()
        }) {
        Text(
            text = text,
            style = TextStyle.Default,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TestButton_Preview() {
    TestButton(
        modifier = Modifier,
        text = "Permitir"
    ) {

    }
}