package com.itson.giftapp.view
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itson.giftapp.R
import com.itson.giftapp.adapter.FriendAdapter
import com.itson.giftapp.data.Friend

class FriendsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        val friendList = listOf(
            Friend("Amigo 1", R.drawable.amigo1),
            Friend("Amigo 2", R.drawable.amigo2),
            // Agrega más amigos si lo deseas
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = FriendAdapter(this, friendList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : FriendAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Aquí puedes mostrar el mensaje "Regalo enviado" cuando se hace clic en un amigo
                Toast.makeText(this@FriendsActivity, "Regalo enviado", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
