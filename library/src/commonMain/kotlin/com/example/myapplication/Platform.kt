package com.example.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform