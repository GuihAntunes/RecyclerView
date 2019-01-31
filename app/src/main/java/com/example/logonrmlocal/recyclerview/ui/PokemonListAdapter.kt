package com.example.logonrmlocal.recyclerview.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logonrmlocal.recyclerview.R
import com.example.logonrmlocal.recyclerview.api.getPicassoAuth
import com.example.logonrmlocal.recyclerview.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_row.view.*

class PokemonListAdapter(
        private val context: Context,
        private val pokemons: List<Pokemon>,
        private val listener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.pokemon_row, parent, false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindView(pokemons[position], listener)
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(pokemon: Pokemon, listener: (Pokemon) -> Unit) = with(itemView) {
            tvPokemon.text = pokemon.name
            getPicassoAuth(itemView.context)
                    .load("https://pokedexdx.herokuapp.com${pokemon.imageURL}")
                    .into(ivPokemon)
            setOnClickListener { listener(pokemon) }
        }
    }
}