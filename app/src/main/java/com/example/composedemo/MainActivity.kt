package com.example.composedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.algolia.instantsearch.core.Callback
import com.algolia.search.helper.deserialize
import com.algolia.search.model.response.ResponseSearch
import com.example.composedemo.model.AlgoliaProduct
import com.example.composedemo.model.Banner
import com.example.composedemo.model.MainContent
import com.example.composedemo.ui.BottomNavigationBar
import com.example.composedemo.ui.CustomTopAppBar
import com.example.composedemo.ui.MainScreen
import com.example.composedemo.ui.search.SearchResultsScreen
import com.example.composedemo.ui.drawer.CustomDrawerContent
import com.example.composedemo.utils.Status
import com.example.composedemo.viewmodel.AlgoliaViewModel
import com.example.composedemo.viewmodel.BannerViewModel
import com.example.composedemo.viewmodel.ViewModelFactory

sealed class BottomNavItem(val title: String, val icon: Int) {
    object Home : BottomNavItem("Home", R.drawable.ic_home)
    object Favourites : BottomNavItem("Favourites", R.drawable.ic_favorite)
    object Basket : BottomNavItem("Basket", R.drawable.ic_basket)
    object Account : BottomNavItem("Account", R.drawable.ic_account)
}

class MainActivity : ComponentActivity() {

    private lateinit var bannerViewModel: BannerViewModel
    private lateinit var viewModel: AlgoliaViewModel
    private var productList by mutableStateOf<List<AlgoliaProduct>>(emptyList())
    private var bannerList by mutableStateOf<List<Banner>>(emptyList())


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bannerViewModel = ViewModelProvider(
            this,
            ViewModelFactory()
        )[BannerViewModel::class.java]
        viewModel = ViewModelProvider(this)[AlgoliaViewModel::class.java]
        viewModel.searcher.searchAsync()
        val callback: Callback<ResponseSearch?> = { response ->

            if (response?.nbHitsOrNull != null && response.nbHitsOrNull!! > 0) {
                productList = response.hits.deserialize(AlgoliaProduct.serializer())
            }
        }
        viewModel.searcher.response.subscribe(callback)



        getBannersContents()

        setContent {
            var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val productId = navBackStackEntry?.arguments?.getString("productId")
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            val currentRoute = currentBackStackEntry?.destination?.route
            val isOnProductDetail = currentRoute?.startsWith("productDetail") == true
            val isOnSearchResults = currentRoute?.startsWith("searchResults") == true

            val title = when {
                isOnSearchResults -> ""
                productId != null -> "BoxID: $productId"
                else -> when (selectedItem) {
                    is BottomNavItem.Home -> "Home"
                    is BottomNavItem.Favourites -> "Favourites"
                    is BottomNavItem.Basket -> "Basket"
                    is BottomNavItem.Account -> "Account"
                }
            }

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CustomDrawerContent {
//                        scope.launch { drawerState.close() }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        CustomTopAppBar(
                            title = title,
                            showDrawerIcon = !isOnProductDetail && !isOnSearchResults,
                            drawerState = drawerState,
                            scope = scope,
                            navController = navController
                        )
                    },
                    bottomBar = {
                        if (
                            currentRoute == "home" ||
                            currentRoute == "favourites" ||
                            currentRoute == "basket" ||
                            currentRoute == "account"
                        ) {
                            BottomNavigationBar(
                                selectedItem = selectedItem,
                                onItemSelected = { selectedItem = it }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        when (selectedItem) {
                            is BottomNavItem.Home -> NavHost(
                                navController = navController,
                                startDestination = "home"
                            ) {
                                composable("home") {
                                    MainScreen(
                                        products = productList,
                                        banners = bannerList,
                                        onProductClick = { productId ->
                                            navController.navigate("productDetail/$productId")
                                        }
                                    )
                                }
                                composable(
                                    "searchResults/{query}",
                                    arguments = listOf(navArgument("query") {
                                        type = NavType.StringType
                                    })
                                ) { backStackEntry ->
                                    val query = backStackEntry.arguments?.getString("query") ?: ""
                                    SearchResultsScreen(query = query)
                                }
                                composable(
                                    "productDetail/{productId}",
                                    arguments = listOf(navArgument("productId") {
                                        type = NavType.StringType
                                    })
                                ) {
//                                backStackEntry ->
//                                    val productId = backStackEntry.arguments?.getString("productId")
//                                    ProductDetailScreen(productId = productId ?: "Unknown")
                                }
                            }

                            is BottomNavItem.Favourites ->
                            Text(
                                "Favourites Screen",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize()
                            )

                            is BottomNavItem.Basket -> Text(
                                "Basket Screen",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize()
                            )

                            is BottomNavItem.Account -> Text(
                                "Account Screen",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getContents(): MutableList<String> {
        return mutableListOf<String>().apply {
            add("site/homepage/sliders/1-slider-system/1-cta-url")
            add("site/homepage/sliders/1-slider-system/2-cta-url")
            add("site/homepage/sliders/1-slider-system/3-cta-url")
            add("site/homepage/sliders/1-slider-system/1-file-name")
            add("site/homepage/sliders/1-slider-system/2-file-name")
            add("site/homepage/sliders/1-slider-system/3-file-name")
        }
    }

    private fun getBannersContents() {
        bannerViewModel.getNewContent(getContents()).observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        if (resource.data?.body() != null && resource.data.body()?.response?.data != null) {
                            val response = resource.data.body()?.response
                            val contentList = response?.data?.contents
                            bannerList = getBannerListData(contentList)
                            Log.d("bannerList", bannerList.toString())
                        }

                    }

                    Status.ERROR -> {
                        Log.d("Error", resource.message.toString())
                    }
                }
            }
        }
    }

}

fun formImageURL(imageName: String): String {
    val DOT = "."
    var filename = ""
    val ZERO = 0
    val ONE = 1
    if (!imageName.isNullOrEmpty()) {
        val imgArray = imageName.split(DOT)
        filename =
            "https://uk.static.designerexchange.com/content_images/sliders/" + imgArray[ZERO] + "." + imgArray[ONE]
    }
    return filename.trim()
}

fun getBannerListData(contentList: List<MainContent.ContentWrapper>?): MutableList<Banner> {
    val bannerList: MutableList<Banner> = mutableListOf()
    if (contentList != null) {
        for (data in contentList) {
            if (data.contentKey.contains("site/homepage/sliders/1-slider-system/1-file-name")) {
                val banner1 = formImageURL(data.content)
                bannerList.add(Banner(banner1, data.contentKey))
            }
            if (data.contentKey.contains("site/homepage/sliders/1-slider-system/2-file-name")) {
                val banner2 = formImageURL(data.content)
                bannerList.add(Banner(banner2, data.contentKey))
            }
            if (data.contentKey.contains("site/homepage/sliders/1-slider-system/3-file-name")) {
                val banner3 = formImageURL(data.content)
                bannerList.add(Banner(banner3, data.contentKey))
            }
        }
        return bannerList
    }
    return bannerList
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMainScreen() {
//    MainScreen(products = emptyList(), banners = emptyList())
//}