package io.github.adrianogba.searchviewrecycleviewlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView
import android.view.View


class MainActivity : AppCompatActivity() {

    //crating an arraylist to store users using the data class user
    val users = ArrayList<User>()

    //list filtered by the searchbar
    val filteredUsers = ArrayList<User>()

    //creating our adapter
    val adapter = CustomAdapter(users)

    //creating our adapter
    val filteredAdapter = CustomAdapter(filteredUsers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        //adding some dummy data to the list
        users.add(User("John Oliver", "26"))
        users.add(User("Bruce Wayne", "29"))
        users.add(User("Clark Kent", "27"))
        users.add(User("Berry Allen", "23"))


        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        //getting the search view from the menu
        val searchViewItem = menu.findItem(R.id.menuSearch)

        //getting the search view
        val searchView = searchViewItem.actionView as SearchView

        //making the searchview consume all the toolbar when open
        searchView.maxWidth= Int.MAX_VALUE

        searchView.queryHint = "Search View Hint"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //action while typing

                //hiding the empty textview
                tvEmpty.visibility= View.GONE

                if (newText.isEmpty()){
                    recyclerView.adapter = adapter

                }else{
                    filteredUsers.clear()
                    for (user in users){
                        if (user.name.toLowerCase().contains(newText.toLowerCase())){
                            filteredUsers.add(user)
                        }
                    }
                    if (filteredUsers.isEmpty()){
                        //showing the empty textview when the list is empty
                        tvEmpty.visibility= View.VISIBLE
                    }

                    recyclerView.adapter = filteredAdapter
                }

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //action when type Enter
                return false
            }

        })








        return true
    }
}
