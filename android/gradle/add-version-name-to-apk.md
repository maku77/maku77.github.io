---
title: APK ファイル名のサフィックスにバージョンを追加する
date: "2016-08-15"
---

Android の Application プロジェクトの `build.gradle` で、下記のように設定しておくと、`build/outputs/apk` 以下に生成される APK ファイルにバージョン番号を付けてくれるようになります（例: `app-debug-1.0.0.apk`）。

#### build.gradle

```groovy
apply plugin: 'com.android.application'

android {
    ...
    applicationVariants.all { variant ->
        variant.outputs.each { output ->
            def file = output.outputFile
            output.outputFile = new File(file.parent,
                file.name.replace('.apk', "-${variant.versionName}.apk"))
        }
    }
}
```

