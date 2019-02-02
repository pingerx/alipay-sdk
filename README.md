# 支付宝Android版支付SDK包装
[ ![Download](https://api.bintray.com/packages/fungo/maven/alipay-sdk/images/download.svg) ](https://bintray.com/fungo/maven/alipay-sdk/_latestVersion)

由于支付宝官方提供的最新支付SDK是AAR格式的，在Library中使用需要各个module都配置aar路径，非常麻烦。
为了避免这种到处配置的问题，这里把AAR文件添加到`jcenter`仓库，生成依赖的方式，可以直接引用。

### 使用
在build.gradle文件中添加依赖

    dependencies {
        implementation 'com.pingerx:alipay-sdk:1.0.0'
    }

### 依赖的支付宝SDK
* alipaySdk-15.5.9-20181123210601.aar

