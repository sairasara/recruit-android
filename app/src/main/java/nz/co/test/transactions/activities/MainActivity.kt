package nz.co.test.transactions.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import nz.co.test.transactions.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}