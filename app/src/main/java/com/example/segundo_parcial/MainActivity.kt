package com.example.segundo_parcial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {


    private lateinit var txt_user: EditText
    private lateinit var txt_pass: EditText
    private lateinit var btn_login: Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_user =  findViewById(R.id.txt_user);
        txt_pass =  findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);


        var lottieA: LottieAnimationView = findViewById(R.id.ani_ppt)
        lottieA.setVisibility(View.INVISIBLE);


        btn_login.setOnClickListener {
            Log.d("Buttton","Se dio click en el boton");
            if (login()){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show();

                val intent = Intent(this,  Welcome_Activity::class.java)
                    intent.putExtra("User", "SegundoParcial")
                    startActivity(intent)

            }else{
                Toast.makeText(this, "Contraseña y/o usuario incorrecto", Toast.LENGTH_LONG).show();
                lottieA.setVisibility(View.VISIBLE);
            }
        };
    }

    private fun login(): Boolean{

       return txt_pass.text.toString() == "ULSA2021" && "SegundoParcial" == txt_user.text.toString()
        //return txt_pass.text.toString() == "1" && "1" == txt_user.text.toString()




    }


}