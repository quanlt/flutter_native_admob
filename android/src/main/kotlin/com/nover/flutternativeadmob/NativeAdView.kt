package com.nover.flutternativeadmob

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView

enum class NativeAdmobType {
  full, banner
}

class NativeAdView : FrameLayout {

  constructor(context: Context) : super(context) {
    inflateView()
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    inflateView()
  }

  constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
    context,
    attrs,
    defStyle
  ) {
    inflateView()
  }


  private fun inflateView() {
    val view = LayoutInflater.from(context).inflate(R.layout.native_admob_full_view, this, true)
    view.findViewById<MediaView>(R.id.adMedia).setImageScaleType(ImageView.ScaleType.CENTER_CROP)

    val adView = view.findViewById<UnifiedNativeAdView>(R.id.ad_view)
    adView.headlineView = view.findViewById<TextView>(R.id.tvHeadline)
    adView.bodyView = view.findViewById<TextView>(R.id.tvBody)
    adView.callToActionView = view.findViewById<Button>(R.id.btnAction)
    adView.iconView = view.findViewById<ImageView>(R.id.imvIcon)
    adView.priceView = view.findViewById<Button>(R.id.tvPrice)
    adView.storeView = view.findViewById<Button>(R.id.tvStore)
    adView.advertiserView = view.findViewById<Button>(R.id.tvAdvertiser)
    adView.mediaView = view.findViewById(R.id.adMedia)
  }

  fun setNativeAd(nativeAd: UnifiedNativeAd?) {
    val adView = findViewById<UnifiedNativeAdView>(R.id.ad_view)
    (adView.headlineView as TextView).text = nativeAd?.headline
    (adView.bodyView as TextView).text = nativeAd?.body
    (adView.callToActionView as Button).text = nativeAd?.callToAction
    (adView.iconView as ImageView).setImageDrawable(nativeAd?.icon?.drawable)
    (adView.priceView as TextView).text = nativeAd?.price
    (adView.storeView as TextView).text = nativeAd?.store
    (adView.advertiserView as TextView).text = nativeAd?.advertiser
    nativeAd?.mediaContent?.let {
      adView.mediaView?.setMediaContent(it)
    }
    adView.setNativeAd(nativeAd)
  }
}