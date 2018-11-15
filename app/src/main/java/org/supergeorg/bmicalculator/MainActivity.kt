package org.supergeorg.bmicalculator

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow


val resultMap = mapOf(16.0 to R.string.weight_1,17.0 to R.string.weight_2,
        18.5 to R.string.weight_3,25.0 to R.string.weight_4,30.0 to R.string.weight_5,
        35.0 to R.string.weight_6,40.0 to R.string.weight_7,45.0 to R.string.weight_8,
        50.0 to R.string.weight_9,60.0 to R.string.weight_10,100.0 to R.string.weight_11).toSortedMap()

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.about_str)
                        .setCancelable(false)
                        .setPositiveButton(R.string.about_ok){_, _ ->  }
                val alert = builder.create()
                alert.show()
                return true
            }
            R.id.menu_moreinfo -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.website_bmi))))
            }
        }
        invalidateOptionsMenu()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun getDescription(bmi: Double) : String?{
            resultMap.forEach {
                if (bmi<it.key) {
                    return getString(it.value)
                }
            }
                return getString(R.string.error)
        }

        fun updateBMI() : Boolean{
            val mass = editMass.text.toString()
            val height = editHeight.text.toString()
            if (!mass.isEmpty() and !height.isEmpty()){
                val bmi = mass.toDouble()/(height.toDouble().div(100).pow(2))
                textResult.visibility = View.VISIBLE
                textResult.text=getString(R.string.bmi_result, bmi).plus("\n").plus(getDescription(bmi))
                return true
            }
            textResult.visibility = View.INVISIBLE
            return false
        }

        updateBMI()

        editMass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                updateBMI()
            }
        })
        editHeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                updateBMI()
            }
        })
    }
}
