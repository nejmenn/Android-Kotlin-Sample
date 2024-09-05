package br.com.jaimenejaim.testedevjrandroidkotlin.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

class BrazilianCurrency(private var _value: Double) {

    val value
        get() = _value

    init {
        _value = BigDecimal(value).setScale(2, RoundingMode.FLOOR).toDouble()
    }

    fun format() : String {
        val ptBr = Locale("pt", "BR")
        return NumberFormat.getCurrencyInstance(ptBr).format(value)
    }
}