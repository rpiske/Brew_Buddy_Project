//Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#1
//Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
//Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#3
//Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#4
//Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#5

package com.example.brewbuddyproject

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import android.util.Log
import android.view.View
import androidx.camera.core.ImageCaptureException
import com.example.brewbuddyproject.databinding.ActivityCameraBinding
import java.text.SimpleDateFormat
import java.util.Locale

typealias LumaListener = (luma: Double) -> Unit

//Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#1
class CameraActivity : AppCompatActivity() {

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#1
    private lateinit var viewBinding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Request camera permissions, if it has permissions it starts the camera, if not it starts a compat asking for permissions
        if (checkIfCameraPermissionGranted()) {
            startCameraPreview()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    //Upon destruction of the activity is shuts down the camera
    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#5
    //Functionality for taking the photo and storing it in the media
    fun takePhoto(view: View) {
        val imageCapture = imageCapture ?: return

        // Adds photo to the storage
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
        
        //Kills the camera activity after the picture was taken
        finish()
    }

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#3
    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#4
    //Code to start up the preview
    private fun startCameraPreview() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // This code builds the preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.cameraViewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            // Sets the rear camera to the default camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            //Binds camera to the lifecycle
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)
            //Throws an exception if the binding fails
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    //Method that checks if permissions were granted
    ////Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
    private fun checkIfCameraPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    //File format for taking a photo
    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
    //If request was approved start the camera, if not gives toast message that camera permission was not given
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkIfCameraPermissionGranted()) {
                startCameraPreview()
            } else {
                Toast.makeText(this,
                    "Camera permission wasn't granted.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}