package dev.partemy.shlist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform