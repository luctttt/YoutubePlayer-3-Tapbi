package vn.tapbi.youtubeplayer3.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import com.tuanhav95.drag.DragView
import com.tuanhav95.drag.utils.inflate
import com.tuanhav95.drag.utils.reWidth
import kotlinx.android.synthetic.main.layout_top.view.*
import timber.log.Timber
import vn.tapbi.youtubeplayer3.R
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity

class DragSource @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DragView(context, attrs, defStyleAttr) {
    var mWidthWhenMax = 0
    var mWidthWhenMiddle = 0
    var mWidthWhenMin = 0

    init {
        getFrameFirst().addView(inflate(R.layout.layout_top))
        getFrameSecond().addView(inflate(R.layout.layout_bottom))
    }

    override fun initFrame() {
        mWidthWhenMax = width
        mWidthWhenMiddle = (width - mPercentWhenMiddle * mMarginEdgeWhenMin).toInt()
        mWidthWhenMin = mHeightWhenMin * 22 / 9

        super.initFrame()
    }

    override fun refreshFrameFirst() {
        super.refreshFrameFirst()

        val width = if (mCurrentPercent < mPercentWhenMiddle) {
            (mWidthWhenMax - (mWidthWhenMax - mWidthWhenMiddle) * mCurrentPercent)
        } else {
            (mWidthWhenMiddle - (mWidthWhenMiddle - mWidthWhenMin) * (mCurrentPercent - mPercentWhenMiddle) / (1 - mPercentWhenMiddle))
        }

        frameTop.reWidth(width.toInt())

        // add event button
        ivExit.setOnClickListener(OnClickListener{
            (context as HomeActivity).stopVideo()
            (context as HomeActivity).hideProgressBar()
        })

        ivPause.setOnClickListener(OnClickListener {
            (context as HomeActivity).pauseVideo()
            isPause()
        })
        ivPlay.setOnClickListener(OnClickListener {
            (context as HomeActivity).playVideo()
            isPlay()
        })

        tvNameVideo.text =(context as HomeActivity).titleVideo
        tvDescription.text =(context as HomeActivity).channelVideo

    }

    fun isPlay(){
        ivPause.visibility = View.VISIBLE
        ivPlay.visibility = View.GONE
    }

    fun isPause(){
        ivPause.visibility = View.GONE
        ivPlay.visibility = View.VISIBLE
    }
}