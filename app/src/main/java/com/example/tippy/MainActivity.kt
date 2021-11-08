package com.example.tippy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
private lateinit var etBase : EditText
private lateinit var seekBar : SeekBar

class MainActivity : AppCompatActivity() {
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
                Log.i(TAG, "onProgressChanged $progress")
                tipPercent.text = "$progress%"
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

    private fun computeTipAndTotal(){
        val tvTipAmount : TextView = findViewById(R.id.tvTipTotal)
        val tvBaseTotal = findViewById<TextView>(R.id.tvBaseTotal)

        //Get the value of the base and tip percent
        if(etBase.text.toString().isNullOrEmpty())
        {
            tvTipAmount.text = ""
            tvBaseTotal.text = ""
            return
        }
        val baseAmount = etBase.text.toString().toDouble()
        val tipPercent = seekBar.progress
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = tipAmount.toString()
        tvBaseTotal.text = totalAmount.toString()
    }
}