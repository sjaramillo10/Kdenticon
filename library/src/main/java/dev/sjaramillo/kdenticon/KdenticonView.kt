package dev.sjaramillo.kdenticon

import android.content.Context
import android.widget.ImageView
import jdenticon.Jdenticon
import android.graphics.drawable.PictureDrawable
import android.util.AttributeSet
import com.caverock.androidsvg.SVG


class KdenticonView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    init {
        val hash = "3CD098490EE45551C21DBBF12AAF370BF34F54FABF5BE4ADB8DC92003F011ACE" // palmpaytest-6 sha256
        val size = 180
        val padding = 0f

        val svgString = Jdenticon.toSvg(hash, size, padding)

        val svg = SVG.getFromString(svgString)

        val pd = PictureDrawable(svg.renderToPicture())

        setImageDrawable(pd)
    }
}