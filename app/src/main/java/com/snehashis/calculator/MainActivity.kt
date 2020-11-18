package com.snehashis.calculator

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.keelar.exprk.Expressions
import kotlinx.android.synthetic.main.activity_main.*

var isDecPressed : Boolean = false

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not needed yet")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not needed yet")
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO
                Log.d("inputField", inputField.text.toString())
                if(inputField.text!=outputField.text)
                    outputField.text = Expressions().evalToString(inputField.text.toString())
            }
        })
        outputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not needed yet")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not needed yet")
            }

            override fun afterTextChanged(s: Editable?) {
                //outputValue = outputField.text.toString().toDouble()
            }
        })
        btn_1.setOnClickListener {
            numPressed('1')
        }
        btn_2.setOnClickListener {
            numPressed('2')
        }
        btn_3.setOnClickListener {
            numPressed('3')
        }
        btn_4.setOnClickListener {
            numPressed('4')
        }
        btn_5.setOnClickListener {
            numPressed('5')
        }
        btn_6.setOnClickListener {
            numPressed('6')
        }
        btn_7.setOnClickListener {
            numPressed('7')
        }
        btn_8.setOnClickListener {
            numPressed('8')
        }
        btn_9.setOnClickListener {
            numPressed('9')
        }
        btn_0.setOnClickListener {
            numPressed('0')
        }
        btn_dec.setOnClickListener {
            numPressed('.')
        }
        btn_add.setOnClickListener {
            numPressed('+')
        }
        btn_sub.setOnClickListener {
            numPressed('-')
        }
        btn_mul.setOnClickListener {
            numPressed('*')
        }
        btn_div.setOnClickListener {
            numPressed('/')
        }
        btn_lb.setOnClickListener {
            numPressed('(')
        }
        btn_rb.setOnClickListener {
            numPressed(')')
        }
        btn_eq.setOnClickListener {
            inputField.text = outputField.text
            outputField.text = ""
        }
        btn_del.setOnClickListener {
            if (inputField.text.length > 1)
                inputField.text =
                    inputField.text.toString().substring(0, inputField.text.toString().length - 1)
            else {
                inputField.text = ""
                outputField.text = ""
            }
        }
        btn_del.setOnLongClickListener(View.OnLongClickListener {
            inputField.text=""
            outputField.text=""
            return@OnLongClickListener true
        })
        btn_expo.setOnClickListener {
            numPressed('^')
        }
        btn_info.setOnClickListener {
            Toast.makeText(this, "Hey there have a nice Day", Toast.LENGTH_LONG).show()
            val about = AlertDialog.Builder(this)
            about.setTitle("About This App")
            val customView = layoutInflater.inflate(R.layout.dialog_about,null)
            about.setView(customView)
            about.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(this, "...\ud83c\udfb2...", Toast.LENGTH_SHORT).show()
            })
            about.show()
        }
    }
    private fun isOperator(op: Char): Boolean = (op == '+' || op == '-' || op == '*' || op == '/' || op == '^' || op == '%')

    private fun numPressed(num : Char) {
        if(inputField.text.toString() != "" || num == '.' || num == ')'){
            if(num == '.'&& isDecPressed )
                return
            else if(num == '.')
                isDecPressed = true
            else if (isOperator(num))
                isDecPressed = false

            inputField.text = inputField.text.toString() + num
        }
        else
            inputField.text = num.toString()
    }

}