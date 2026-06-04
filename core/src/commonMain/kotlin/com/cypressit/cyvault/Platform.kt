package com.cypressit.cyvault

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform