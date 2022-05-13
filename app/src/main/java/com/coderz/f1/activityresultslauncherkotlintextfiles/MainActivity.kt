package com.coderz.f1.activityresultslauncherkotlintextfiles

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.provider.DocumentsContractCompat
import com.coderz.f1.activityresultslauncherkotlintextfiles.databinding.ActivityMainBinding
import java.io.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    private val openFile: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()){uri ->
        try {
            val reader = InputStreamReader(contentResolver.openInputStream(uri))
            val content = reader.readText()
            binding.editText.setText(content)
            reader.close()
        } catch(exception:Exception){
            exception.printStackTrace()
        }
    }


    private val saveFile:ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.CreateDocument()){uri ->
        try {
            val content:String? = binding.editText.text.toString()
            val writer: Writer? = OutputStreamWriter(contentResolver.openOutputStream(uri,"w"))
            val bufferedWriter = BufferedWriter(writer)
            bufferedWriter.write(content)
            bufferedWriter.close()
        } catch(exception:Exception){
            exception.printStackTrace()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonOpen.setOnClickListener(buttonOpenListener)
        binding.buttonSave.setOnClickListener(buttonSaveListener)
        binding.buttonClear.setOnClickListener (buttonClearListener)
    }

    private val buttonOpenListener:View.OnClickListener = View.OnClickListener {
        openFile.launch("text/plain")
    }

    private val buttonSaveListener:View.OnClickListener = View.OnClickListener {
        saveFile.launch("filename.txt")
    }

    private val buttonClearListener:View.OnClickListener = View.OnClickListener {
        binding.editText.text.clear()
        binding.editText.requestFocus()
    }
}