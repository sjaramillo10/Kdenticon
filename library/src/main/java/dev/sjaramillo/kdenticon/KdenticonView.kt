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

        // Hash algorithms
        private const val MD5 = 0
        private const val SHA1 = 1
        private const val SHA256 = 2
        private const val SHA512 = 3
    }

    var hashAlgo = SHA256
        set(value) {
            field = value

            renderJdenticon()
        }

    var text = DEFAULT_TEXT
        set(value) {
            field = value

            renderJdenticon()
        }

    init {
        val hash = "3CD098490EE45551C21DBBF12AAF370BF34F54FABF5BE4ADB8DC92003F011ACE" // palmpaytest-6 sha256

        setupAttributes(attrs)
        renderJdenticon()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
                R.styleable.KdenticonView, 0, 0)

        text = typedArray.getString(R.styleable.KdenticonView_text) ?: DEFAULT_TEXT
        hashAlgo = typedArray.getInt(R.styleable.KdenticonView_hash_algo, MD5)

        typedArray.recycle()
    }

    private fun renderJdenticon() {
        val hash = when(hashAlgo) {
            MD5 -> HashUtils.md5(text)
            SHA1 -> HashUtils.sha1(text)
            SHA256 -> HashUtils.sha256(text)
            SHA512 -> HashUtils.sha512(text)
            else -> ""
        }

        val size = 180
        val padding = 0f

        val svgString = Jdenticon.toSvg(hash, size, padding)

        val svg = SVG.getFromString(svgString)

        val pd = PictureDrawable(svg.renderToPicture())

        setImageDrawable(pd)
    }
}