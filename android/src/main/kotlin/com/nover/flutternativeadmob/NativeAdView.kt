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

class NativeAdView @JvmOverloads constructor(
    context: Context,
    type: NativeAdmobType,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  var options = NativeAdmobOptions()
    set(value) {
      field = value
      updateOptions()
    }

  private val adView: UnifiedNativeAdView

  private val ratingBar: RatingBar

  private val adMedia: MediaView?

  private val adHeadline: TextView
  private val adBody: TextView?
  private val callToAction: Button

  init {
    val inflater = LayoutInflater.from(context)
    val layout = when (type) {
      NativeAdmobType.full -> R.layout.native_admob_full_view
      NativeAdmobType.banner -> R.layout.native_admob_banner_view
    }
    inflater.inflate(layout, this, true)

    setBackgroundColor(Color.TRANSPARENT)

    adView = findViewById(R.id.ad_view)

    adMedia = adView.findViewById(R.id.ad_media)

    adHeadline = adView.findViewById(R.id.ad_headline)
    adBody = adView.findViewById(R.id.ad_body)
    callToAction = adView.findViewById(R.id.ad_call_to_action)

    initialize()
  }

  private fun initialize() {
    // The MediaView will display a video asset if one is present in the ad, and the
    // first image asset otherwise.
    adView.mediaView = adMedia

    // Register the view used for each individual asset.
    adView.headlineView = adHeadline
    adView.bodyView = adBody
    adView.callToActionView = callToAction
    adView.iconView = adView.findViewById(R.id.ad_icon)
    adView.storeView = adStore
  }

  fun setNativeAd(nativeAd: UnifiedNativeAd?) {
    if (nativeAd == null) return

    // Some assets are guaranteed to be in every UnifiedNativeAd.
    adMedia?.setMediaContent(nativeAd.mediaContent)
    adMedia?.setImageScaleType(ImageView.ScaleType.FIT_CENTER)

    adHeadline.text = nativeAd.headline
    adBody?.text = nativeAd.body
    (adView.callToActionView as Button).text = nativeAd.callToAction

    // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
    // check before trying to display them.
    val icon = nativeAd.icon

    if (icon == null) {
      adView.iconView.visibility = View.GONE
    } else {
      (adView.iconView as ImageView).setImageDrawable(icon.drawable)
      adView.iconView.visibility = View.VISIBLE
    }
    // Assign native ad object to the native view.
    adView.setNativeAd(nativeAd)
  }

  private fun updateOptions() {
    adMedia?.visibility = if (options.showMediaContent) View.VISIBLE else View.GONE

    ratingBar.progressDrawable
        .setColorFilter(options.ratingColor, PorterDuff.Mode.SRC_ATOP)



    adHeadline.setTextColor(options.headlineTextStyle.color)
    adHeadline.textSize = options.headlineTextStyle.fontSize
    adHeadline.visibility = options.headlineTextStyle.visibility

    adBody?.setTextColor(options.bodyTextStyle.color)
    adBody?.textSize = options.bodyTextStyle.fontSize
    adBody?.visibility = options.bodyTextStyle.visibility

    callToAction.textSize = options.callToActionStyle.fontSize
  }
}