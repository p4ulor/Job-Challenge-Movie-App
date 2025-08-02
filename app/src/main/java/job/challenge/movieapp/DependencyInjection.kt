package job.challenge.movieapp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import job.challenge.movieapp.android.utils.NetworkObserver
import job.challenge.movieapp.android.viewmodels.MovieDetailsViewModel
import job.challenge.movieapp.android.viewmodels.MovieListViewModel
import job.challenge.movieapp.data.repositories.MoviesRepository
import job.challenge.movieapp.data.repositories.TheMovieDbRepository
import job.challenge.movieapp.data.usecases.MovieListUseCase
import job.challenge.movieapp.data.usecases.MovieDetailsUseCase
import javax.inject.Singleton

/**
 * https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules
 */
@Module
@InstallIn(SingletonComponent::class) // Dependencies will live as long as the Application
object DependencyInjection {

    @Provides
    @Singleton
    fun theMovieDbRepository(@ApplicationContext ctx: Context) : MoviesRepository {
        return TheMovieDbRepository(ctx)
    }

    @Provides
    @Singleton
    fun moviesListUseCase(repo: TheMovieDbRepository) = MovieListUseCase(repo)

    @Provides
    @Singleton
    fun movieDetailsUseCase(repo: TheMovieDbRepository) = MovieDetailsUseCase(repo)

    @Provides
    @Singleton
    fun networkObserver(@ApplicationContext ctx: Context) = NetworkObserver(ctx)

/*    @Provides
    @Singleton
    fun movieListViewModel(
        @ApplicationContext ctx: Context,
        movieListUseCase: MovieListUseCase,
        networkObserver: NetworkObserver
    ) = MovieListViewModel(ctx, movieListUseCase, networkObserver)*/
}