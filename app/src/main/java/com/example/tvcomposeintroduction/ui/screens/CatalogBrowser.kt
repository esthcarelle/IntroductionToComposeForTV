import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.tvcomposeintroduction.R
import com.example.tvcomposeintroduction.data.Movie
import com.example.tvcomposeintroduction.ui.components.MovieCard
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserViewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CatalogBrowser(
    modifier: Modifier = Modifier,
    catalogBrowserViewModel: CatalogBrowserViewModel = viewModel(),
    onMovieSelected: (Movie) -> Unit = {}
) {
    val categoryList by
    catalogBrowserViewModel.categoryList.collectAsState()
    TvLazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 48.dp, vertical = 32.dp)
    ) {
        item {
            val featuredMovieList by catalogBrowserViewModel.featuredMovieList.collectAsState()
            Carousel(
                itemCount = featuredMovieList.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(376.dp),
            ) { indexOfCarouselItem ->
                val featuredMovie = featuredMovieList[indexOfCarouselItem]
                CarouselSlide(
                    background = {
                        AsyncImage(
                            model = featuredMovie.backgroundImageUrl,
                            contentDescription = null,
                            placeholder = painterResource(
                                id = R.drawable.placeholder
                            ),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                    },
                    modifier = Modifier.clickable { onMovieSelected(featuredMovie) }
                ) {
                    Text(text = featuredMovie.title)
                }
            }
        }
        items(categoryList) { category ->
            Text(text = category.name)
            TvLazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(category.movieList) { movie ->
                    MovieCard(movie = movie, onClick = { onMovieSelected(movie) })
                }
            }
        }
    }
}