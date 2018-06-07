package com.android.ares.sample

import android.content.Context
import android.graphics.PixelFormat
import android.view.*
import android.widget.Button


object AresLayout {

    private lateinit var emptyLayout: ViewGroup
    private lateinit var loadingLayout: ViewGroup
    private lateinit var networkErrorLayout: ViewGroup
    private var currentLayout: ViewGroup? = null
    private lateinit var mContext: Context
    private var isAresShowing = false
    private var onRetryClickedListener: OnRetryClickedListener? = null

    /**
     * 初始化
     */
    public fun init(context: Context) {
        mContext = context
    }

    /**
     * 设置空数据界面的布局
     */
    public fun setEmptyLayout(resId: Int) {
        emptyLayout = getLayout(resId)
        emptyLayout.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    /**
     * 设置加载中界面的布局
     */
    public fun setLoadingLayout(resId: Int) {
        loadingLayout = getLayout(resId)
        loadingLayout.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    /**
     * 设置网络错误界面的布局
     */
    public fun setNetworkErrorLayout(resId: Int) {
        networkErrorLayout = getLayout(resId)
        networkErrorLayout.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        networkErrorLayout.findViewById<Button>(R.id.retry).setOnClickListener {
            onRetryClickedListener?.let { it.onRetryClick() }
        }
    }

    /**
     * 展示空数据界面
     * target的大小及位置决定了window界面在实际屏幕中的展示大小及位置
     */
    fun showEmptyLayout(target: View, wm: WindowManager) {
        if (currentLayout != null) {
            wm.removeView(currentLayout)
        }
        isAresShowing = true
        currentLayout = emptyLayout
        wm.addView(currentLayout, setLayoutParams(target))
    }

    /**
     * 展示加载中界面
     * target的大小及位置决定了window界面在实际屏幕中的展示大小及位置
     */
    fun showLoadingLayout(target: View, wm: WindowManager) {
        if (currentLayout != null) {
            wm.removeView(currentLayout)
        }
        isAresShowing = true
        currentLayout = loadingLayout
        wm.addView(currentLayout, setLayoutParams(target))
    }

    /**
     * 展示网络错误界面
     * target的大小及位置决定了window界面在实际屏幕中的展示大小及位置
     */
    fun showNetworkErrorLayout(target: View, wm: WindowManager) {
        if (currentLayout != null) {
            wm.removeView(currentLayout)
        }
        isAresShowing = true
        currentLayout = networkErrorLayout
        wm.addView(currentLayout, setLayoutParams(target))
    }

    fun getEmptyLayout(): ViewGroup = emptyLayout

    /**
     * 是否有Ares界面正在展示
     */
    fun isAresShowing(): Boolean = isAresShowing


    private fun setLayoutParams(target: View): WindowManager.LayoutParams {
        val wlp = WindowManager.LayoutParams()
        wlp.format = PixelFormat.TRANSPARENT
        wlp.flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        var location = IntArray(2)
        target.getLocationOnScreen(location)
        wlp.x = location[0]
        wlp.y = location[1]
        wlp.height = target.height
        wlp.width = target.width
        wlp.type = WindowManager.LayoutParams.FIRST_SUB_WINDOW
        wlp.gravity = Gravity.LEFT or Gravity.TOP
        return wlp
    }

    private fun getLayout(resId: Int): ViewGroup {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(resId, null) as ViewGroup
    }

    interface OnRetryClickedListener {
        fun onRetryClick()
    }

    fun setOnRetryClickedListener(listener: OnRetryClickedListener) {
        onRetryClickedListener = listener
    }

    fun onDestroy(wm: WindowManager) {
        isAresShowing = false
        currentLayout?.let {
            wm.removeView(currentLayout)
            currentLayout = null
        }
    }
}