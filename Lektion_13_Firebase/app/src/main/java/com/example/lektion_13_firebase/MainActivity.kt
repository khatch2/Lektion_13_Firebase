package com.example.lektion_13_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lektion_13_firebase.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var db : DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup DB
        db = FirebaseDatabase
            .getInstance("https://lektion-13-firebase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users")

        // ID's
        val btnUserSubmit = binding.btnUserSubmit
        val btnFetchUser = binding.btnFetchUser
        val tvUser = binding.tvUser
        val etUsername = binding.etUserUsername
        val etPassword = binding.etUserPassword

        /* TODO - THIS CODE IS NOT REQUIRED, GOOD FOR WHEN YOU DON'T USE ON-CLICK
                // Define Listener
                val userListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue<User>()
                        tvUser.text = user.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext, "An error occurred: $error", Toast.LENGTH_LONG).show()
                    }
                }


                // Connect DB with Listener
                db.addValueEventListener(userListener)
         */

        /* TODO - Query Information
        *   db.orderByChild("users")        <-- Fetches ALL users
        *   db.child("-NRXYny37FJF8btRkYPx") <--
        * */

        // On Fetch
        btnFetchUser.setOnClickListener {
            db.child("-NRXYny37FJF8btRkYPx")
                .get()
                .addOnSuccessListener {
                    // val newUser: User = it

                    val newUser = User(
                        it.child("username").value.toString(),
                        it.child("password").value.toString(),
                        it.child("isRegistered").value.toString().toBoolean()
                    )

                    tvUser.text = newUser.username
                }

                .addOnFailureListener {
                    Toast.makeText(applicationContext, "An error occurred: $it", Toast.LENGTH_LONG).show()
                }
        }

        // On Submit
        btnUserSubmit.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            /* TODO - Check if EMPTY */

            val newUser = User(username, password, true)
            tvUser.text = newUser.toString()

            /* TODO - CHECK USERNAME: EXISTS?
            *
            * #1 Check if username exist - Fetch
            * #2 DB Value against Username
            * */


            db.push()
                .setValue(newUser)
                .addOnSuccessListener {
                Toast.makeText(this, "Success: $newUser was inserted", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failure: Something went wrong $it", Toast.LENGTH_LONG).show()
            }
        }
    }
}










