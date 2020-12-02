package com.snehashis.calculator

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.github.keelar.exprk.Expressions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_about.view.*
import kotlinx.android.synthetic.main.diaolog_theme.view.*
import kotlin.random.Random

var isDecPressed : Boolean = false
lateinit var defColour : ColorStateList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        defColour = outputField.textColors
        val sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val themeState = sharedPreferences.getInt("themeState", AppCompatDelegate.getDefaultNightMode())
        AppCompatDelegate.setDefaultNightMode(themeState)

        inputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not needed yet")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not needed yet")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("inputField", inputField.text.toString())
                if(inputField.text!=outputField.text){
                    try {

                        outputField.text = Expressions().eval(formatExpression(inputField.text.toString())).toString()
                    }
                    catch (e: Exception){
                        //e.printStackTrace()
                        outputField.text = e.message
                    }

                }
            }
        })
        btn_1.setOnClickListener {
           numPressed(it, '1')
        }
        btn_2.setOnClickListener {
           numPressed(it, '2')
        }
        btn_3.setOnClickListener {
           numPressed(it, '3')
        }
        btn_4.setOnClickListener {
           numPressed(it, '4')
        }
        btn_5.setOnClickListener {
           numPressed(it, '5')
        }
        btn_6.setOnClickListener {
           numPressed(it, '6')
        }
        btn_7.setOnClickListener {
           numPressed(it, '7')
        }
        btn_8.setOnClickListener {
           numPressed(it, '8')
        }
        btn_9.setOnClickListener {
           numPressed(it, '9')
        }
        btn_0.setOnClickListener {
           numPressed(it, '0')
        }
        btn_dec.setOnClickListener {
           numPressed(it, '.')
        }
        btn_add.setOnClickListener {
           numPressed(it, '+')
        }
        btn_sub.setOnClickListener {
           numPressed(it, '-')
        }
        btn_mul.setOnClickListener {
           numPressed(it, '×')
        }
        btn_div.setOnClickListener {
           numPressed(it, '÷')
        }
        btn_lb.setOnClickListener {
           numPressed(it, '(')
        }
        btn_rb.setOnClickListener {
           numPressed(it, ')')
        }
        btn_eq.setOnClickListener {
            it.rootView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            if(inputField.text.isNotEmpty() || inputField.text.isNotBlank()) {
                inputField.text = outputField.text
                toggleIpOp()
            }
        }
        btn_del.setOnClickListener {
            it.rootView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            if (inputField.text.length > 1 ) {
                if (inputField.text.toString()[inputField.text.toString().length - 1] == '.') isDecPressed = false
                inputField.text = inputField.text.toString().substring(0, inputField.text.toString().length - 1)
            }
            else {
                inputField.text = ""
                outputField.text = ""
                isDecPressed = false
            }
        }
        btn_aclr.setOnClickListener {
            it.rootView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            inputField.text = ""
            outputField.text = ""
            isDecPressed = false
            toggleIpOp()
        }
        btn_del.setOnLongClickListener(View.OnLongClickListener {
            btn_aclr.performClick()
            return@OnLongClickListener true
        })
        btn_expo.setOnClickListener {
           numPressed(it, '^')
        }
        btn_info.setOnClickListener {
            it.rootView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val about = MaterialAlertDialogBuilder(this)
            about.setTitle("About This App")
            val customView = layoutInflater.inflate(R.layout.dialog_about,null)
            about.setView(customView)
            var tapCount = 0
            var diceRoll = 0
            customView.appIconImage.setOnClickListener {
                tapCount++
                if (tapCount == 6){
                    customView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    Toast.makeText(this, "Rolling...\uD83C\uDFB2...", Toast.LENGTH_SHORT).show()
                    diceRoll = Random.nextInt(1,6)
                }
            }
            about.setPositiveButton("Ok") { _, _ ->
                if(diceRoll > 0) {
                    Toast.makeText(this, if (diceRoll < 3) "You Rolled $diceRoll XD\nBetter Luck Next Time"
                    else if (diceRoll < 5) "You rolled $diceRoll!\nIt's Nothing just an Easter Egg XD"
                    else "You rolled $diceRoll!!!\nYou have great luck, Have Fun XD", Toast.LENGTH_LONG).show()
                }
                tapCount = 0
                diceRoll = 0
            }
            about.setCancelable(false)
            about.show()
        }

        btn_menu.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { item->
                when (item.itemId) {
                    R.id.btn_theme -> {
                        val themeDialog = MaterialAlertDialogBuilder(this)
                        themeDialog.setTitle("Choose a theme")
                        val themeView = layoutInflater.inflate(R.layout.diaolog_theme, null)
                        themeDialog.setView(themeView)
                        themeView.themeGroup.check( when (themeState){
                            AppCompatDelegate.MODE_NIGHT_NO -> R.id.theme_light
                            AppCompatDelegate.MODE_NIGHT_YES -> R.id.theme_dark
                            else -> R.id.theme_auto
                        })
                        themeDialog.setPositiveButton("Ok") { _, _ ->
                            when (themeView.themeGroup.checkedRadioButtonId) {
                                R.id.theme_light -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                R.id.theme_dark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                R.id.theme_auto -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                            }
                            editor.putInt("themeState", AppCompatDelegate.getDefaultNightMode())
                            editor.apply()
                        }
                        themeDialog.setNegativeButton("Cancel") {_, _ ->
                            //TODO
                        }
                        themeDialog.setCancelable(false)
                        themeDialog.show()
                    }
                    R.id.btn_about ->
                        btn_info.performClick()
                }
                true
            }
            popupMenu.show()
        }
    }
    private fun isOperator(op: Char): Boolean = (op == '+' || op == '-' || op =='*' || op == '×' || op == '/' || op == '÷' || op == '^' || op == '%')

    private fun toggleIpOp() {
        if(inputField.visibility == View.VISIBLE) {
            outputField.setTextColor(inputField.textColors)
            inputField.visibility = View.GONE
            btn_aclr.visibility = View.VISIBLE
            btn_del.visibility = View.GONE
        }
        else {
            outputField.setTextColor(defColour)
            inputField.visibility = View.VISIBLE
            btn_aclr.visibility = View.GONE
            btn_del.visibility = View.VISIBLE
        }
    }

    private fun formatExpression(exp : String) : String {
        var i = 0
        var newExp: String = ""
        while (i < exp.length)
        {
            when(exp[i]){
                '(' ->
                    if (i > 0 && newExp[newExp.length-1] != '(' && !isOperator(newExp[newExp.length-1])) newExp += "*(" else newExp += '('
                ')' -> newExp += ')'
                '^' -> newExp += '^'
                '×' -> newExp += '*'
                '÷' -> newExp += '/'
                '+' -> newExp += '+'
                '-' -> newExp += '-'
                else -> if (i > 0 && newExp[newExp.length - 1] == ')') newExp += "*" + exp[i] else newExp += exp[i]
            }
            i++
            Log.d("Formatted",newExp)
        }

        return newExp
    }

    @SuppressLint("SetTextI18n")
    private fun numPressed(view: View, num : Char) {
        if(inputField.visibility == View.GONE) toggleIpOp()
        if(inputField.text.toString() != "" || num == '.' || num == ')'){
            if(num == '.' && isDecPressed )
                return
            else if(num == '.')
                isDecPressed = true
            else if (isOperator(num) || num == ')' || num == '(')
                isDecPressed = false

            inputField.text = inputField.text.toString() + num
        }
        else
            inputField.text = num.toString()
        view.rootView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

}