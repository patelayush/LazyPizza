import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.PrimaryGradientEnd
import com.example.lazypizza.ui.theme.PrimaryGradientStart
import com.example.lazypizza.ui.theme.TextOnPrimary


@Composable
fun GradientButton(modifier: Modifier, text: String, onCLick: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        PrimaryGradientStart, PrimaryGradientEnd
                    )
                ),
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                onCLick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontFamily = FontFamily,
            fontWeight = FontWeight.SemiBold,
            color = TextOnPrimary,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        )
    }
}