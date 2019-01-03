package com.mindboardapps.app.smallsketch.tosvg.style

import java.io.*
import org.json.*
import com.mindboardapps.app.smallsketch.tosvg.model.Color

class StyleObject(styleJsonFile: File) : IStyleObject{
    override var border: Boolean = false
    override var borderColor: Color = StyleObjectRes.BLACK
    override var strokeWidth: Float = 1.0f
    override var strokeColor: Color = StyleObjectRes.BLACK
    override var backgroundColor: Color = StyleObjectRes.WHITE
    override var canvasWidth: Int? = null

    init {
        if( styleJsonFile.exists() ){
            val styleJsonText = FileInputStream(styleJsonFile).bufferedReader(Charsets.UTF_8).use {
                it.readText()
            }

            val jsonObject = JSONObject(styleJsonText)

            this.border = jsonObject.getBoolean("border")
            this.borderColor = StyleObjectRes.createColor( jsonObject.getJSONObject("borderColor"), StyleObjectRes.BLACK )

            this.strokeWidth = jsonObject.getDouble("strokeWidth").toFloat()
            this.strokeColor = StyleObjectRes.createColor( jsonObject.getJSONObject("strokeColor"), StyleObjectRes.BLACK )

            this.backgroundColor = StyleObjectRes.createColor( jsonObject.getJSONObject("backgroundColor"), StyleObjectRes.WHITE )

            this.canvasWidth = jsonObject.getInt("canvasWidth")
        }
    }

    override fun enabledCanvasWidth(): Boolean {
        val w = this.canvasWidth

        return if( w==null ){
            false
        }
        else if( w<1 ){
            false
        }
        else {
            true
        }
    }
}
