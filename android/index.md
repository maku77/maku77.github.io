---
title: Android
layout: category-index
---

Tools
----
* [protection レベルが dangerous なパーミッションの一覧を表示する](dangerous-permissions.html)
* [パーミッショングループの一覧を表示する](permission-groups.html)
* [ダウンロード可能な Android SDK コンポーネントの一覧を取得する](list-sdk.html)
* [Dex 形式の Shared library (JAR) を作成する (dx)](create-dex-jar.html)
* [adb から Backup Manager を走らせる (bmgr)](backup-manager.html)
* [Activity の情報を表示する (dumpsys)](dumpsys-activity.html)
* [メモリの使用状況を確認する (dumpsys)](dumpsys-meminfo.html)
* [Android アプリのパフォーマンス改善に使用できるツール](tools/performance-tools.html)
* [Traceview でプロファイル情報を表示する](tools/traceview.html)
* [トラブル: Systrace の画面が真っ白になる](tools/white-screen-on-systrace.html)
* [APK のパッケージ名から APK ファイルのパスを調べる](tools/pm-path.html)

### 外部ツールとの連携
* [Ruby で adb コマンドの出力結果を処理する](tools/handle-adb-with-ruby.html)


開発環境／ADB 接続
----
* [APK ファイルを逆コンパイルする (apktool)](decompile-apk.html)
* [APK の情報を表示する (aapt)](aapt.html)
* [Android Studio で静的解析プラグインを使用する](androidstudio-static-analysis.html)
* [TCP/IP で adb 接続する](connect-adb-with-tcpip.html)
* [ANR の原因を突き止める](analyze-anr.html)
* [ADB でディレクトリ内のファイルをすべて取得する](adb/adb-pull-dir.html)
* [ADB で指定したアプリを強制終了する](adb/adb-force-stop-app.html)
* [ADB でスリープ状態に入る／スリープから抜ける](adb/go-to-sleep.html)
* [ADB で WakeLock を強制的に外す](adb/unlock-wakelock.html)
* [Nexus7 (2013) に adb 接続する](connect-adb-to-nexus.html)
* [Xperia Tablet Z に adb 接続する](connect-adb-to-xperia-tablet-z.html)
* [Eclipse から Android SDK を使えるようにする (ADK)](install-adk.html)

Logcat
----
* [Logcat のログ出力をフィルタする](filter-logcat.html)

Android デバイス開発者向け（ベンダ向け情報）
----
* [root ユーザで adb 接続する](adb-with-root.html)
* [Sleep モードに遷移する](go-to-sleep.html)
* [現在の Configuration を変更する](change-configuration.html)
* [LowMemoryKiller によるプロセス kill の優先順位の仕組み](low-memory-killer1.html)
* [LowMemoryKiller の too many background によるプロセス kill の仕組み](low-memory-killer2.html)
* [Android デバイスのパーティション構成概要](partitions.html)
* [make 後の生成イメージが格納されるディレクトリを調べる](product-out-dir.html)
* [インストールされた各 APK パッケージに割り当てられたユーザー ID を調べる](check-user-id.html)
* [android/build/core 以下の *.mk ファイルで定義されている関数 (define) のリスト](defines-in-makefile.html)
* [ART/Dalvik VM のバージョンを調べる](dalvik-version.html)
* [Locale を切り替える](change-locale.html)
* [サポートされている Locale の一覧 (Android 4.0)](supported-locales.html)
* [製品にパッケージングする APK を指定する](vendor/package-apks.html)
* [Make 変数でビルド範囲を制御する](vendor/control-build-target.html)
* [システムプロパティのあれこれ](vendor/system-property.html)
* [findmakefile で Android.mkのあるディレクトリを調べる](vendor/findmakefile.html)
* [getevent/sendevent で入力デバイスへの入力情報を取得する／入力を行う](vendor/getevent-and-sendevent.html)


Java レイヤ
----
* [特殊ディレクトリの情報を取得する](special-dir-info.html)
* [AsyncTask による非同期処理と UI 更新処理](async-task.html)

### 設定 (Settings)
* [画面消灯 (SCREEN OFF) までの時間を取得・設定する](settings/screen-off-timeout.html)

Native レイヤ
----
* [RefBase によるスマートポインタ](refbase-smart-pointer.html)
* [ネイティブサービスの実装 (1) Binder 関連のクラス](native-service1.html)
* [ネイティブサービスの実装 (2) サービスの実装から利用まで](native-service2.html)
* [ネイティブサービスの実装 (3) サービスのインタフェースを定義する](native-service3.html)

### ServiceManager 関連
* [ServiceManager に登録されたサービスを列挙する](list-services.html)


ビルド関連 / Gradle / Android Studio
----
* [AAR 形式のファイルを作成する/使用する](create-and-import-aar.html)
* [Android Studio を使っているときの Gradle 関連ファイル](gradle-related-files.html)
* [リソース名に正しくプレフィックスが付いているか確認する](resource-prefix.html)
* [Android Studio に割り当てるメモリを増やす](studio/increase-memory.html)
* [Android Studio で新規ファイル作成時に Copyright を自動挿入する](studio/auto-copyright.html)
* [Android Studio の便利なショートカット (1) メソッド間のジャンプ](studio/shortcut-method-jump.html)
* [Android Studio の便利なショートカット (2) ブックマーク](studio/shortcut-bookmark.html)
* Gradle 全般の説明は [こちらを参照](../gradle/)

その他
----
* [ネイティブライブラリの展開先](install-path-of-native-libs.html)
* [現在のスクリーンレイアウトサイズを取得する](screen-layout-size.html)
* [ネットワーク関連の情報を取得する](network-info.html)

