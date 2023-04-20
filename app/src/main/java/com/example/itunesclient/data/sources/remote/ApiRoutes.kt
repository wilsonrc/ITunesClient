package com.example.itunesclient.data.sources.remote

object ApiRoutes {
    private const val  BASE_URL = " https://itunes.apple.com/"
    const val SEARCH = "${BASE_URL}search"
    const val SEARCH_TERM_PARAM = "term"
    const val SEARCH_ENTITY_PARAM = "entity"
}