package com.fungo.demo

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alipay.sdk.app.PayTask
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler()
    private val params =
        "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onAliPay(view: View) {
        println(view)

        val payTask = PayTask(this)
        // 子线程发起
        executor.execute {
            val payResult = payTask.payV2(params, true)

            // UI线程处理结果
            handler.post {
                if (payResult != null) {
                    payResult.keys.forEach {
                        println("pay result key：$it | value：${payResult[it]} ")
                    }
                    val resultStatus = payResult["resultStatus"]
                    val memo = payResult["memo"]
                    val result = payResult["result"]
                    when {
                        TextUtils.equals(resultStatus, "9000") -> // 支付成功
                            setResult("pay success：$result")
                        TextUtils.equals(
                            resultStatus,
                            "8000"
                        ) -> // 支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            setResult("pay dealing")
                        TextUtils.equals(resultStatus, "6001") -> // 支付取消
                            setResult("pay cancel：$result")
                        TextUtils.equals(resultStatus, "6002") -> // 网络连接出错
                            setResult("pay network connection failure")
                        TextUtils.equals(resultStatus, "4000") -> // 支付错误
                            setResult("pay error：$memo")
                    }
                } else {
                    setResult("------------> pay result is null.")
                }
            }
        }
    }

    private fun setResult(result: String) {
        tvResult.text = result
    }
}
