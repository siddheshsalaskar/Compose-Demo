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
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName


class AlgoliaViewModel : ViewModel() {
    var config: ConfigurationSearch = ConfigurationSearch(ApplicationID("JIE6P1H3VT"),APIKey("05787bab9b4dcc0a435e6ca951e3966d"),hosts = listOf(RetryableHost("uat-search.webuy.tools")))

   var client = ClientSearch(config)

    val index = client.initIndex(IndexName("uat_dex_es_new_arrival_desc"))
    val searcher = HitsSearcher(client,index.indexName)
    private val filters = filters {
    }

    val filterState = FilterState(filters)

    // Hits
   // val hitsPaginator = Paginator(searcher) { it.deserialize(AlgoliaProduct) }


    var connection = ConnectionHandler(searcher.connectFilterState(filterState))

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.clear()
    }
}