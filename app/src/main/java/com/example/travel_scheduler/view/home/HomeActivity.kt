package com.example.travel_scheduler.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.travel_scheduler.R
import com.example.travel_scheduler.firebase.FirebaseSignOut
import com.example.travel_scheduler.view.home.image_container.adapter.SliderAdapter
import com.example.travel_scheduler.view.home.image_container.model.SliderItem
class HomeActivity : AppCompatActivity() {

    //lateinit var signOutBtn: Button
    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.viewpager_images)
    }

    private val logOutFirebase: FirebaseSignOut by lazy {
        FirebaseSignOut(applicationContext)
    }

    private val sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //signOutBtn = findViewById(R.id.signOut)

        setHomeTitleBar()

        val sliderItems: MutableList<SliderItem> = ArrayList()
        setImages(sliderItems)

        viewPager.adapter = SliderAdapter(sliderItems,viewPager)
        val compositePageTransformer = CompositePageTransformer()

        compositeTransformerAdders(compositePageTransformer)
        viewPagerSetter(viewPager,compositePageTransformer)

/*        signOutBtn.setOnClickListener{
            logOutFirebase.signOut()
        }*/
    }

    private val sliderRunnable = Runnable{
        viewPager.currentItem = viewPager.currentItem + 1
    }

    private fun setHomeTitleBar(){
        supportActionBar?.title = "Home"
    }

    private fun setImages(sliderItems: MutableList<SliderItem> = ArrayList()){
        sliderItems.add(SliderItem(R.drawable.image1))
        sliderItems.add(SliderItem(R.drawable.image2))
        sliderItems.add(SliderItem(R.drawable.image3))
        sliderItems.add(SliderItem(R.drawable.image4))
        sliderItems.add(SliderItem(R.drawable.image5))
        sliderItems.add(SliderItem(R.drawable.image6))
    }

    private fun viewPagerSetter(viewPager: ViewPager2,compositePageTransformer: CompositePageTransformer){
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        viewPager.setPageTransformer(compositePageTransformer)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable,2500)
            }
        })

    }
    private fun compositeTransformerAdders(compositePageTransformer: CompositePageTransformer){
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer{page,position ->
            run {
                val r = 1 - kotlin.math.abs(position)
                page.scaleY = 0.85f + r * 0.25f

            }
        }
    }
}