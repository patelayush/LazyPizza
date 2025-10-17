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

@Composable
fun MenuImage(modifier: Modifier = Modifier, imageUrl: String) {
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build(),
            contentDescription = "Item image",
            modifier = Modifier.fillMaxWidth(),
            onLoading = {
                isImageLoading = true
            },
            onSuccess = {
                isImageLoading = false
            },
            onError = {
                Log.e(
                    "MenuImage", "Error loading image: ${it.result.throwable} $imageUrl"
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