package com.example.pizzalab

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzalab.adapters.TopSlideAdapter
import com.example.pizzalab.R
import com.example.pizzalab.adapters.PizzaAdapter

class MainActivity : AppCompatActivity(), PizzaAdapter.OnItemClickListener {

    private lateinit var topRecyclerView: RecyclerView
    private lateinit var verticalRecyclerView: RecyclerView
    private lateinit var topAdapter: TopSlideAdapter
    private lateinit var pizzaAdapter: PizzaAdapter
    private lateinit var topImageList: ArrayList<Int>
    private lateinit var pizzaList: ArrayList<Pizza>
    private lateinit var filteredPizzaList: ArrayList<Pizza>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topRecyclerView = findViewById(R.id.toprecyclerView)
        topRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRecyclerView.addItemDecoration(SpaceItemDecoration(20))
        topImageList = ArrayList()
        topImageList.add(R.drawable.topslide1)
        topImageList.add(R.drawable.topslide2)
        topImageList.add(R.drawable.topslide3)
        topImageList.add(R.drawable.topslide4)
        topImageList.add(R.drawable.topslide5)
        topImageList.add(R.drawable.topslide6)

        topAdapter = TopSlideAdapter(this, topImageList)
        topRecyclerView.adapter = topAdapter

        verticalRecyclerView = findViewById(R.id.verticalRecyclerView)
        verticalRecyclerView.layoutManager = LinearLayoutManager(this)
        verticalRecyclerView.addItemDecoration(SpaceItemDecoration(10))
        pizzaList = ArrayList()
        pizzaList.add(
            Pizza(
                R.drawable.pizza1,
                "Баварская",
                "Острые колбаски чоризо, маринованные огурчики, красный лук, томаты, горчичный соус, моцарелла, фирменный томатный соус",
                "2.700 тг"
            )
        )
        pizzaList.add(
            Pizza(
                R.drawable.pizza2,
                "Наруто Пицца",
                "Куриные кусочки, соус терияки, ананасы, моцарелла, фирменный соус альфредо",
                "2.700 тг"
            )
        )
        pizzaList.add(
            Pizza(
                R.drawable.pizza3,
                "Bay Кебаб",
                "Мясо говядины, соус ранч, сыр моцарелла, сладкий перец, томаты, красный лук и фирменный томатный соус",
                "2.700 тг"
            )
        )
        pizzaList.add(
            Pizza(
                R.drawable.pizza4,
                "Пепперони с грибами",
                "Пикантная пепперони, моцарелла, шампиньоны, соус альфредо",
                "2.700 тг"
            )
        )
        pizzaList.add(
            Pizza(
                R.drawable.pizza5,
                "Ветчина и огурчики",
                "Соус ранч, моцарелла, ветчина из цыпленка, маринованные огурчики, красный лук",
                "от 2.700 тг"
            )
        )
        pizzaAdapter = PizzaAdapter(pizzaList, this)
        verticalRecyclerView.adapter = pizzaAdapter


        filteredPizzaList = ArrayList()
        filteredPizzaList.addAll(pizzaList)
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val filteredPizzas = pizzaList.filter {
                    it.name.contains(
                        query,
                        ignoreCase = true
                    ) || it.description.contains(query, ignoreCase = true)
                }
                if (filteredPizzas.isNotEmpty()) {
                    filterPizzaList(filteredPizzas)
                } else {
                    Toast.makeText(this@MainActivity, "А такой нет:D", Toast.LENGTH_LONG).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterPizzaList(pizzaList.filter {
                    it.name.contains(
                        newText,
                        ignoreCase = true
                    ) || it.description.contains(newText, ignoreCase = true)
                })
                return true
            }
        })
    }

    private fun filterPizzaList(filteredPizzas: List<Pizza>) {
        filteredPizzaList.clear()
        filteredPizzaList.addAll(filteredPizzas)
        pizzaAdapter.updateList(filteredPizzas)
        pizzaAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(pizza: Pizza) {
        val intent = Intent(this, PizzaDetailsActivity::class.java)
        intent.putExtra("pizza", pizza)
        startActivity(intent)
    }

    class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.left = space
            outRect.right = space
        }
    }
}