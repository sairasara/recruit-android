package nz.co.test.transactions.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import javax.inject.Inject

class NetworkUtils @Inject constructor(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var isNetworkAvailable = false

    init {
        // Initial network state check
        checkNetworkConnection()
        registerNetworkCallback()
    }

    private fun registerNetworkCallback() {

        try {
            // Register a network callback to listen for changes
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isNetworkAvailable = true
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    isNetworkAvailable = false
                }
            })
        } catch (e: Exception) {
            Log.e("", e.printStackTrace().toString())
        }
    }

    fun isInternetAvailable(): Boolean {
        return isNetworkAvailable
    }

    private fun checkNetworkConnection() {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        isNetworkAvailable = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}