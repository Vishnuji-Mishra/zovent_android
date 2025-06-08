package com.app.zovent.utils.network

 sealed  class NetworkStatus {
     object Available : NetworkStatus()
     object Unavailable : NetworkStatus()
}
