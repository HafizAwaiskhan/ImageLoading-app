package com.example.joinappstudioassignment.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.joinappstudioassignment.R
import com.example.joinappstudioassignment.utils.LoadingDialog
import timber.log.Timber


abstract class BaseActivity : AppCompatActivity(){

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge to edge support
        window.decorView.fitsSystemWindows = true
        loadingDialog = LoadingDialog(this)
    }

    /**
     * Show loading view
     * Child view should override this method for specific realisation
     */
        fun showLoading(show: Boolean) {
        if (show) loadingDialog?.show() else loadingDialog?.dismiss()
    }

    /**
     * Navigate to the new destination by the provided destination id
     *
     * @param destinationId destination id redirect to
     */
    protected fun navigate(@IdRes destinationId: Int) {
        try {
            findNavController(R.id.navHostFragment).navigate(destinationId)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying preform navigation action: $e")
        }
    }

    /**
     * Navigate to the provided destination
     *
     * @param direction destination redirect to
     */
    protected fun navigate(direction: NavDirections) {
        try {
            findNavController(R.id.navHostFragment).navigate(direction)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying preform navigation action: $e")
        }
    }

    /**
     * Navigate to the previous destination
     */
    protected fun navigateBack() {
        try {
            findNavController(R.id.navHostFragment).popBackStack()
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying preform navigation action: $e")
        }
    }


}