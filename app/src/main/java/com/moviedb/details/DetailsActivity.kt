package com.moviedb.details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.core.data.remote.ApiService.Companion.IMG_URL
import com.example.core.domain.Genre
import com.example.core.domain.Item
import com.example.core.utils.Constant.ITEM
import com.example.core.utils.Constant.TYPE
import com.google.android.material.snackbar.Snackbar
import com.moviedb.R
import com.moviedb.databinding.ActivityDetailsBinding
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@InternalCoroutinesApi
class DetailsActivity : AppCompatActivity() {
    private val viewModel: DetailsViewModel by viewModel()
    private lateinit var itemDB: Item
    private var booked = false
    private var loaded = false
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(android.R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))

        val view = listOf(
            binding.appbarContent.detailPoster,
            binding.appbarContent.detailTitle,
            binding.appbarContent.detailRelease,
            binding.detailGenre,
            binding.appbarContent.detailRating,
            binding.appbarContent.detailTime,
            binding.appbarContent.detailTagline,
            binding.detailOverview
        )
        startAnim(view)

        itemDB = intent.getSerializableExtra(ITEM) as Item

        if (savedInstanceState == null) {
            viewModel.getData(intent.getStringExtra(TYPE), itemDB.id)
        }

        viewModel.getMovie().observe(this, {
            binding.appbarContent.detailEpisode.visibility = View.GONE
            binding.appbarContent.detailSeason.visibility = View.GONE
            Glide.with(this).load(IMG_URL + it.poster)
                .placeholder(R.drawable.loading_placeholder)
                .error(R.drawable.error_placeholder)
                .override(500, 750)
                .into(binding.appbarContent.detailPoster)
            clearAnim(view)
            binding.appbarContent.detailTitle.text = it.title
            binding.toolbarTitle.title = it.title
            binding.appbarContent.detailRelease.text = it.release_date.toRelease()
            binding.detailGenre.text = it.genre.toGenres()
            binding.appbarContent.detailRating.text = it.vote
            binding.appbarContent.detailTime.text =
                resources.getString(R.string.runtime, it.runtime)
            binding.appbarContent.detailTagline.text = it.tagline
            binding.appbarContent.detailTagline.visibility =
                if (it.tagline.isEmpty()) View.GONE else View.VISIBLE
            binding.detailOverview.text = it.overview
            binding.detailOverview.height = ViewGroup.LayoutParams.WRAP_CONTENT
            booked = it.isBookmarked
            loaded = true
            if (booked) {
                binding.fabBookmark.setImageDrawable(resources.getDrawable(R.drawable.bookmarked))
            }
        })

        viewModel.getTV().observe(this, {
            binding.appbarContent.detailTime.visibility = View.GONE
            binding.appbarContent.detailTagline.visibility = View.GONE
            Glide.with(this).load(IMG_URL + it.poster)
                .placeholder(R.drawable.loading_placeholder)
                .error(R.drawable.error_placeholder)
                .override(500, 750)
                .into(binding.appbarContent.detailPoster)
            clearAnim(view)
            binding.appbarContent.detailTitle.text = it.name
            binding.toolbarTitle.title = it.name
            binding.appbarContent.detailRelease.text = it.air_date.toRelease()
            binding.detailGenre.text = it.genre.toGenres()
            binding.appbarContent.detailRating.text = it.vote
            binding.detailOverview.text = it.overview
            binding.appbarContent.detailSeason.text =
                resources.getQuantityString(R.plurals.season, it.season, it.season)
            binding.appbarContent.detailEpisode.text =
                resources.getQuantityString(R.plurals.episode, it.episode, it.episode)
            booked = it.isBookmarked
            loaded = true
            if (booked) {
                binding.fabBookmark.setImageDrawable(resources.getDrawable(R.drawable.bookmarked))
            }
        })


        binding.fabBookmark.setOnClickListener {
            if (loaded) {
                if (!booked) {
                    viewModel.updateBookmark(itemDB, true)
                    Snackbar.make(
                        binding.snackParent,
                        getString(R.string.bookmarked),
                        Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(getColor(R.color.colorSuccess)).show()
                    binding.fabBookmark.setImageDrawable(resources.getDrawable(R.drawable.bookmarked))
                    booked = true
                } else {
                    viewModel.updateBookmark(itemDB, false)
                    Snackbar.make(
                        binding.snackParent,
                        getString(R.string.remove_bookmark),
                        Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(getColor(R.color.colorPrimary)).show()
                    binding.fabBookmark.setImageDrawable(resources.getDrawable(R.drawable.bookmark))
                    booked = false
                }
            }
        }

        viewModel.getError().observe(this, {
            if (it == true) {
                FancyToast.makeText(
                    this,
                    getString(R.string.net_err),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.ERROR,
                    R.drawable.no_internet,
                    false
                ).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 1500L)
            }
        })

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean("BOOL", true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun startAnim(list: List<View>) {
        list.map {
            it.startAnimation(AnimationUtils.loadAnimation(this@DetailsActivity, R.anim.flashing))
        }
    }

    private fun clearAnim(view: List<View>) {
        binding.detailProgress.visibility = View.GONE
        clearItem(view)
    }

    private fun clearItem(view: List<View>) {
        view.map {
            it.clearAnimation()
            it.setBackgroundResource(0)
        }
    }

    private fun List<Genre>.toGenres(): String {
        var genre = ""
        this.forEach { i ->
            genre += if (i == this.last()) {
                i.name
            } else {
                "${i.name}, "
            }
        }
        return genre
    }

    private fun String.toRelease(): String {
        val splitting = this.split("-")
        val year = splitting[0]
        val month = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "Mei",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Okt",
            "Nov",
            "Des"
        )[splitting[1].toInt() - 1]
        val date = splitting[2]
        return "Release : $date $month $year"
    }

}

