package br.com.aulaspuc.appimc

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import br.com.aulaspuc.appimc.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflar a activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etPeso.setOnFocusChangeListener { peso, focus ->
            if (focus){
                binding.tvIMC.visibility = View.GONE
                binding.tvResult.visibility = View.GONE

                binding.tvTitleDica.visibility = View.VISIBLE
                binding.tvDica.visibility = View.VISIBLE
            }
        }

        binding.etAltura.setOnFocusChangeListener { altura, focus ->
            if (focus){
                binding.tvIMC.visibility = View.GONE
                binding.tvResult.visibility = View.GONE

                binding.tvTitleDica.visibility = View.VISIBLE
                binding.tvDica.visibility = View.VISIBLE
            }
        }

        binding.btnCalcular.setOnClickListener{
            hideKeyboard()
            validarEntrada()
        }
    }

    fun validarEntrada(){

        if(binding.etPeso.text.toString() == "" && binding.etAltura.text.toString() == ""){
            Snackbar.make(binding.tvIMC, "Informe seu peso ou sua altura.", Snackbar.LENGTH_LONG).show()
        }else if(binding.etPeso.text.toString() == "") {
                Snackbar.make(binding.tvIMC, "Informe seu peso.", Snackbar.LENGTH_LONG).show()
            }else if(binding.etAltura.text.toString() == "") {
                    Snackbar.make(binding.tvIMC, "Informe sua altura.", Snackbar.LENGTH_LONG).show()
                }else{
                    val imc = calculoDeIMC(binding.etPeso.text.toString(), binding.etAltura.text.toString())

                    val dcmFormat = DecimalFormat("#.0")
                    val imcVal = "Seu IMC:  ${dcmFormat.format(imc)}\n${checkIMC(imc)}"

                    binding.tvIMC.text = imcVal
                    binding.tvResult.text = "SEU RESULTADO"

                    binding.tvIMC.visibility = View.VISIBLE
                    binding.tvResult.visibility = View.VISIBLE

                    binding.tvTitleDica.visibility = View.GONE
                    binding.tvDica.visibility = View.GONE

                    binding.etAltura.text!!.clear()
                    binding.etAltura.clearFocus()

                    binding.etPeso.text!!.clear()
                    binding.etPeso.clearFocus()

                    var inputAltura = binding.etAltura.text.toString()
                    Log.i("inputAlturaTest", "onCreate: $inputAltura")

                    var inputPeso = binding.etPeso.text.toString()
                    Log.i("inputPesoTest", "onCreate: $inputPeso")

                    var outputPeso = dcmFormat.format(imc)
                    Log.i("inputPesoTest", "onCreate: $outputPeso")
                }
    }

    private fun calculoDeIMC(peso: String, altura: String): Double = peso.toDouble() / (altura.toDouble() * altura.toDouble())

    private fun checkIMC(db: Double): String{
        return when(db){
            in 0.0..17.0 -> "Muito abaixo do peso."
            in 17.1..18.49 -> "Abaixo do peso."
            in 18.5..24.99 -> "Peso normal."
            in 25.0..29.99 -> "Acima do peso."
            in 30.0..34.99 -> "Obesidade I."
            in 35.0..39.99 -> "Obesidade II(severa)."
            else -> "Obesidade III(mórbida)."
        }
    }

    private fun hideKeyboard(){
        val hide = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var currentFocus = this.currentFocus

        if(currentFocus == null){
            currentFocus = View(this)
        }
        hide.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}