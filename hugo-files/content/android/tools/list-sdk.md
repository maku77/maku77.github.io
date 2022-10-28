---
title: "ダウンロード可能な Android SDK コンポーネントの一覧を取得する (android list sdk)"
url: "p/8k5m4k2/"
date: "2015-10-09"
tags: ["Android"]
aliases: /android/list-sdk.html
---

Android SDK に付属している __`android`__ コマンドの __`list sdk`__ サブコマンドを使用すると、追加でインストール可能な SDK コンポーネントの一覧を取得することができます。

{{< code lang="console" title="インストール可能な SDK コンポーネント" >}}
$ android list sdk --no-ui --all --extended
...
----------
id: 157 or "extra-google-usb_driver"
     Type: Extra
     Desc: Google USB Driver, revision 11
           By Google Inc.
           USB Driver for Windows, revision 11
           Install path: extras\google\usb_driver
----------
id: 158 or "extra-google-webdriver"
     Type: Extra
     Desc: Google Web Driver, revision 2
           By Google Inc.
           WebDriver
           Install path: extras\google\webdriver
----------
id: 159 or "extra-intel-Hardware_Accelerated_Execution_Manager"
     Type: Extra
     Desc: Intel x86 Emulator Accelerator (HAXM installer), revision 5.5
           By Intel Corporation
           Emulation speedup using Intel? VT processor technology. Read more
           at www.intel.com/software/android
           Install path: extras\intel\Hardware_Accelerated_Execution_Manager
{{< /code >}}

例えば、以下のようなコンポーネントがリストアップされます。

{{< code title="実行結果の抜粋" >}}
id: 4 or "build-tools-23.0.1"
id: 25 or "android-23"
id: 64 or "sys-img-armeabi-v7a-android-tv-23"
id: 100 or "addon-google_apis-google-23"
id: 133 or "source-23"
id: 143 or "extra-Sony-DeviceProfiles"
id: 145 or "extra-android-support"
id: 157 or "extra-google-usb_driver"
{{< /code >}}

