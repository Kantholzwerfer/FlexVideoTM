import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.graphics.Typeface
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.example.flexvideotm.R
import android.content.ContentValues
import android.graphics.Color
import android.os.Environment

private const val CAMERA_PERMISSION_REQUEST_CODE = 100
private const val CAMERA_REQUEST_CODE = 101
private const val PERMISSION_REQUEST_CODE = 102
private const val REQUEST_WRITE_EXTERNAL_STORAGE = 103
private const val FILE_PROVIDER_AUTHORITY = "com.example.flexvideotm.fileprovider"

class Exercises : Fragment() {
    private lateinit var linearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercises, container, false)
        linearLayout = view.findViewById(R.id.linearLayout)
        val openCameraButton: Button = view.findViewById(R.id.openCameraButton)
        openCameraButton.setOnClickListener {
            openCamera()
        }
        Log.d("Exercises", "Fetching videos from gallery")
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            displayVideosFromGallery()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }

        return view
    }

    @Suppress("DEPRECATION")
    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val videoFile = createVideoFile()

            val videoUri = FileProvider.getUriForFile(
                requireContext(),
                FILE_PROVIDER_AUTHORITY,
                videoFile
            )

            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)

            val resolvedIntentActivities = requireContext().packageManager
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolvedIntentInfo in resolvedIntentActivities) {
                val packageName = resolvedIntentInfo.activityInfo.packageName
                requireContext().grantUriPermission(
                    packageName,
                    videoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun createVideoFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val videoFileName = "my_video_$timestamp.mp4"

        val storageDir = File(requireContext().filesDir, "FlexVideo")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val videoFile = File(storageDir, videoFileName)

        return videoFile
    }

    @Suppress("DEPRECATION")
    private fun displayVideosFromGallery() {
        val videoDirectory = File("/data/data/com.example.flexvideotm/files/FlexVideo/")
        videoDirectory.setReadable(true)
        if (!videoDirectory.exists()) {
            val isDirectoryCreated = videoDirectory.mkdirs()
            if (!isDirectoryCreated) {
                Log.e("Videos", "Failed to create FlexVideo directory.")
                return
            }
        }
        else {
            importVideosToGallery()
        }

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA
        )

        val selection = when {
            videoDirectory.exists() -> "${MediaStore.Video.Media.DATA} like ? OR ${MediaStore.Video.Media.DATA} like ?"
            else -> MediaStore.Video.Media.DATA + " like ?"
        }

        val selectionArgs = when {
            videoDirectory.exists() -> arrayOf("%/FlexVideo/%", "%${videoDirectory.absolutePath}%")
            else -> arrayOf("%${videoDirectory.absolutePath}%")
        }

        Log.d("Selection", "Selection: $selection")
        Log.d("Selection", "Selection Args: ${selectionArgs.contentToString()}")

        val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"

        val cursor: Cursor? = requireContext().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder

        )
        if (cursor == null) {
            Log.e("Videos", "Cursor ist NULL.")
        }

        cursor?.use {
            val videoColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            Log.d("Videos", "Total videos: ${cursor.count}")
            while (cursor.moveToNext()) {
                val videoPath = cursor.getString(videoColumnIndex)
                Log.d("Cursor", "Video Path: $videoPath")
                val videoUri = Uri.parse(videoPath)

                val file = File(videoPath)
                val videoFileName = file.name
                val videoFileExtension = file.extension

                Log.d("Videos", "Video File Name: $videoFileName")
                Log.d("Videos", "Video File Extension: $videoFileExtension")

                val canRead = file.canRead()
                val canWrite = file.canWrite()

                Log.d("Videos", "Can Read: $canRead")
                Log.d("Videos", "Can Write: $canWrite")

                if (videoUri != null && videoPath.isNotEmpty()) {
                    val videoView = VideoView(requireContext())
                    val width = resources.displayMetrics.widthPixels
                    val height = resources.displayMetrics.heightPixels
                    videoView.layoutParams = ViewGroup.LayoutParams(width, height)

                    Log.d("Videos", "Video URI: $videoUri")

                    val playButton = ImageView(requireContext())
                    playButton.setImageResource(R.drawable.ic_play_button)
                    playButton.scaleType = ImageView.ScaleType.CENTER
                    playButton.visibility = View.VISIBLE
                    videoView.setOnPreparedListener { mp ->
                        playButton.setOnClickListener {
                            playButton.visibility = View.GONE
                            mp.start()
                        }
                    }

                    val videoFileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                    val fileNameWithoutExtension = videoFileName.substringBeforeLast(".")
                    val videoTextView = TextView(requireContext())
                    videoTextView.text = fileNameWithoutExtension
                    videoTextView.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    videoTextView.setPadding(0, 35, 0, 35)
                    videoTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    videoTextView.setTypeface(null, Typeface.BOLD)
                    videoTextView.setBackgroundColor(Color.parseColor("#0878b6"))

                    linearLayout.addView(videoView)
                    linearLayout.addView(playButton)
                    linearLayout.addView(videoTextView)

                    videoView.setVideoURI(videoUri)
                    videoView.visibility = View.VISIBLE
                    Log.d("Videos", "Video path: $videoPath")
                }
            }
        }
    }

    private fun importVideosToGallery() {
        val videoDirectory = File("/data/data/com.example.flexvideotm/files/FlexVideo/")

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA
        )

        val selection = when {
            videoDirectory.exists() -> "${MediaStore.Video.Media.DATA} like ?"
            else -> MediaStore.Video.Media.DATA + " like ?"
        }

        val selectionArgs = when {
            videoDirectory.exists() -> arrayOf("%${videoDirectory.absolutePath}%")
            else -> arrayOf("%${videoDirectory.absolutePath}%")
        }

        val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"

        val cursor: Cursor? = requireContext().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        if (cursor == null) {
            Log.e("Videos", "Cursor is NULL.")
            return
        }

        cursor.use {
            val videoColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            Log.d("Videos", "Total videos import: ${cursor.count}")
            while (cursor.moveToNext()) {
                val videoPath = cursor.getString(videoColumnIndex)
                Log.d("Cursor", "Video Path import: $videoPath")

                val file = File(videoPath)
                val videoFileName = file.name

                if (videoPath.isNotEmpty()) {
                    val targetFilePath = "${Environment.getExternalStorageDirectory()}/DCIM/FlexVideo/$videoFileName"
                    val targetFile = File(targetFilePath)
                    val isMoved = file.copyTo(targetFile, true)
                    if (isMoved != null) {
                        Log.d("Videos", "Video imported successfully to FlexVideo album.")
                        val values = ContentValues().apply {
                            put(MediaStore.Video.Media.DATA, targetFilePath)
                            put(MediaStore.Video.Media.MIME_TYPE, "video/*")
                        }
                        requireContext().contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
                    } else {
                        Log.e("Videos", "Failed to import video to FlexVideo album.")
                    }
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("Kamera", "Berechtigung verweigert")
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayVideosFromGallery()
            } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("Videos", "Berechtigung verweigert")
            }
        } else if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                importVideosToGallery()
            } else {
                Log.e("Videos", "Permission denied for WRITE_EXTERNAL_STORAGE")
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val videoFile = getLatestVideoFileFromDirectory()
            if (videoFile != null) {
                displayVideosFromGallery()
            }
        }
    }

    private fun getLatestVideoFileFromDirectory(): File? {
        val storageDir = requireContext().getExternalFilesDir(null)?.absolutePath + "/FlexVideo"
        val storageDirFile = File(storageDir)
        val files = storageDirFile.listFiles()
        if (files != null && files.isNotEmpty()) {
            Arrays.sort(files) { file1, file2 ->
                val lastModified1 = file1.lastModified()
                val lastModified2 = file2.lastModified()
                lastModified2.compareTo(lastModified1)
            }

            return files[0]
        }
        return null
    }

}
