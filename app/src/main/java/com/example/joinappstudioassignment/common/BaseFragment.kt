package com.example.joinappstudioassignment.common

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.joinappstudioassignment.utils.LoadingDialog
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentActivity = activity ?: return
        loadingDialog = LoadingDialog(currentActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews(savedInstanceState)
    }

    // Provide a default empty implementation, so it is optional in child fragments
    open fun setUpViews(savedInstanceState: Bundle?) {
        // Optional setup
    }

    fun showLoading(show: Boolean) {
        if (show) loadingDialog?.show() else loadingDialog?.dismiss()
    }

    protected fun navigate(@IdRes destinationId: Int) {
        try {
            findNavController().navigate(destinationId)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying to perform navigation action: $e")
        }
    }

    protected fun navigateWithData(@IdRes destinationId: Int, bundle: Bundle) {
        try {
            findNavController().navigate(destinationId, bundle)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying to perform navigation action: $e")
        }
    }

    protected fun navigate(direction: NavDirections) {
        try {
            findNavController().navigate(direction)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying to perform navigation action: $e")
        }
    }

    protected fun navigateBack() {
        try {
            findNavController().popBackStack()
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying to perform navigation action: $e")
        }
    }

    protected fun navigateBack(@IdRes destinationId: Int, inclusive: Boolean = false) {
        try {
            findNavController().popBackStack(destinationId, inclusive)
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Error while trying to perform navigation action: $e")
        }
    }
}
