package app.foodbear.foodbearapp

import android.app.Activity
import android.app.AlertDialog

class loadingDialog(private val activity: Activity) {

    private var dialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.carga, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog!!.dismiss()
    }
}