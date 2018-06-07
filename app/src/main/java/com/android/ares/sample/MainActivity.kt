package com.android.ares.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AresLayout.OnRetryClickedListener {

    override fun onRetryClick() {
        Toast.makeText(this, "重新加载", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyBtn.setOnClickListener { AresLayout.showEmptyLayout(content, windowManager) }
        loadingBtn.setOnClickListener { AresLayout.showLoadingLayout(content, windowManager) }
        errorBtn.setOnClickListener { AresLayout.showNetworkErrorLayout(content, windowManager) }

        AresLayout.setOnRetryClickedListener(this)

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && AresLayout.isAresShowing()) {//禁用返回键
            AresLayout.onDestroy(windowManager)
            return false
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }

}
