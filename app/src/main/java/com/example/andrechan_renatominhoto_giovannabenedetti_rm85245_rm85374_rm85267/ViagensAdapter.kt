package com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.andrechan_renatominhoto_giovannabenedetti_rm85245_rm85374_rm85267.databinding.ViagemItemViewBinding

class ViagensAdapter(val delegate:IUpdateViagem) :
    RecyclerView.Adapter<ViagensAdapter.LineViewHolder>(){

    val viagensList: MutableList<ViagensModel> = mutableListOf()

    class LineViewHolder(val itemHolder: ViagemItemViewBinding) :
            RecyclerView.ViewHolder(itemHolder.root) {
            fun bind(item: ViagensModel) {
                itemHolder.viagemTitulo.text = item.viagem
                itemHolder.viagemPacote.text = item.pacote
                itemHolder.viagemPartida.text = item.partida
                itemHolder.viagemDestino.text = item.destino
            }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        return LineViewHolder(
            ViagemItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        holder.bind(viagensList[position])

    }

    override fun getItemCount(): Int {
        return viagensList.size
    }

    fun setList(newViagens: List<ViagensModel>) {
        viagensList.clear()
        viagensList.addAll(newViagens)
        notifyDataSetChanged()
    }

    fun addListItem(newViagem: ViagensModel) {
        viagensList.add(newViagem)
        notifyItemInserted(viagensList.size)
    }

    }

