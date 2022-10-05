package com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.Constants.IS_FIRST_TIME
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.Constants.VIAGEM_PREFERENCES
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.Constants.VIAGEM_RESULT
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.Constants.VIAGEM_STORE
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity(), IUpdateViagem {
    private lateinit var databind: ActivityMainBinding
    val viagemAdapter = ViagensAdapter(this)

    private val lista: MutableList<ViagensModel> = mutableListOf()


    fun setLista(listaItem: MutableList<ViagensModel>) {
        lista.clear()
        lista.addAll(listaItem)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(databind.root)

        if (isFirstTime()) {
            callWelcomeDialog()
            savefirstTime()
        }

        setupReclycler()
        viagemAdapter.setList(getViagens())

        databind.adicionarViagensBtn.setOnClickListener {
            addViagemRegister.launch(Intent(this, AdicionarViagemActivity::class.java))
        }
    }

    private fun setupReclycler() {
        databind.listaViagensView.layoutManager = LinearLayoutManager(this)
        databind.listaViagensView.adapter = viagemAdapter

        val l = mutableListOf<ViagensModel>()
        l.add(ViagensModel("Bahia>São Paulo","Bahia", "São Paulo", "Premium"))
        l.add(ViagensModel("Bahia>São Paulo","Bahia", "São Paulo", "Premium"))
        l.add(ViagensModel("Bahia>São Paulo","Bahia", "São Paulo", "Premium"))
        l.add(ViagensModel("Bahia>São Paulo","Bahia", "São Paulo", "Premium"))
        setLista(l)
        databind.listaViagensView.adapter
    }

    fun getViagens(): ArrayList<ViagensModel> {

        val sharedPref = this.getSharedPreferences(VIAGEM_PREFERENCES, Context.MODE_PRIVATE)
        val gsonValue = sharedPref?.getString(VIAGEM_STORE, null)
        if (gsonValue != null) {
            val itemType: Type = object : TypeToken<ArrayList<ViagensModel>>() {}.type
            return Gson().fromJson(gsonValue, itemType)
        }
        return ArrayList()
    }

    fun isFirstTime(): Boolean {

        val sharedPref = this.getSharedPreferences(VIAGEM_PREFERENCES, Context.MODE_PRIVATE)
        val isFirstTime = sharedPref?.getBoolean(IS_FIRST_TIME, true)
        return isFirstTime ?: true
    }

    private fun savefirstTime() {
        val sharedPreference = getSharedPreferences(VIAGEM_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean(IS_FIRST_TIME, false)
        editor.apply()
    }

    private fun saveViagensList(viagens: ArrayList<ViagensModel>) {
        val sharedPreference = getSharedPreferences(VIAGEM_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val json = Gson().toJson(viagens)
        editor.putString(VIAGEM_STORE, json)
        editor.apply()
    }

    private fun callWelcomeDialog() {

        val confirmDialog = AlertDialog.Builder(this)
        confirmDialog.setTitle("Bem Vindo!")
        confirmDialog.setMessage("Gostaria de já cadastrar sua primeira viagem ?")
        confirmDialog.setPositiveButton("Sim") { _, _ ->
            addViagemRegister.launch(Intent(this, AdicionarViagemActivity::class.java))
        }
        confirmDialog.setNegativeButton("Não") { dialog, _ ->
            dialog.cancel()
        }
        confirmDialog.show()
    }

    private val addViagemRegister = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                if (data.hasExtra(VIAGEM_RESULT)) {
                    val resultViagem = data.getParcelableExtra<ViagensModel>(VIAGEM_RESULT)
                    if (resultViagem != null) {
                        viagemAdapter.addListItem(resultViagem)
                        saveViagensList(viagemAdapter.viagensList as ArrayList<ViagensModel>)
                    }
                }
            }
        }
    }

    override fun UpdateViagem() {
        saveViagensList(viagemAdapter.viagensList as ArrayList<ViagensModel>)
    }
}