package com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ViagensModel (
    val viagem: String,
    val partida: String,
    val destino: String,
    val pacote: String
) : Parcelable