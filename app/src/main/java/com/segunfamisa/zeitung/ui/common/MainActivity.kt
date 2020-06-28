package com.segunfamisa.zeitung.ui.common

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.segunfamisa.zeitung.R
import com.segunfamisa.zeitung.databinding.ActivityMainBinding
import com.segunfamisa.zeitung.headlines.HeadlinesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNav.setOnNavigationItemSelectedListener {
            setBottomNavSelection(it.itemId)
        }
        binding.bottomNav.selectedItemId = R.id.menu_news
    }

    private fun setBottomNavSelection(@IdRes itemId: Int): Boolean {
        return when (itemId) {
            R.id.menu_news -> {
                addFragment(HeadlinesFragment::class.java.name) {
                    HeadlinesFragment()
                }
                true
            }
            else -> {
                Toast.makeText(this, "Not yet implemented", Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    private inline fun addFragment(tag: String, factory: () -> Fragment) {
        val frag = supportFragmentManager.findFragmentByTag(tag) ?: factory.invoke()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frag, tag)
            .commit()
    }
}
