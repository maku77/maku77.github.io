---
title: "Android"
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
* [APK のパッケージ名から APK ファイルのパスを調べる](tools/pm-path.html)
* [APK ファイルに署名する](tools/sign-jar.html)
* [APK ファイルの署名を確認する](tools/verify-certs.html)

### パフォーマンス
* [Android アプリのパフォーマンス改善に使用できるツール](tools/performance-tools.html)
* [Traceview でプロファイル情報を表示する](tools/traceview.html)
* [Systrace をコマンド化して簡単に実行できるようにする (systrace.cmd)](tools/systrace-cmd.html)
* [トラブル: Systrace の画面が真っ白になる](tools/white-screen-on-systrace.html)

### 外部ツールとの連携
* [Ruby で adb コマンドの出力結果を処理する](tools/handle-adb-with-ruby.html)

### 解析／デバッグ／ADB コマンド
* [APK のパッケージ依存関係やクラス依存関係を調べる](analyze/apk-dependencies.html)
* [APK ファイルを逆コンパイルする (apktool)](decompile-apk.html)
* [APK の情報を表示する (aapt)](aapt.html)
* [ADB でアプリを起動する intent を投げる (am start/am broadcast)](adb/adb-am-start.html)
* [ADB でディレクトリ内のファイルをすべて取得する](adb/adb-pull-dir.html)
* [ADB で指定したアプリを強制終了する](adb/adb-force-stop-app.html)
* [ADB でスリープ状態に入る／スリープから抜ける](adb/go-to-sleep.html)
* [ADB で WakeLock を強制的に外す](adb/unlock-wakelock.html)
* [ADB からキー入力やテキスト入力を行う (input text, input keyevent)](adb/input-keyevent.html)
* [ADB で特定のブロードキャストインテントをレシーブするアプリを列挙する (dumpsys activity broadcasts)](adb/enum-receivers.html)
* [Android Studio で静的解析プラグインを使用する](androidstudio-static-analysis.html)
* [ANR の原因を突き止める](analyze-anr.html)
* [ADB 経由でスクリーンキャプチャを取得する](adb/screencapture.html)
* [StrictMode を有効にして望ましくない実装を検出する](analyze/strict-mode.html)

### Logcat
* [Logcat のログ出力をフィルタする](filter-logcat.html)

### ADB 接続
* [TCP/IP で adb 接続する](adb/connect-adb-with-tcpip.html)
* [Nexus7 (2013) に adb 接続する](adb/connect-adb-to-nexus.html)
* [Xperia Tablet Z に adb 接続する](adb/connect-adb-to-xperia-tablet-z.html)
* [Sony Tablet S に adb 接続する](adb/connect-adb-to-sony-tablet-s.html)
* [SHARP IS03 に adb 接続する](adb/connect-adb-to-sharp-is03.html)
* [Eclipse から Android SDK を使えるようにする (ADK)](install-adk.html)

ビルド関連
----

### Gradle
* [Android アプリ用の Gradle スクリプトの基本](gradle/gradle-for-android.html)
* [リソース名に正しくプレフィックスが付いているか確認する](resource-prefix.html)
* [Android Studio を使っているときの Gradle 関連ファイル](gradle-related-files.html)
* [BuildConfig クラスでアプリの動作を切り替える](gradle/build-config.html)
* [サブモジュールで使用する SDK バージョンを統一する](gradle/align-sdk-versions.html)
* [外部から提供された AAR ファイルを利用する](gradle/external-aar.html)
* [APK ファイル名のサフィックスにバージョンを追加する](gradle/add-version-name-to-apk.html)
* [Lint エラーが発生した場合もビルドを継続する](gradle/ignore-lint-errors.html)
* [すべてのサブモジュール（ライブラリ）の依存関係をツリー構造で表示する](gradle/dependency-tree.html)
* Gradle 全般の説明は [こちらを参照](../gradle/)

### Android Studio
* [AAR 形式のファイルを作成する/使用する](create-and-import-aar.html)
* [Android Studio に割り当てるメモリを増やす](studio/increase-memory.html)
* [Android Studio で新規ファイル作成時に Copyright を自動挿入する](studio/auto-copyright.html)
* [Android Studio の便利なショートカット (1) コードの調査／メソッド間のジャンプ](studio/shortcut-method-jump.html)
* [Android Studio の便利なショートカット (2) ブックマーク](studio/shortcut-bookmark.html)

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
* [libhardware.so が hardware サポートライブラリをロードする仕組み](vendor/libhardware.html)

Android Framework/SDK
----
* [Activity の起動モードと起動フラグ（タスクとアフィニティ）](fw/task-and-affinity.html)
* [SharedPreference でアプリの設定値を保存する](fw/shared-preference.html)
* [SpeechRecognizer で音声入力を実現する](fw/speech-recognizer.html)
* [リソース ID を示す変数やパラメータ用のアノテーションを使用する](fw/res-annotation.html)

Java レイヤ
----
* [特殊ディレクトリの情報を取得する](special-dir-info.html)
* [AsyncTask による非同期処理と UI 更新処理](async-task.html)

### I/O（入出力）
* [キーイベント (KeyEvent) を見やすく出力する](io/print-keyevents.html)
* [タッチイベントをハンドルする](io/handle-touch-events.html)

### UI（描画）
* [任意の View をフォーカスする](ui/focus-view.html)
* [ソフトウェアキーボードを常に表示する](ui/show-keyboard-all-the-time.html)
* [カスタムビューを作成する](ui/create-custom-view.html)
* [カスタムビューでソフトウェア D-Pad を表示する](ui/software-dpad.html)
* [ValueAnimator でアニメーションに使用する値を計算する](ui/value-animator.html)
* [ScrollView を一番下までスクロールさせる](ui/scroll-to-bottom.html)
* [Canvas に複数行のテキストを描画する](ui/draw-multiline-text-on-canvas.html)
* [Canvas への描画時にアンチエイリアスを有効にする](ui/antialias-on-canvas.html)

### Service（サービス）
* [同じプロセス内のサービスへバインドする (Local Bind)](service/local-bind.html)
* [別プロセスのサービスへバインドする (Remote Bind)](service/remote-bind.html)
* [サービスからコールバックできるようにする](service/callback.html)

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

トラブルシューティング
----
* [repo init でエラーが出る場合](trouble/repo-init-error.html)

その他
----
* [ネイティブライブラリの展開先](install-path-of-native-libs.html)
* [現在のスクリーンレイアウトサイズを取得する](screen-layout-size.html)
* [ネットワーク関連の情報を取得する](network-info.html)

