package com.app.zovent.utils
//
//import android.app.Activity
//import android.app.AlertDialog
//import android.app.DatePickerDialog
//import android.app.DownloadManager
//import android.content.Context
//import android.content.DialogInterface
//import android.content.Intent
//import android.graphics.Bitmap
//import android.location.Address
//import android.location.Geocoder
//import android.net.Uri
//import android.os.Build
//import android.os.Environment
//import android.util.Log
//import android.view.View
//import android.view.Window
//import android.view.inputmethod.InputMethodManager
//import android.widget.DatePicker
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.widget.AppCompatTextView
//import androidx.databinding.BindingAdapter
//import androidx.fragment.app.Fragment
//import com.app.blink_agent.R
//import com.app.blink_agent.ui.main.activity.MainActivity
//import com.app.blink_agent.ui.main.fragment.LogoutDailogFragment
//import com.app.blink_agent.utils.encryption_decription.AESHelper
//import com.app.zovent.utils.Session
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.RequestOptions
//import com.google.android.gms.maps.model.Marker
//import com.google.android.material.snackbar.Snackbar
//import com.google.gson.Gson
//import de.hdodenhof.circleimageview.CircleImageView
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.FileOutputStream
//import java.io.UnsupportedEncodingException
//import java.security.InvalidAlgorithmParameterException
//import java.security.InvalidKeyException
//import java.security.NoSuchAlgorithmException
//import java.text.DecimalFormat
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.concurrent.TimeUnit
//import javax.crypto.BadPaddingException
//import javax.crypto.IllegalBlockSizeException
//import javax.crypto.NoSuchPaddingException
//
//
//object CommonUtils {
//
//
//    fun decryptData(response:String): String? {
//        var dec = AESHelper.decrypt(Constants.KEY ,response)
//        Log.d("jhgfds",""+dec)
//        return dec;
//    }
//     fun <T> encryptRequest(`object`: T): HashMap<String, String>? {
//        val requestMap: HashMap<String, String> = HashMap()
//        var encryptedRequest = ""
//        try {
//            encryptedRequest = AESHelper.encrypt(Constants.KEY, Gson().toJson(`object`))
//            requestMap["reqData"] = encryptedRequest
//        } catch (e: UnsupportedEncodingException) {
//            e.printStackTrace()
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//        } catch (e: NoSuchPaddingException) {
//            e.printStackTrace()
//        } catch (e: InvalidAlgorithmParameterException) {
//            e.printStackTrace()
//        } catch (e: InvalidKeyException) {
//            e.printStackTrace()
//        } catch (e: BadPaddingException) {
//            e.printStackTrace()
//        } catch (e: IllegalBlockSizeException) {
//            e.printStackTrace()
//        }
//        return requestMap
//    }
//
//    fun logoutAlert(
//        activity: Activity,
//        context: Context,
//        title: String,
//        message: String,
//    ) {
//        val alert: AlertDialog = AlertDialog.Builder(context)
//            .create()
//        alert.setTitle(title)
//        alert.setMessage(message)
//        alert.setCanceledOnTouchOutside(false);
//        alert.setCancelable(false);
//
//
//        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
//          LogoutDailogFragment.isLogout=true
//            Session.deleteToken(Constants.TOKEN)
//            Preferences.removePreference(context, PreferenceEntity.ISLOGIN)
//            Log.d("delete", "" + Session.token)
//            var intent = Intent(context, MainActivity::class.java)
//            context.startActivity(intent)
//            activity!!.finish()
//
//
//        }
//
//        alert.show()
//    }
//
//    /*    fun logoutAlert(context: Context, title: String, message: String) {
//            val alert: AlertDialog = AlertDialog.Builder(context)
//                .create()
//            alert.setTitle(title)
//            alert.setMessage(message)
//            alert.setCanceledOnTouchOutside(false);
//            alert.setCancelable(false);
//
//
//            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
//                dialog.dismiss()
//
//
//            }
//
//            alert.show()
//        }*/
//    fun makeTransparentStatusBar(activity: Activity, isTransparent: Boolean) {
//        if (isTransparent) {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                val window: Window = activity.window
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    window.statusBarColor = activity.getColor(R.color.purple_splash)
//                }
//                window.decorView.systemUiVisibility = 0
//            }
//        } else {
//            val window: Window = activity.window
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                window.statusBarColor = activity.getColor(R.color.white)
//            }
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
//    }
//
//    fun setFirstName(name: String): String {
//        val currentString = name
//        val separated = currentString.split(" ").toTypedArray()
//        val firstName = separated[0] // this will contain "Fruit"
//        return firstName
//    }
//
//    fun DownloadFile(
//        context: Context,
//        URL: String?,
//        fileName: String?,
//        fileNameExtension: String?
//    ) {
//        Log.w("URL-->", URL!!)
//        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val uri = Uri.parse(URL)
//        val request = DownloadManager.Request(uri)
//        request.setTitle(fileName)
//        request.setDescription("Downloading...")
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
//        request.setAllowedNetworkTypes(
//            DownloadManager.Request.NETWORK_WIFI
//                    or DownloadManager.Request.NETWORK_MOBILE
//        )
//            .setAllowedOverRoaming(false).setTitle(fileName)
//            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileNameExtension)
//        downloadmanager.enqueue(request)
//        Toast.makeText(
//            context,
//            "Downloading File...",  //To notify the Client that the file is being downloaded
//            Toast.LENGTH_LONG
//        ).show()
//    }
//
//    fun getImageUri(inContext: Context, inImage: Bitmap): File {
//
//        //create a file to write bitmap data
//        val f = File(inContext.cacheDir, "filename.jpg");
//        f.createNewFile()
//
//        //Convert bitmap to byte array
//        val bitmap = inImage
//        val bos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//        val bitmapdata = bos.toByteArray()
//
//        //write the bytes in file
//        val fos = FileOutputStream(f)
//        fos.write(bitmapdata);
//        fos.flush()
//        fos.close()
//        return f
//    }
//
//    fun getCurrentDateAndTime(): String {
//        val c = Calendar.getInstance().time
//        println("Current time => $c")
//
//        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
//        val formattedDate = df.format(c)
//        return formattedDate
//    }
//
//
//    fun setPaymentTypes(transactionType: String, isSend: Boolean): CharSequence {
//        when (transactionType) {
//            "payment" -> {
//                return if (isSend) "Paid to" else "Receive from"
//            }
//            "transaction" -> {
//                return if (isSend) "Transfer to" else "Receive transfer"
//            }
//            "bonus" -> {
//                return if (isSend) "Bonus from" else "Bonus from"
//            }
//            "refund" -> {
//                return if (isSend) "Refund to" else "Refund from"
//            }
//            "cash-in" -> {
//                return if (isSend) "Cash-In to" else "Cash-In to"
//            }
//            "cash-out" -> {
//                return if (isSend) "Cash-Out to" else "Cash-Out to"
//            }
//            "cashback" -> {
//                return if(isSend) "Cancelled Bonus  " else "Bonus from"
//            }
//            "commission"->{
//                return if (isSend) "Commission paid" else " Commission Refund"
//            }
//        }
//        return ""
//    }
//
//    fun openShareBottomSheet(context: Activity, uri: Uri) {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.putExtra(Intent.EXTRA_STREAM, uri)
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        intent.type = "image/png"
//        context.startActivity(intent)
//    }
//
//    fun screenshot(view: View, filename: String): Bitmap? {
//
//        try {
//            // Initialising the directory of storage
//            view.isDrawingCacheEnabled = true
//            val bitmap = Bitmap.createBitmap(view.drawingCache)
//            view.isDrawingCacheEnabled = false
//
//            return bitmap
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    fun Fragment.logout() {
//        //  Session.logout()
//        // clear all preference
//        startActivity(Intent(requireContext(), MainActivity::class.java).putExtra("data", "yes"))
//        requireActivity().finish()
//
//
//    }
//
//    fun navigateToGoogleMap(context: Context, marker: Marker) {
//
//        val intent = Intent(
//            Intent.ACTION_VIEW,
//            Uri.parse("http://maps.google.com/maps?daddr=${marker.position.latitude},${marker.position.longitude}")
//        )
//        context.startActivity(intent)
//
//    }
//
//    fun openDatePicker(
//        context: Context,
//        startYear: Int = Calendar.getInstance()[Calendar.YEAR],
//        startMonth: Int = Calendar.getInstance()[Calendar.MONTH],
//        startDay: Int = Calendar.getInstance()[Calendar.DAY_OF_MONTH],
//        minDate: Long = Calendar.getInstance().timeInMillis,
//        maxDate: Long = Calendar.getInstance().timeInMillis,
//        isMinDate: Boolean = false,
//        isMaxDate: Boolean = false,
//        dateFormat: String = "dd-MM-yyyy",
//        listener: (date: String) -> Unit
//    ) {
//
//        //Define Picker
//        val datePickerDialog = DatePickerDialog(
//            context,
//            { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
//                Log.w("TAG", "onDateSet: $year$monthOfYear$dayOfMonth")
//
//                try {
//                    val dateInput = SimpleDateFormat("dd-MM-yyyy")
//                    val dateOutput = SimpleDateFormat(dateFormat)
//
//                    val date = dateInput.parse("$dayOfMonth-${monthOfYear + 1}-$year")
//                    Log.e("TAG", "openDatePicker: " + date.date)
//                    Log.e("TAG", "openDatePicker: " + date.month)
//                    val dateStr = dateOutput.format(date)
//                    listener(dateStr)
//
//                } catch (e: Exception) {
//                    Log.e("TAG", "openDatePicker: " + e.localizedMessage)
//                }
//
//            }, startYear, startMonth, startDay
//        )
//
//
//        //No Min Date.
//        if (isMinDate) {
//            datePickerDialog.datePicker.minDate = minDate
//        }
//
//        if (isMaxDate) {
//            datePickerDialog.datePicker.maxDate = maxDate
//        }
//
//        datePickerDialog.show()
//    }
//
//    fun getAddressFromLatLng(context: Context?, latitude: Double, longitude: Double): Address? {
//        //Set Address
//        try {
//            val geocoder = Geocoder(context, Locale.getDefault())
//            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
//            if (addresses != null && addresses.isNotEmpty()) {
//                val address: String =
//                    addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                val city: String = addresses[0].locality
//                val state: String = addresses[0].adminArea
//                val country: String = addresses[0].countryName
//                val postalCode: String = addresses[0].postalCode
//                val phone = addresses[0].phone
//                val premises = addresses[0].premises
//                val subLocality = addresses[0].subLocality
//                val extras = addresses[0].extras
//                val knownName: String =
//                    addresses[0].featureName // Only if available else return NULL
//                Log.d("TAG", "getAddress:  address: $address")
//                Log.d("TAG", "getAddress:  country: $country")
//                Log.d("TAG", "getAddress:  city: $city")
//                Log.d("TAG", "getAddress:  state: $state")
//                Log.d("TAG", "getAddress:  postalCode: $phone")
//                Log.d("TAG", "getAddress:  knownName: $premises")
//                Log.d("TAG", "getAddress:  knownName: $postalCode")
//                return addresses[0]
//            }
//        } catch (e: Exception) {
//            Log.e("Exception:", "getAddressFromLatLng: ${e.localizedMessage}")
//            Log.e("Exception:", "getAddressFromLatLng: ${e.message}")
//        }
//        return null
//    }
//
//    fun Fragment.hideKeyboard() {
//        view?.let { activity?.hideKeyboard(it) }
//    }
//
//    fun Context.hideKeyboard(view: View) {
//        val inputMethodManager =
//            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//    fun Fragment.showKeyboard() {
//        view?.let { activity?.showKeyboard(it) }
//    }
//
//    fun Context.showKeyboard(view: View) {
//        val inputMethodManager =
//            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(
//            view.windowToken,
//            InputMethodManager.SHOW_IMPLICIT
//        )
//    }
//
//
//    fun setImage(url: String, context: Context, view: ImageView) {
//
//        Glide.with(view)
//            .load(url)
//            .apply(
//                RequestOptions.placeholderOf(R.drawable.ic_user_placeholder).dontAnimate()
//                    .error(R.drawable.ic_user_placeholder)
//            )
//            .into(view)
//
//    }
//
//    @BindingAdapter("android:circularImageUrl")
//    @JvmStatic
//    fun loadImage(view: View?, image_url: String?) {
//        val imageView = view as CircleImageView?
//        Glide.with(view!!)
//            .load(image_url)
//            .apply(
//                RequestOptions.placeholderOf(R.drawable.ic_user_placeholder).dontAnimate()
//                    .error(R.drawable.ic_user_placeholder)
//            )
//            .into(imageView!!)
//    }
//
//    @JvmStatic
//    @BindingAdapter("android:imageViewUrl")
//    fun loadNormalImage(view: View?, image_url: String?) {
//        val imageView = view as ImageView?
//        Glide.with(view!!)
//            .load(image_url).apply(
//                RequestOptions.placeholderOf(R.drawable.ic_qr_img_icon).dontAnimate()
//                    .error(R.drawable.ic_qr_img_icon)
//            )
//            .into(imageView!!)
//    }
//
//    fun Fragment.showSnackBar(message: String) {
//
//        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
//            .show()
//
//    }
//
//    fun Fragment.showToast(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
//    }
//
//    fun convertTimeStampToDate(date: String?): String? {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        val outputFormat = SimpleDateFormat("dd MMMM yyyy")
//        inputFormat.setTimeZone(TimeZone.getTimeZone("IST"));
//        var dateTimeStamp: Date? = null
//        try {
//            dateTimeStamp = inputFormat.parse(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        var formattedDate: String? = null
//        if (dateTimeStamp != null) {
//            formattedDate = outputFormat.format(dateTimeStamp)
//        }
//        return formattedDate
//    }
//
//    fun convertTimeStampToTime(date: String?): String? {
//        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        val outputFormat = SimpleDateFormat("hh:mm a")
//        inputFormat.setTimeZone(TimeZone.getTimeZone("IST"));
//        var dateTimeStamp: Date? = null
//        try {
//            dateTimeStamp = inputFormat.parse(date)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        var formattedDate: String? = null
//        if (dateTimeStamp != null) {
//            formattedDate = outputFormat.format(dateTimeStamp)
//        }
//        return formattedDate
//    }
//
//    fun timeAgo(dataDate: String?): String? {
//        var convTime: String? = null
//        val prefix = ""
//        val suffix = "Ago"
//        try {
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            val pasTime = dateFormat.parse(dataDate)
//            val nowTime = Date()
//            val dateDiff = nowTime.time - pasTime.time
//            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
//            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
//            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
//            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
//
//            if (second < 60) {
//                convTime = "$second Seconds $suffix"
//            } else if (minute < 60) {
//                convTime = "$minute Minutes $suffix"
//            } else if (hour < 24) {
//                convTime = "$hour Hours $suffix"
//            } else if (day >= 7) {
//                convTime = if (day > 360) {
//                    (day / 360).toString() + " Years " + suffix
//                } else if (day > 30) {
//                    (day / 30).toString() + " Months " + suffix
//                } else {
//                    (day / 7).toString() + " Week " + suffix
//                }
//            } else if (day < 7) {
//                convTime = "$day Days $suffix"
//            }
//
//            if (day.toDouble() == 0.0) {
//                return "Today"
//            } else if (day.toDouble() == 1.0) {
//                return "Yesterday"
//            }
////            else{
////                return    convertTimeStampToDate(dataDate.toString(),CONSTRAINTS.UTC_FORMAT,"dd MMM, hh:mm a")
////            }
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.e("ConvTimeE", e.message!!)
//        }
//
//        return convTime
//    }
//
//
//}
//
//@BindingAdapter("android:setDateFromTimeStamp")
//fun setDateFromTimeStamp(textView: AppCompatTextView, date: String) {
//    textView.text = notificationDate(date)
//}
//
//fun notificationDate(date: String): String {
//    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
//    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
//    inputFormat.timeZone = TimeZone.getTimeZone("IST")
//
//    try {
//        val dateParse = inputFormat.parse(date)
//
//        val dateF = dateFormat.format(dateParse)
//        val timeF = timeFormat.format(dateParse)
//        return "${CommonUtils.timeAgo(date)} at ${timeF}"
//
//    } catch (e: Exception) {
//        Log.e("Exception: ", "notificationDate: ${e.localizedMessage}")
//    }
//    return ""
//}
//
//@BindingAdapter("android:decimalText")
//fun setDecimalValue(view: TextView, value: String) {
//    var amount = value.toDouble()
//    view.text = DecimalFormat("##.###").format(amount);
//}