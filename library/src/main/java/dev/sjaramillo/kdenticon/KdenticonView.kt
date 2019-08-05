package dev.sjaramillo.kdenticon

import android.content.Context
import android.widget.ImageView
import jdenticon.Jdenticon
import android.graphics.drawable.PictureDrawable
import android.util.AttributeSet
import com.caverock.androidsvg.SVG


class KdenticonView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    companion object {
        private const val DEFAULT_TEXT = ""
    }

    var hashAlgo = HashAlgos.MD5.ordinal
        set(value) {
            field = value

            renderKdenticon()
        }

    var text = DEFAULT_TEXT
        set(value) {
            field = value

            renderKdenticon()
        }

    init {
        setBackgroundResource(android.R.color.white) // TODO make background color styleable

        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
                R.styleable.KdenticonView, 0, 0)

        text = typedArray.getString(R.styleable.KdenticonView_text) ?: DEFAULT_TEXT
        hashAlgo = typedArray.getInt(R.styleable.KdenticonView_hash_algo, HashAlgos.MD5.ordinal)

        typedArray.recycle()
    }

    private fun renderKdenticon() {
        val hash = when(hashAlgo) {
            HashAlgos.MD5.ordinal -> HashUtils.md5(text)
            HashAlgos.SHA1.ordinal -> HashUtils.sha1(text)
            HashAlgos.SHA256.ordinal -> HashUtils.sha256(text)
            HashAlgos.SHA512.ordinal -> HashUtils.sha512(text)
            else -> ""
        }

        val size = 180
        val padding = 0.05f

        val svgString = Jdenticon.toSvg(hash, size, padding)

        val svg = SVG.getFromString(svgString)

        val pd = PictureDrawable(svg.renderToPicture())

        setImageDrawable(pd)
    }
}