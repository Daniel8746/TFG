import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextLogin(onClick: () -> Unit, color: Color = MaterialTheme.colorScheme.primary) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Text(
            "¿Ya tienes una cuenta?",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )
        Spacer(Modifier.width(8.dp))
        Text(
            "Registrarse",
            modifier = Modifier.clickable(onClick = onClick),
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextLoginTest() {
    Column {
        TextLogin(onClick = {})
    }
}
