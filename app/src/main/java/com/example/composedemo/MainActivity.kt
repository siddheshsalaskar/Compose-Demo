package com.example.composedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.algolia.instantsearch.core.Callback
import com.algolia.search.helper.deserialize
import com.algolia.search.model.response.ResponseSearch
import com.example.composedemo.model.AlgoliaProduct
import com.example.composedemo.model.Banner
import com.example.composedemo.model.MainContent
import com.example.composedemo.utils.Status
import com.example.composedemo.viewmodel.AlgoliaViewModel
import com.example.composedemo.viewmodel.BannerViewModel
import com.example.composedemo.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var bannerViewModel: BannerViewModel
    private lateinit var viewModel: AlgoliaViewModel
    private var productList by mutableStateOf<List<AlgoliaProduct>>(emptyList())
    private var bannerList by mutableStateOf<List<Banner>>(emptyList())


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
            MainScreen(products = productList, banners = bannerList)
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

@Composable
fun MainScreen(products: List<AlgoliaProduct>, banners: List<Banner>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        item { BannerCarousel(banners) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { NewArrivalsSection() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ProductCarousel(products) }
    }
}

@Composable
fun BannerCarousel(banners: List<Banner>) {
    val pagerState = rememberPagerState(pageCount = { banners.size })
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        HorizontalPager(state = pagerState) { page ->
            Image(
                painter = rememberImagePainter(data = banners[page].content),
                contentDescription = "Banner Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            repeat(banners.size) { index ->
                val color = if (pagerState.currentPage == index) Color.Black else Color.LightGray
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .padding(4.dp)
                        .background(color = color, shape = RoundedCornerShape(50))
                )
            }
        }
    }
}

@Composable
fun NewArrivalsSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New Arrivals",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006D5B)
        )
    }
}

@Composable
fun ProductCard(product: AlgoliaProduct) {
    var isWishlisted by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(400.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(data = product.imageUrls?.medium),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp),
                    contentScale = ContentScale.Fit
                )
                IconButton(
                    onClick = { isWishlisted = !isWishlisted },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(id = if (isWishlisted) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24),
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) Color(0xFF004D40) else Color(0xFF004D40)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = product.superCatFriendlyName.toString(),
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.boxName.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "£${product.sellPrice}",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF006D5B),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductCarousel(products: List<AlgoliaProduct>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(products) { product ->
            ProductCard(product = product)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen(products = emptyList(), banners = emptyList())
}