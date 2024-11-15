package com.example.reto01amm

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.example.reto01amm.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), OnClickListener, CompoundButton.OnCheckedChangeListener {
    //meter notificacion
    // lo del ingles, doble pantalla y verificacion con checkbox
    private lateinit var binding: ActivityMainBinding

    // Almaceno las variables y la operación (Sumar, restar, multiplicar o dividir)
    private var operando1: Double = 0.0
    private var operando2: Double = 0.0
    private var operacion: String = ""

    // Guardo la cadena del número en pantalla
    private var numVista: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Uso el OnClickListener a cada uno de los botones
        binding.button0.setOnClickListener(this)
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.button5.setOnClickListener(this)
        binding.button6.setOnClickListener(this)
        binding.button7.setOnClickListener(this)
        binding.button8.setOnClickListener(this)
        binding.button9.setOnClickListener(this)
        binding.buttonSumar.setOnClickListener(this)
        binding.buttonRestar.setOnClickListener(this)
        binding.buttonMultiplicar.setOnClickListener(this)
        binding.buttonDividir.setOnClickListener(this)
        binding.buttonAC.setOnClickListener(this)
        binding.buttonPi.setOnClickListener(this)
        binding.buttonRaiz.setOnClickListener(this)
        binding.buttonCuadrado.setOnClickListener(this)
        binding.buttonPorcentaje.setOnClickListener(this)
        binding.buttonIgual.setOnClickListener(this)
        binding.buttonComa.setOnClickListener(this)

        //Podria ser nulo porque solo aparece en el landscape
        binding.checkEdicion?.setOnCheckedChangeListener(this)

    }

    // Con este método visualizo el número en pantalla
    private fun visualizar(vista: View) {
        val boton = vista as Button
        val textoBoton = boton.text.toString()
        if(vista == binding.buttonComa){
            if(numVista.isEmpty()){
                numVista += "0,"
            }
            if(!numVista.contains(",")){
                numVista += textoBoton;
            }
        }else{
        numVista += textoBoton // Concateno el número
            }
        binding.resultado.text = numVista // Muestro el número que he concatenado
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            // Dependiendo del id se llama a la vista de ese botón
            binding.button0.id, binding.button1.id, binding.button2.id,
            binding.button3.id, binding.button4.id, binding.button5.id,
            binding.button6.id, binding.button7.id, binding.button8.id,
            binding.button9.id, binding.buttonComa.id -> {
                visualizar(v)
            }

            binding.buttonSumar.id -> {
                operacion = "+" // Almaceno el símbolo
                operando1 = numVista.replace(",", ".").toDouble() // Convierto el número a Double y lo guardo
                numVista = "" // Vacío para el siguiente operando
                binding.resultado.text = "" // Limpio la pantalla
            }

            binding.buttonRestar.id -> {
                operacion = "-"
                operando1 = numVista.replace(",", ".").toDouble()
                numVista = ""
                binding.resultado.text = ""
            }

            binding.buttonMultiplicar.id -> {
                operacion = "*"
                operando1 = numVista.replace(",", ".").toDouble()
                numVista = ""
                binding.resultado.text = ""
            }

            binding.buttonDividir.id -> {
                operacion = "/"
                operando1 = numVista.replace(",", ".").toDouble()
                numVista = ""
                binding.resultado.text = ""
            }

            binding.buttonDividir.id -> {
                operacion = "."
                operando1 = numVista.replace(",", ".").toDouble()
                numVista = ""
                binding.resultado.text = ""
            }

            binding.buttonIgual.id -> {
                operando2 = numVista.replace(",", ".").toDouble() // Convierto el operando 2 a Double
                calcular() // Llamo a la función calcular
                //Añado una notificacion
                Snackbar.make(
                    binding.root, "Operacion resuelta",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            binding.buttonAC.id -> {
                operando1 = 0.0 // Reinicio los operandos
                operando2 = 0.0
                numVista = "" // Borro lo que está en pantalla
                binding.resultado.text = "0" // Muestro cero en pantalla
            }

            binding.buttonRaiz.id -> {
                operando1 = numVista.replace(",", ".").toDouble()
                val resultado = sqrt(operando1)
                numVista = resultado.toString()
                binding.resultado.text = numVista
            }

            binding.buttonPorcentaje.id -> {
                operando1 = numVista.replace(",", ".").toDouble()
                val resultado = operando1 / 100
                numVista = resultado.toString()
                binding.resultado.text = numVista
            }

            binding.buttonPi.id -> {
                numVista = Math.PI.toString()
                binding.resultado.text = numVista
            }

            binding.buttonCuadrado.id -> {
                operando1 = numVista.replace(",", ".").toDouble()
                val resultado = operando1.pow(2.0)
                numVista = resultado.toString()
                binding.resultado.text = numVista
            }
        }
    }

    private fun calcular() {
        val resultado: Double = when (operacion) {
            // Realizo las operaciones
            "+" -> operando1 + operando2
            "-" -> operando1 - operando2
            "*" -> operando1 * operando2
            "/" -> if (operando2 != 0.0) operando1 / operando2 else Double.NaN
            else -> 0.0
        }

        binding.resultado.text = resultado.toString().replace(".", ",") // Muestro el resultado en pantalla
        numVista = resultado.toString() // Actualizo numVista para siguientes operaciones
    }

 //Realizo la configuracion para habilitar o no la calculadore en el modo horizontal
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            binding.checkEdicion?.id -> {
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    deshabilitarBotones(isChecked)
                }
            }
        }
    }

    private fun deshabilitarBotones(checked: Boolean) {
        val filas = binding.tablaBotones.children
        for(fila in filas){
            val botones = (fila as TableRow).children
            for(boton in botones) {
                boton.isEnabled = checked
            }
        }

    }
}