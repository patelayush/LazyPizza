import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.Primary8
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.TextOnPrimary
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.Screen

@Composable
fun BottomBarIcon(
    modifier: Modifier = Modifier,
    tabName: Screen,
    tabIcon: Int? = null,
    subLabel: String? = null,
    currentTabSelected: Screen,
    onTabClick: (Screen) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .clickable {
                onTabClick(tabName)
            }
    ) {
        Box {
            tabIcon?.let {
                Box(
                    modifier = Modifier.background(
                        color = if (currentTabSelected == tabName) Primary8 else SurfaceHigher,
                        shape = CircleShape
                    ).padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(it),
                        contentDescription = "Menu",
                        colorFilter = ColorFilter.tint(
                            if (currentTabSelected == tabName) Primary else TextSeconday
                        ),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            subLabel?.let {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(
                            color = Primary,
                            shape = CircleShape
                        ).padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = subLabel,
                        fontSize = 11.sp,
                        fontFamily = FontFamily,
                        lineHeight = 1.em,
                        fontWeight = FontWeight.Medium,
                        color = TextOnPrimary
                    )
                }
            }
        }
        Text(
            text = tabName.title,
            fontSize = 11.sp,
            fontFamily = FontFamily,
            lineHeight = 1.em,
            fontWeight = FontWeight.Medium,
            color = if (currentTabSelected == tabName) TextPrimary else TextSeconday
        )
    }
}