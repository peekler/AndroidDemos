package hu.bme.aut.mymarket

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin.setOnClickListener {
            btnLogin.startAnimation()

            Handler().postDelayed({
                btnLogin.doneLoadingAnimation(Color.GREEN,
                        BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_48dp))

                val intent = Intent(this, FleaMarketActivity::class.java)
                val p1 = androidx.core.util.Pair.create<View, String>(ivLogo, "logo")
                val p2 = androidx.core.util.Pair.create<View, String>(btnLogin, "login")
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2)
                startActivity(intent, options.toBundle())

                btnLogin.revertAnimation()
            }, 1000)
        }
    }
}