package com.example.a3andm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.a3andm.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_photo.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PhotoActivity() : AppCompatActivity() {

    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null

    lateinit var DetailsPhoto : ArrayList<PhotoName>
    lateinit var DetailsPhotoV2 : ArrayList<PhotoName>
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        id = intent.getIntExtra("id", 0)

        if(this::DetailsPhoto.isInitialized){
            println("OK")
        } else {
            DetailsPhoto = ArrayList<PhotoName>()
        }
        loadData()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                requestPermissions(permissions, PERMISSION_CODE);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.CAMERA);
                requestPermissions(permissions, PERMISSION_CODE);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                requestPermissions(permissions, PERMISSION_CODE);
            }
        }

        //button click
        capture_btn.setOnClickListener {
            openCamera()
        }
        val swipeToDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                DetailsPhoto.remove(DetailsPhotoV2[pos])
                DetailsPhoto.removeAt(pos)
                saveData()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(RecyclerViewV2)

    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "We need the access to proceed the task", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val IMAGE_PICK_CODE = 1000;
        if (requestCode == IMAGE_CAPTURE_CODE){
            image_view.setImageURI(image_uri)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)
            image_uri = data?.data
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun gotoSave(view : View) {
        val name = findViewById<EditText>(R.id.message)
        val date = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
        val currentDate = date.format(Date())
        val item = PhotoName(image_uri.toString(), name.text.toString(), currentDate, id)
        DetailsPhoto.add(item)
        val intent = Intent(view.context, MainActivity::class.java)
        view.context.startActivity(intent)
        saveData()
        }

    private fun loadData(){
        var sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        var gson = Gson()
        var json = sharedPreferences.getString("DetailsPhoto", null)
        var type = object : TypeToken<ArrayList<PhotoName>>() {}.type
        if(json != null){
            DetailsPhoto = gson.fromJson(json, type)
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var gson = Gson()
        var json = gson.toJson(DetailsPhoto)
        editor.apply(){
            editor.putString("DetailsPhoto", json)
        }.apply()
    }

}

