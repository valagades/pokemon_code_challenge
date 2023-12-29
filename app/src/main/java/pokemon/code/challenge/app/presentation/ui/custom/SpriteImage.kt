package pokemon.code.challenge.app.presentation.ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import pokemon.code.challenge.app.R

@Composable
fun SpriteImage(name: String, url: String, modifier: Modifier) {
    if (url.isEmpty()) {
        val isInitialLetter = name.firstOrNull()?.isLetter() ?: false
        if (isInitialLetter) {
            val names = name.split(" ")
            val initials = names.getOrNull(0)?.take(0).orEmpty().plus(names.getOrNull(1)?.take(0).orEmpty()).uppercase()
            InitialsView(initials, false, modifier)
        } else {
            InitialsView("", true, modifier)
        }
    } else {
        GlideImage(
            imageModel = { url },
            modifier = modifier,
            loading = {
                Box(modifier = Modifier.fillMaxSize(), content = {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Blue
                    )
                })
            },
            failure = {
                InitialsView("", true, modifier)
            }
        )
    }
}

@Composable
private fun InitialsView(initials: String, showPlaceHolder: Boolean, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(64.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Green),
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = {
                    if (showPlaceHolder) {
                        Image(
                            painter = painterResource(id = R.drawable.pokemon_placeholder),
                            contentDescription = "Placeholder",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                        )
                    } else {
                        Text(
                            text = initials,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    }
                }
            )
        }
    )
}