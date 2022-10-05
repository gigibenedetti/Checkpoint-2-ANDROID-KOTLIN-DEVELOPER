package com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.databinding.ActivityAdicionarViagemBinding

class AdicionarViagemActivity: AppCompatActivity() {
    private lateinit var databind: ActivityAdicionarViagemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databind = ActivityAdicionarViagemBinding.inflate(layoutInflater)
        setContentView(databind.root)

        databind.confirmBtn.setOnClickListener{
            Intent().apply {
                    ViagensModel(
                        databind.viagemTituloTxt.text.toString(),
                        databind.viagemPacoteTxt.text.toString(),
                        databind.viagemPartidaTxt.text.toString(),
                        databind.viagemDestinoTxt.text.toString()
                    )
            }
            this.finish()
        }
    }
}