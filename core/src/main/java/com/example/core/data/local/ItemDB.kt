package com.example.core.data.local

abstract class ItemDB {
    abstract val id: Int
    abstract val poster: String
    abstract val name: String?
    abstract val title: String?
    abstract val vote: String
}