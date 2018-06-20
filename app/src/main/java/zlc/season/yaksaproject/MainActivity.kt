package zlc.season.yaksaproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linear.setOnClickListener {
            startActivity(LinearExampleActivity::class.java)
        }

        grid.setOnClickListener {
            startActivity(GridExampleActivity::class.java)
        }

        stagger.setOnClickListener {
            startActivity(StaggerExampleActivity::class.java)
        }

        nested.setOnClickListener {
            startActivity(NestedExampleActivity::class.java)
        }

        custom.setOnClickListener {
            startActivity(CustomAdapterAndDslActivity::class.java)
        }
    }
}
