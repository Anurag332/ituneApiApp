package com.anurag.ituneapiapp.repositories

import com.anurag.ituneapiapp.data.ApiResponse

interface RepositoryListener {
    fun onSuccess(res: ApiResponse, of: String)
}