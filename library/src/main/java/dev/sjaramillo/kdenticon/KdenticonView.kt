package dev.sjaramillo.kdenticon

import android.content.Context
import android.widget.ImageView
import jdenticon.Jdenticon
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import com.caverock.androidsvg.SVG

/**
 * View that shows a Jdenticon in a native ImageView.
 */
class KdenticonView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    companion object {
        // Default values
        private const val DEFAULT_TEXT = ""
        private const val DEFAULT_HASH_ALGO = 0 // MD5

        // Constants used to correctly restore state
        private const val KEY_TEXT = "key_text"
        private const val KEY_HASH_ALGO = "key_hash_algo"
        private const val KEY_SUPER_STATE = "key_super_state"
    }

    /**
     * Algorithm to be used to hash the given text.
     */
    var hashAlgo = DEFAULT_HASH_ALGO
        set(value) {
            field = value

            renderKdenticon()
        }

    /**
     * Text that will be hashed to create the Jdenticon.
     */
    var text = DEFAULT_TEXT
        set(value) {
            field = value

            renderKdenticon()
        }

    init {
        setBackgroundResource(android.R.color.white) // TODO make background color styleable

        // Obtains xml attributes
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
                R.styleable.KdenticonView, 0, 0)

        text = typedArray.getString(R.styleable.KdenticonView_text) ?: DEFAULT_TEXT
        hashAlgo = typedArray.getInt(R.styleable.KdenticonView_hash_algo, HashAlgos.MD5.ordinal)

        typedArray.recycle()
    }

    /**
     * Obtains the Jdenticon representation of the hashed text, and displays it in the
     * current view (ImageView).
     */
    private fun renderKdenticon() {
        // Obtains text hash.
        val hash = when(hashAlgo) {
            HashAlgos.MD5.ordinal -> HashUtils.md5(text)
            HashAlgos.SHA1.ordinal -> HashUtils.sha1(text)
            HashAlgos.SHA256.ordinal -> HashUtils.sha256(text)
            HashAlgos.SHA512.ordinal -> HashUtils.sha512(text)
            else -> ""
        }

        // Obtains SVG string representation of the Jdenticon
        val size = 180      // TODO make size styleable
        val padding = 0.05f // TODO make padding styleable
        val svgString = Jdenticon.toSvg(hash, size, padding)

        // Converts string representation to SVG format and then to a PictureDrawable, which is
        // finally used to display in the current view (ImageView)
        val svg = SVG.getFromString(svgString)
        val pd = PictureDrawable(svg.renderToPicture())
        setImageDrawable(pd)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putString(KEY_TEXT, text)
        bundle.putInt(KEY_HASH_ALGO, hashAlgo)
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewState = state
        if (viewState is Bundle) {
            text = viewState.getString(KEY_TEXT, DEFAULT_TEXT)
            hashAlgo = viewState.getInt(KEY_HASH_ALGO, DEFAULT_HASH_ALGO)

            viewState = viewState.getParcelable(KEY_SUPER_STATE)
        }

        super.onRestoreInstanceState(viewState)
    }
}