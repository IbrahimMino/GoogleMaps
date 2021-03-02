package com.example.mapsvazifa

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sour = findViewById<EditText>(R.id.edt_sourse)
        val des = findViewById<EditText>(R.id.edt_destionation)
        val momentLocation = findViewById<FloatingActionButton>(R.id.location_moment)


        val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
             var geoCoder:Geocoder
                var adreses:MutableList<Address> = ArrayList()
               geoCoder = Geocoder(this@MainActivity, Locale.getDefault())
                adreses = geoCoder.getFromLocation(location.latitude, location.longitude,1)
                val adres = adreses[0].getAddressLine(0)
                val country = adreses[0].countryName
               // Log.d("Tag", "${adres.toString()} - $country")



            }

            override fun onProviderDisabled(provider: String) {
                super.onProviderDisabled(provider)
            }

            override fun onProviderEnabled(provider: String) {
                super.onProviderEnabled(provider)
            }


        }
        if (ContextCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            Toast.makeText(applicationContext, "Joylashuvni aniqlashga ruxsar berilmagan.", Toast.LENGTH_SHORT).show()

        }else{
            //we have permisson
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0F,locationListener)

        }

        btn_go.setOnClickListener {
            //check contion
            if (sour.text.isNotEmpty() && des.text.isNotEmpty()) {
                DisplayTrack(sour.text.toString(), des.text.toString())
            }else{
                Toast.makeText(applicationContext, "Locatsiya kiriting!", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun DisplayTrack(sourse: String, destenation: String) {
        //If the device does not have a map installed, then redirect it to play store
        try {
          var uri:Uri = Uri.parse("https://www.google.co.in/maps/dir/" + sourse + "/" + destenation)
            //Initialize intent with action view
            val intent:Intent = Intent(Intent.ACTION_VIEW,uri)
            //set package
            intent.setPackage("com.google.android.apps.maps")
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //Start activity
            startActivity(intent)

        }catch (ex:ActivityNotFoundException){
            //when google map not installed
            //Initialize uri
            val uri:Uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            //Initialize intent with action view
            val intent:Intent = Intent(Intent.ACTION_VIEW,uri)
            //Set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //Start activity
            startActivity(intent)

        }

    }

    private fun PlaceState(place:String){
        //google.com/maps/place/Namangan
        var uri:Uri = Uri.parse("google.com/maps/place/" + place)
        //Initialize intent with action view
        val intent:Intent = Intent(Intent.ACTION_VIEW,uri)
        //set package
        intent.setPackage("com.google.android.apps.maps")
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //Start activity
        startActivity(intent)


    }

}