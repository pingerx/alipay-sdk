# 支付宝Android版支付SDK包装
[ ![Download](https://api.bintray.com/packages/fungo/maven/alipay-sdk/images/download.svg) ](https://bintray.com/fungo/maven/alipay-sdk/_latestVersion)

### 说明
由于支付宝官方提供的最新支付SDK是AAR格式的，在Library中使用需要各个module都配置aar路径，非常麻烦。
为了避免这种到处配置的问题，这里把AAR文件添加到`jcenter`仓库，生成依赖的方式，可以直接引用。

### 添加依赖
在build.gradle文件中添加依赖

    dependencies {
        implementation 'com.pingerx:alipay-sdk:1.0.0'
    }

### 使用流程
* [具体流程请看官方文档](https://docs.open.alipay.com/204/105296/)
* 导入sdk资源，只需要添加依赖即可。

#### 支付接口调用
* 需要在新线程中调用支付接口。
* PayTask对象主要为商户提供订单支付、查询功能，及获取当前开发包版本号。获取PayTask支付对象调用支付。

        // 必须异步调用支付
        Thread(Runnable {
            val result = PayTask(this).payV2("商品信息", true)
            // ui线程处理结果
            // ...
        }).start()

* payV2方法参数
    * orderInfo：app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。
        * 示例：app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D
    * isShowPayLoading：用户在商户app内部点击付款，是否需要一个loading做为在钱包唤起之前的过渡，这个值设置为true，将会在调用pay接口的时候直接唤起一个loading，直到唤起H5支付页面或者唤起外部的钱包付款页面loading才消失。（建议将该值设置为true，优化点击付款到支付唤起支付页面的过渡过程。）

#### 支付结果获取和处理
* 同步返回：商户应用客户端通过当前调用支付的Activity的Handler对象，通过它的回调函数获取支付结果。
* 异步通知：商户需要提供一个http协议的接口，包含在请求支付的入参中，其key对应notify_url。支付宝服务器在支付完成后，会以POST方式调用notify_url传输数据。


#### 获取当前开发包版本号
* 调用PayTask对象的getVersion()方法查询。

       val payTask = PayTask(activity)
       val version = payTask.getVersion()


### 支付宝官方资源
* 本仓库使用的sdk版本：`alipaySdk-15.5.9-20181123210601.aar`
* [官方支付sdk下载](https://docs.open.alipay.com/54/104509)
