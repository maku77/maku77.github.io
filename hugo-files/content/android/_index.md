---
title: "Android"
url: "/android/"

categoryName: "まくまく Android ノート"
categoryUrl: "/android/"
categoryIcon: _index.svg
---

Android アプリや Android デバイスの開発に役立つかもしれないノートです。

Tools
----
* [protection レベルが dangerous なパーミッションの一覧を表示する (`pm list permissions`)](/p/8x8qmy3/)
* [パーミッショングループの一覧を表示する (`pm list permission-groups`)](/p/viry8fo/)
* [ダウンロード可能な Android SDK コンポーネントの一覧を取得する (`android list sdk`)](/p/8k5m4k2/)
* [ADB から Backup Manager を走らせる (`bmgr backup`, `bmgr restore`)](/p/x9xhyhz/)
* [Activity の情報を表示する (`dumpsys activity`)](/p/mbnw7gq/)
* [メモリの使用状況を確認する (`dumpsys meminfo`)](/p/t6wfxev/)
* [APK のパッケージ名から APK ファイルのパスを調べる (`pm path`, `pm list packages`)](/p/ghtgxgw/)
* [APK ファイルに署名する (`keytool`, `jarsigner`)](/p/3yk3j2i/)
* [APK ファイルの署名を確認する (`jarsigner`, `openssl`)](/p/2x9it3c/)
* [Dex 形式の Shared library (JAR) を作成する (`dx`)](/p/huevdub/)

### パフォーマンス
* [Android アプリのパフォーマンス改善のためのチェックリスト](/p/sfqyajt/)
* [dumpsys gfxinfo でジャンクフレームの発生率を調べる（60FPSの確認）](/p/26hr2bk/)
* [Choreographer で FPS を計測する（Fps クラスの実装）](/p/imx8hr2/)
* [Android アプリのパフォーマンス改善に使用できるツール](/p/39cfimo/)
* [ADB で描画パフォーマンス計測のデバッグ機能を有効にする](/p/bn8q8o6/)
* [Perfetto でシステム全体のパフォーマンスを計測する](/p/ehu5eox/)
* [Traceview でプロファイル情報を表示する](/p/2wgxfwd/)
* [旧）Systrace をコマンド化して簡単に実行できるようにする (systrace.cmd)](/p/ycwfweu/)
* [旧）Systrace の画面が真っ白になるときの対応方法](/p/u3n5m3j/)

### エミュレーター
* [Android エミュレーター内のサーバーに外部からアクセスする (`adb forward`)](/p/q6r98p8/)
* [Android エミュレーターをコマンドラインから操作する (`emulator`, `adb emu`)](/p/zgyhygw/)

### 外部ツールとの連携
* [Golang で Android 上で動く CLI コマンドを作成する (`go build`)](/p/ftducta/)
* [Ruby で adb コマンドの出力結果を処理する](tools/handle-adb-with-ruby.html)

### 解析／デバッグ／ADB コマンド
* [APK のパッケージ依存関係やクラス依存関係を調べる](analyze/apk-dependencies.html)
* [APK ファイルを逆コンパイルする (apktool)](decompile-apk.html)
* [APK の情報を表示する (aapt)](aapt.html)
* [ADB で Activity や Service を起動するインテントを投げる (am start/start-service/broadcast)](adb/adb-am-start.html)
* [ADB で指定したアプリを強制終了する (am force-stop)](adb/adb-force-stop-app.html)
* [ADB で特定のブロードキャストインテントをレシーブするアプリを列挙する (dumpsys activity broadcasts)](adb/enum-receivers.html)
* [ADB でディレクトリ内のファイルをすべて取得する (adb pull)](adb/adb-pull-dir.html)
* [ADB でスリープ状態に入る／スリープから抜ける](adb/go-to-sleep.html)
* [ADB で WakeLock を強制的に外す](adb/unlock-wakelock.html)
* [ADB からキー入力やテキスト入力を行う (`input text`, `input keyevent`)](/p/gorux24/)
* [ADB で Android 端末のバージョンや API レベルを調べる (getprop)](/p/bg2g4bu/)
* [ADB で Android 端末にインストールされているパッケージの一覧を取得する (pm list packages)](/p/uh84kfj/)
* [TCP/IP で adb 接続する](adb/connect-adb-with-tcpip.html)
* [Android Studio で静的解析プラグインを使用する](androidstudio-static-analysis.html)
* [ANR の原因を突き止める](analyze-anr.html)
* [ADB 経由でスクリーンキャプチャを取得する](adb/screencapture.html)
* [StrictMode を有効にして望ましくない実装を検出する](analyze/strict-mode.html)
* [StrictMode の違反メッセージを Toast で表示する (StrictModeToaster)](analyze/strict-mode-toaster.html)
* 旧）各種デバイスの ADB 接続
  * [Nexus7 (2013) に adb 接続する](adb/connect-adb-to-nexus.html)
  * [Xperia Tablet Z に adb 接続する](adb/connect-adb-to-xperia-tablet-z.html)
  * [Sony Tablet S に adb 接続する](adb/connect-adb-to-sony-tablet-s.html)
  * [SHARP IS03 に adb 接続する](adb/connect-adb-to-sharp-is03.html)
  * [Eclipse から Android SDK を使えるようにする (ADK)](install-adk.html)

### Logcat
* [Logcat のログ出力をフィルタする](filter-logcat.html)


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
* [Android アプリが使用している依存ライブラリをツリー構造で表示する（dependencies/androidDependencies タスク）](gradle/dependency-tree.html)
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
* [getevent/sendevent で入力デバイスへの入力情報を取得する／入力を行う](/p/gufweuc/)
* [libhardware.so が hardware サポートライブラリをロードする仕組み](vendor/libhardware.html)

Android Framework/SDK
----
* [Jetpack Compose の宣言型 (declarative) の UI 定義とは？](fw/compose.html)
* [Activity の起動モードと起動フラグ（タスクとアフィニティ）](fw/task-and-affinity.html)
* [SpeechRecognizer で音声入力を実現する](fw/speech-recognizer.html)
* [リソース ID を示す変数やパラメータにアノテーションを付ける](fw/res-annotation.html)

### 設定 (SharedPreferences)
* [SharedPreferences でアプリの設定値を保存する](fw/shared-preference.html)
* [Preference フレームワークを使って設定画面を簡単に作成する](fw/preference-fw.html)

Java レイヤ
----
* [特殊ディレクトリの情報を取得する](special-dir-info.html)
* [AsyncTask による非同期処理と UI 更新処理](async-task.html)

### I/O（入出力）
* [キーイベント (KeyEvent) を見やすく出力する](io/print-keyevents.html)
* [タッチイベントをハンドルする](io/handle-touch-events.html)
* [Emulator での開発時にキーボードによるキー入力が長押しかどうかを判別する](io/keyboard-long-press.html)

### UI（描画）
* [常に画面の最前面に表示されたままになる View を作る (TYPE_APPLICATION_OVERLAY)](ui/always-top.html)
* [Kotlin で OpenGL ES を使って 3D 表示するアプリを作る](ui/opengl.html)
* [RecyclerView の基本](ui/recycler-view.html)
* [任意の View をフォーカスする](ui/focus-view.html)
* [ソフトウェアキーボードを常に表示する](ui/show-keyboard-all-the-time.html)
* [カスタムビューを作成する](ui/create-custom-view.html)
* [カスタムビューの子ビューがフォーカスを得たことを検出する](ui/custom-view-focus.html)
* [カスタムビューでソフトウェア D-Pad を表示する](ui/software-dpad.html)
* [ValueAnimator でアニメーションに使用する値を計算する](ui/value-animator.html)
* [ScrollView を一番下までスクロールさせる](ui/scroll-to-bottom.html)
* [Canvas に複数行のテキストを描画する](ui/draw-multiline-text-on-canvas.html)
* [Canvas への描画時にアンチエイリアスを有効にする](ui/antialias-on-canvas.html)

### 数値／文字列
* [数値をある範囲内［min, max］に丸める (MathUtils.clamp)](numstr/clamp.html)

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

