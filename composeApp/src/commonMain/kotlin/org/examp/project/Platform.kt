package org.examp.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform