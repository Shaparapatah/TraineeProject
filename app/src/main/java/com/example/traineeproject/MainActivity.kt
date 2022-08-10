package com.example.traineeproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.traineeproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val imageUrl = "https://picsum.photos/id/870/200/300?grayscale&blur=2"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showPicture()

    }

    private fun showPicture() {
        binding.bnt.setOnClickListener {
            val editLink = binding.editText.text.toString()

            if (editLink != imageUrl) {
                Toast.makeText(this@MainActivity, "Wrong URL!", Toast.LENGTH_SHORT).show()
            } else {
                Glide.with(this@MainActivity)
                    .load(imageUrl)
                    .into(binding.imageView)
            }
        }
    }
}

