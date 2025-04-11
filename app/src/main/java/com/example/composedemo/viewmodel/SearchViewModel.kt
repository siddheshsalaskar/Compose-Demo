package com.example.composedemo.viewmodel

import androidx.lifecycle.ViewModel
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.filter.state.FilterState
import com.algolia.instantsearch.filter.state.filters
import com.algolia.instantsearch.searcher.connectFilterState
import com.algolia.instantsearch.searcher.hits.HitsSearcher
import com.algolia.search.client.ClientSearch
import com.algolia.search.configuration.ConfigurationSearch
import com.algolia.search.configuration.RetryableHost
import com.algolia.search.helper.deserialize
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.example.composedemo.model.AlgoliaProduct


class SearchViewModel : ViewModel() {
    private var config: ConfigurationSearch = ConfigurationSearch(
        ApplicationID("JIE6P1H3VT"),
        APIKey("05787bab9b4dcc0a435e6ca951e3966d"),
        hosts = listOf(RetryableHost("uat-search.webuy.tools"))
    )

    private var client = ClientSearch(config)

    private val index = client.initIndex(IndexName("uat_dex_uk"))
    val searcher = HitsSearcher(client, index.indexName)
    private val filters = filters {
    }
    private val filterState = FilterState(filters)
    private var connection = ConnectionHandler(searcher.connectFilterState(filterState))

    fun search(query: String, onResults: (List<AlgoliaProduct>) -> Unit) {
        searcher.setQuery(query)

        searcher.response.subscribe { response ->
            if (response?.nbHitsOrNull != null && response.nbHitsOrNull!! > 0) {
                val results = response.hits.deserialize(AlgoliaProduct.serializer())
                onResults(results)
            } else {
                onResults(emptyList())
            }
        }

        searcher.searchAsync()
    }


    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.clear()
    }
}