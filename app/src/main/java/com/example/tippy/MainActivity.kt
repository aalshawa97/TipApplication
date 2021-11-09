package com.example.tippy

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBase : EditText
    private lateinit var seekBar : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBase = findViewById(R.id.etBase)
        seekBar = findViewById(R.id.seekBar)
        seekBar.progress = INITIAL_TIP_PERCENT
        val tipPercent = findViewById<TextView>(R.id.tvTipPercent)
        tipPercent.text = "$INITIAL_TIP_PERCENT"
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO Auto-generated method stub
                val i = "$progress%"
                tipPercent.text = i
                updateTipDescription(progress)
                computeTipAndTotal()
            }
        })

        etBase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "After text changed $p0")
                computeTipAndTotal()
            }

        })
    }

    private  fun updateTipDescription(tipPercent : Int){
        val tipDescription : String
        tipDescription = when(tipPercent){
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Great"
            in 20..30 -> "Excellent!"
            else -> "Amazing!"
        }
        val tvTipDescription = findViewById<TextView>(R.id.tvAcceptable)
        tvTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat() / seekBar.max,
            ContextCompat.getColor(this, R.color.worstTip),
            ContextCompat.getColor(this, R.color.bestTip)
        ) as Int
        tvTipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal(){
        val tvTipAmount : TextView = findViewById(R.id.tvTipTotal)
        val tvBaseTotal = findViewById<TextView>(R.id.tvBaseTotal)

        //Get the value of the base and tip percent
        if(etBase.text.toString().isEmpty() ||!etBase.text.toString().isDigitsOnly())
        {
            tvTipAmount.text = ""
            tvBaseTotal.text = ""
            return
        }
        val baseAmount = etBase.text.toString().toDouble()
        val tipPercent = seekBar.progress
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = getString(R.string.twoDecimalPercision).format(tipAmount)
        tvBaseTotal.text = getString(R.string.twoDecimalPercision).format(totalAmount)
    }
}