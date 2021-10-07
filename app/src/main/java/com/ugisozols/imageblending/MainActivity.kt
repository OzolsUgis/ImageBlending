package com.ugisozols.imageblending

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.ugisozols.imageblending.ui.theme.ImageBlendingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val portrait = ImageBitmap.imageResource(id = R.drawable.vegetables)
            var clickOffset by remember {
                mutableStateOf(Offset.Zero)
            }
            var previousClickOffset by remember{
                mutableStateOf(Offset.Zero)
            }

            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragEnd = {
                             clickOffset = Offset.Zero
                        }
                    ){change, _ ->
                        clickOffset = previousClickOffset + change.position
                    }
                    
                }
            ){
                val canvasWidth = this.size.width.toInt()
                val canvasCenter = Offset(
                    x = center.x - (size.width/2),
                    y=  center.y - (canvasWidth * (portrait.height.toFloat()/ portrait.width) /  2f)
                )
                val circle = Path().apply {
                    addOval(
                        oval = Rect(clickOffset, 150f),
                    )
                }

                drawImage(
                    image = portrait,
                    dstOffset = IntOffset(canvasCenter.x.toInt(), canvasCenter.y.toInt()),
                    dstSize = IntSize(
                        width =  (canvasWidth),
                        height = (canvasWidth * (portrait.height.toFloat()/ portrait.width)).toInt()
                    ),
                    colorFilter = ColorFilter.tint(Color(0xFF141414), BlendMode.Color)
                )
                clipPath(circle, clipOp = ClipOp.Intersect){
                   drawImage(
                       image = portrait,
                       dstOffset = IntOffset(canvasCenter.x.toInt(), canvasCenter.y.toInt()),
                       dstSize = IntSize(
                           width =  (canvasWidth),
                           height = (canvasWidth * (portrait.height.toFloat()/ portrait.width)).toInt()
                       )
                   )

                }

            }
        }
    }
}

