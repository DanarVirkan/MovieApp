package com.example.core.di

import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.Repository
import com.example.core.data.local.AppDatabase
import com.example.core.data.local.LocalSource
import com.example.core.data.remote.ApiService
import com.example.core.data.remote.RemoteSource
import com.example.core.domain.IRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory {
        get<AppDatabase>().bookmarkDao()
    }
    single {
        val pass: ByteArray = SQLiteDatabase.getBytes("moviedb".toCharArray())
        val factory = SupportFactory(pass)
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_db")
            .openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {
        val hostname = "themoviedb.org"
        val ssl = CertificatePinner.Builder()
            .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=").build()

        val header = Interceptor {
            val req =
                it.request().newBuilder().addHeader("Authorization", "Bearer ${ApiService.TOKEN}").build()
            it.proceed(req)
        }
        val client =
            OkHttpClient.Builder().addInterceptor(header).certificatePinner(ssl)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
        }
        val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).client(client.build()).build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalSource(get()) }
    single { RemoteSource(get()) }
    single<IRepository> {
        Repository(
            get(), get()
        )
    }
}