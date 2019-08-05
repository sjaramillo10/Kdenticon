package dev.sjaramillo.kdenticon.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import dev.sjaramillo.kdenticon.HashAlgos
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        // Setup spinner with its corresponding adapter
        val hashAlgos = HashAlgos.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hashAlgos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spHashAlgos.adapter = adapter

        // Generate a new Kdenticon with the given information
        btnGenerate.setOnClickListener { generateKdenticon() }
    }

    private fun generateKdenticon() {
        val text = etText.text.toString()
        val hashAlgo = spHashAlgos.selectedItemPosition

        kdenticon.text = text
        kdenticon.hashAlgo = hashAlgo
    }
}
