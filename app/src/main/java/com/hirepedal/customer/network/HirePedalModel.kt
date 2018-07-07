package com.elyeproj.wikisearchcount

object HirePedalModel {
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
    data class AllCustomerResult(val result:String )
}
