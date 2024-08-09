package com.example.dictionaryeasy

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dictionaryeasy.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener{
            val word = binding.searchInput.text.toString()
            getMeaning(word)
        }

    }

    private fun getMeaning(word : String){
        setInProgress(true)
        GlobalScope.launch {
            val response = RetrofitInstance.dictionaryApi.getMeaning(word)
            runOnUiThread{
                setInProgress(false)
                response.body()?.first()?.let {
                    setUI(it)
                }
            }
        }
    }

    private fun setUI(response: WordResult){
        binding.wordTextview.text = response.word
        binding.phoneticTextview.text = response.phonetic
    }

    private fun setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.searchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.searchBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}