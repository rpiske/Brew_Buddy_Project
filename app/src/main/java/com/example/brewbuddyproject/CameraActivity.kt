//Much of this code was sourced or is based off of the official android CameraX documentation page:
//https://developer.android.com/codelabs/camerax-getting-started#1

package com.example.brewbuddyproject

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import android.util.Log
import android.view.View
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.PermissionChecker
import com.example.brewbuddyproject.databinding.ActivityCameraBinding
import com.example.brewbuddyproject.databinding.ActivityMainBinding
import java.nio.ByteBuffer
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
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        //viewBinding.cameraTakePictureButton.setOnClickListener { takePhoto() }
    }

    //Upon destruction of the activity is shuts down the camera
    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#2
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#5
    fun takePhoto(view: View) {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
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

        // Set up image capture listener, which is triggered after photo has been taken
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
    }

    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#3
    //Googler, (Accessed: May 5, 2023)Getting Started with Camera X, (Version: June 22, 2022)https://developer.android.com/codelabs/camerax-getting-started#4
    //Code to start up the preview
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
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

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

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
    //If request was approved start the camera, if not toast that permissions werent given
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkIfCameraPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Camera permission wasn't granted.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}