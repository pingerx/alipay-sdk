package com.fungo.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alipay.sdk.app.PayTask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            PayTask(this)
            tvStatus.text = "alipay-sdk调用成功"
        } catch (e: Exception) {
            tvStatus.text = "alipay-sdk调用失败"
            e.printStackTrace()
        }
    }
}
