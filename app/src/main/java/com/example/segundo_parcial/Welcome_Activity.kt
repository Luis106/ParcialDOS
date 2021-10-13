package com.example.segundo_parcial

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.picasso.Picasso
private val AutoList = listOf(
    Auto("RIO",2020,"KIA", "https://www.diariomotor.com/imagenes/picscache/1440x655c/kia-rio-2020-p_1440x655c.jpg", 28.6356351118141, -106.12311741760831),
    Auto("AVEO",2021,"CHEVROLET", "https://autobild.com.mx/wp-content/uploads/2021/04/chevroletaveo2021.jpg", 28.66258149422191, -106.12989798801155),
    Auto("SENTRA",2021,"NISSAN", "https://as01.epimg.net/mexico/imagenes/2020/10/04/masdeporte/1601840629_721294_1601840829_noticia_normal_recorte1.jpg",28.65305794197999, -106.13129227266826 )
)

private var lat: Double = 0.0
private var log: Double = 0.0

private lateinit var SigBoton: Button
private lateinit var AtrasBoton: Button
private lateinit var txt_modelo: TextView
private lateinit var txt_año: TextView
private lateinit var txt_marca: TextView
private lateinit var txt_usu: TextView

private lateinit var imgauto: ImageView

private lateinit var btn_mapa: Button
private lateinit var btn_agr: Button
private lateinit var btn_sal: Button



private var currentIndex = 0


class Welcome_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        SigBoton = findViewById(R.id.btn_sig)
        AtrasBoton = findViewById(R.id.btn_ant)
        txt_modelo = findViewById(R.id.txt_mode)
        txt_marca = findViewById(R.id.txt_Marca)
        txt_año = findViewById(R.id.txt_Anno)
        txt_usu = findViewById(R.id.txt_usuario)

        imgauto = findViewById(R.id.imgauto)

        btn_agr = findViewById(R.id.btn_agr)
        btn_mapa = findViewById(R.id.btn_map)
        btn_sal = findViewById(R.id.btn_salir)

        if(intent.hasExtra("User")){
            txt_usu.setText( intent.getStringExtra("User").toString()) ///Nombre con el que él intent fue enviado


        }else{
            txt_usu.setText("Usuario")
            Toast.makeText(this, "Error al cargar el usuario", Toast.LENGTH_SHORT).show();
        }

        btn_agr.setOnClickListener {
            val intent = Intent(this,  QRLECTOR::class.java).apply {

            };
            startActivity(intent);
        }

        btn_sal.setOnClickListener {
            val intent = Intent(this,  MainActivity::class.java).apply {

            };
            startActivity(intent);
        }

        btn_mapa.setOnClickListener {
            val intent = Intent(this,  Maps::class.java)

            intent.putExtra("Coo",  (lat.toString() +"/" +log.toString())  )
            startActivity(intent);
        }

        if(intent.hasExtra("qrCodeValue")){
            val qrcode = intent.getStringExtra("qrCodeValue").toString() ///Nombre con el que él intent fue enviado
            //Toast.makeText(this, "Intent envio qrCodeValue  " + qrcode , Toast.LENGTH_LONG).show();

            val strs = qrcode.split(",").toTypedArray()

            val modelo = strs.get(0)

            txt_modelo.setText(modelo);

            val año = strs.get(1)
            txt_año.setText(año);

            val marca = strs.get(2)
            txt_marca.setText(marca);

            val url = strs.get(3)
            Toast.makeText(this, "Intent envio qrCodeValue  " + qrcode , Toast.LENGTH_LONG).show();
            Picasso.get().load(url).into(imgauto);

            lat =  strs.get(4).toDouble()
            log =  strs.get(5).toDouble()


        }else{
            Toast.makeText(this, "Intent no envio qrCodeValue", Toast.LENGTH_LONG).show();
            updateAuto()
        }


        SigBoton.setOnClickListener {
            currentIndex = (currentIndex + 1) % AutoList.size
            updateAuto()
        }


        AtrasBoton.setOnClickListener {
            if (currentIndex > 0){
                currentIndex = (currentIndex - 1)
            }else{
                currentIndex = AutoList.size - 1
            }

            updateAuto()
        }


    }

    private fun updateAuto() {

        val modelo = AutoList[currentIndex].Modelo
        txt_modelo.setText(modelo);

        val año = AutoList[currentIndex].Año
        txt_año.setText(año.toString());

        val marca = AutoList[currentIndex].Marca
        txt_marca.setText(marca);

        val url = AutoList[currentIndex].Url

        Picasso.get().load(url).into(imgauto);

        lat = AutoList[currentIndex].lat.toDouble()
        log = AutoList[currentIndex].log.toDouble()





    }

}