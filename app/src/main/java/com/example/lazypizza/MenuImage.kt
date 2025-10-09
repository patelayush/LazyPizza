import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.viewmodel.imageBaseUrl

@Composable
fun MenuImage(modifier: Modifier = Modifier, categoryName: String, itemName: String?) {
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(getImageUrl(categoryName, itemName))
                .build(),
            contentDescription = "${itemName ?: ""} image",
            modifier = Modifier.fillMaxWidth(),
            onLoading = {
                isImageLoading = true
            },
            onSuccess = {
                isImageLoading = false
            },
            onError = {
                Log.e(
                    "MenuImage", "Error loading image: ${it.result.throwable} ${
                        getImageUrl(
                            categoryName, itemName
                        )
                    }"
                )
            }
        )
        if (isImageLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Primary
            )
        }
    }
}

fun getImageUrl(categoryName: String, name: String?): String {
    if (name == null) return ""
    return if (categoryName == "pizza" || categoryName == "sauce") {
        imageBaseUrl + "$categoryName/${name}.png"
    } else if (name == "Iced Tea (Lemon)") {
        "$imageBaseUrl$categoryName/iced tea.png"
    } else if (categoryName == "ice cream") {
        val modifiedName = name.replace("Ice Cream", "").trim().lowercase()
        "$imageBaseUrl$categoryName/${modifiedName}.png"
    } else if (categoryName == "toppings" && name == "Extra Cheese") {
        "$imageBaseUrl$categoryName/cheese.png"
    } else if (categoryName == "toppings" && name == "Mushrooms") {
        "$imageBaseUrl$categoryName/mashroom.png"
    } else if (categoryName == "toppings" && name == "Olives") {
        "$imageBaseUrl$categoryName/olive.png"
    }  else if (categoryName == "toppings" && name == "Chili Peppers") {
        "$imageBaseUrl$categoryName/chilli.png"
    } else {
        imageBaseUrl + "$categoryName/${name.lowercase()}.png"
    }
}