---
title: BuildConfig クラスでアプリの動作を切り替える
date: "2016-08-01"
---

Android SDK のバージョン 17 以降では、ビルド時に自動的に `BuildConfig` というクラスが生成されるようになっています。
このクラスはビルド設定に応じた定数フィールドを提供しており、これを利用すると、ビルドタイプが `debug` のときのみデバッグログを出力するといったことが行えます 。

```java
if (BuildConfig.DEBUG) {
    Log.d(TAG, "Important data is missing!");
}
```

Gradle のビルドスクリプトで、この `BuildConfig` クラスに独自の定数フィールドを追加することも可能です。
下記の例では、ビルドタイプごとに `BuildConfig.ACCESS_LOG` 定数と、`BuildConfig.WEBAPI_URL` 定数を定義しています（ここではビルドタイプごとに設定していますが、Product Flavor を含むすべての Build Variants で自由に設定することができます）。

#### build.gradle

```groovy
android {
    ...
    buildTypes {
        debug {
            buildConfigField 'boolean', 'ACCESS_LOG', 'false'
            buildConfigField 'String', 'WEBAPI_URL', '"http://dev.example.com/api/"'
        }
        release {
            buildConfigField 'boolean', 'ACCESS_LOG', 'true'
            buildConfigField 'String', 'WEBAPI_URL', '"http://example.com/api/"'
        }
    }
}
```

