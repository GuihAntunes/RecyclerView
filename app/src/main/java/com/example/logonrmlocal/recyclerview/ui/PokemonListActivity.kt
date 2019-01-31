package com.example.logonrmlocal.recyclerview.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.logonrmlocal.recyclerview.R
import com.example.logonrmlocal.recyclerview.api.getPokemonService
import com.example.logonrmlocal.recyclerview.model.Pokemon
import com.example.logonrmlocal.recyclerview.model.PokemonResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pokemon_list.*

class PokemonListActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)
        loadContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun showOnList(pokemons: List<Pokemon>) {
        rvPokemons.adapter = PokemonListAdapter(this, pokemons, {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
        })
        rvPokemons.layoutManager = LinearLayoutManager(this)
    }

    private fun showError(t: Throwable) {
        Toast.makeText(this, t.message!!, Toast.LENGTH_LONG).show()
    }

    private fun loadContent() {
        getPokemonService()
                .getPokemons(150)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<PokemonResponse> {
                    override fun onComplete() {
                        Log.i("PokemonListActivity", "COMPLETE")
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(t: PokemonResponse) {
                        showOnList(t.content)
                    }

                    override fun onError(e: Throwable) {
                        showError(e)
                    }
                })
    }
}
