package co.feip.fefu2025


import android.os.Bundle
import androidx.activity.ComponentActivity
import co.feip.fefu2025.databinding.MainActivityBinding
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val lang = LanguageTag(this).apply {
                languageName = Constants.languages.random()
                tagColor = -0x1000000 or Random.nextInt(0xFFFFFF)
                usagePercentage = Random.nextFloat() * 100
            }
            binding.layout.addView(lang)
        }
    }
}