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
* [Ruby で adb コマンドの出力結果を処理する](/p/w6vao2e/)

### 解析／デバッグ／ADB コマンド

* [APK のパッケージ依存関係やクラス依存関係を調べる (`jdeps`, `dex2jar`)](/p/w6ste5j/)
* [APK ファイルを逆コンパイルする (apktool)](/p/xr3n5jk/)
* [APK ファイルの情報を表示する (aapt)](/p/qqrxpjd/)
* [ADB で Activity や Service を起動するインテントを投げる (am start/start-service/broadcast)](/p/eezeq4g/)
* [ADB で指定したアプリを強制終了する (am force-stop)](/p/9yse3iz/)
* [ADB で特定のブロードキャストインテントをレシーブするアプリを列挙する (dumpsys activity broadcasts)](/p/jfqtfyn/)
* [Androidメモ: ADB でディレクトリ内のファイルをすべて取得する (adb pull)](/p/36huwqe/)
* [ADB でスリープ状態に入る／スリープから抜ける](/p/cfqazd7/)
* [Androidメモ: ADB で WakeLock を強制的に外す](/p/xx3mb8e/)
* [ADB からキー入力やテキスト入力を行う (`input text`, `input keyevent`)](/p/gorux24/)
* [ADB で Android 端末のバージョンや API レベルを調べる (getprop)](/p/bg2g4bu/)
* [ADB で Android 端末にインストールされているパッケージの一覧を取得する (pm list packages)](/p/uh84kfj/)
* [Androidメモ: TCP/IP で adb 接続する](/p/492mmkw/)
* [旧）Android Studio で静的解析プラグインを使用する (CheckStyle, FindBugs)](/p/web92kb/)
* [ANR の原因を突き止める](/p/be5zj9v/)
* [Androidメモ: ADB 経由でスクリーンキャプチャを取得する (`screencap`)](/p/kp3ubcu/)
* [Androidメモ: StrictMode を有効にして望ましくない実装を検出する](/p/yvit8zb/)
* [Androidメモ: StrictMode の違反メッセージを Toast で表示する (`StrictModeToaster`)](/p/gv2dqi3/)
* Logcat
  * [Logcat のログ出力をフィルタする](/p/asryvzc/)
* 旧）各種デバイスの ADB 接続: <a style="display: inline" href="/p/5b6pmtc/">Nexus7 (2013)</a> /
  <a style="display: inline" href="/p/eq3tw6z/">Xperia Tablet Z</a> /
  <a style="display: inline" href="/p/gg59gt5/">Sony Tablet S</a> /
  <a style="display: inline" href="/p/4hhc7ba/">SHARP IS03</a>
* [旧）Androidメモ: Eclipse から Android SDK を使えるようにする (ADK)](/p/4ch6hjg/)


ビルド関連／Gradle
----

* [Android アプリ用の Gradle スクリプトの基本](gradle/gradle-for-android.html)
* [Android Gradleメモ: リソース名に正しくプレフィックスが付いているか確認する (`resourcePrefix`)](/p/4zogpen/)
* [Android Gradleメモ: Android の Gradle 関連ファイルまとめ](/p/8f3pcke/)
* [BuildConfig クラスでアプリの動作を切り替える](gradle/build-config.html)
* [サブモジュールで使用する SDK バージョンを統一する](gradle/align-sdk-versions.html)
* [外部から提供された AAR ファイルを利用する](gradle/external-aar.html)
* [APK ファイル名のサフィックスにバージョンを追加する](gradle/add-version-name-to-apk.html)
* [Lint エラーが発生した場合もビルドを継続する](gradle/ignore-lint-errors.html)
* [Android アプリが使用している依存ライブラリをツリー構造で表示する（dependencies/androidDependencies タスク）](gradle/dependency-tree.html)
* [Anrdoidトラブルシューティング: repo init でエラーが出る場合](trouble/repo-init-error.html)
* Gradle 全般の説明は [こちらを参照](/gradle/)


Android Studio
----

* [Androidメモ: AAR 形式のファイルを作成する/使用する](/p/3m9f8yv/)
* [Android Studio に割り当てるメモリを増やす](studio/increase-memory.html)
* [Android Studio で新規ファイル作成時に Copyright を自動挿入する](studio/auto-copyright.html)
* [Android Studio の便利なショートカット (1) コードの調査／メソッド間のジャンプ](studio/shortcut-method-jump.html)
* [Android Studio の便利なショートカット (2) ブックマーク](studio/shortcut-bookmark.html)


Androidベンダー向けメモ（Android デバイス開発者向け） <!-- vendor -->
----

* [Androidベンダー向けメモ: LowMemoryKiller の仕組み](/p/qmcauwd/)
* [Androidベンダー向けメモ: コンソールからキーの入出力を行う (`getevent/sendevent`)](/p/gufweuc/)
* [Androidベンダー向けメモ: root ユーザで adb 接続する](/p/4ucsovh/)
* Locale
  * [Androidベンダー向けメモ: Android の Locale を切り替える（Configuration 値の変更）](/p/dzocr3s/)
  * [Androidベンダー向けメモ: Android 4.0 でサポートされている Locale の一覧](/p/ypihqom/)
* [Androidベンダー向けメモ: ART/Dalvik VM のバージョンを調べる (`dalvikvm -showversion`)](/p/5at33s8/)
* [Androidベンダー向けメモ: make 後の生成イメージが格納されるディレクトリを調べる (`$ANDROID_PRODUCT_OUT`)](/p/xsdpj94/)
* [Androidベンダー向けメモ: インストールされた各 APK パッケージに割り当てられたユーザー ID を調べる (`packages.xml`)](/p/okshfzt/)
* [Androidベンダー向けメモ: Android デバイスのパーティション構成概要 (Nexus7)](/p/ks84q3k/)
* [Androidベンダー向けメモ: android/build/core 以下の `*.mk` ファイルで定義されている関数 (`define`) のリスト](/p/7zgfhqz/)
* [Androidベンダー向けメモ: 製品にパッケージングする APK を指定する (`PRODUCT_PACKAGES`)](/p/n8n3mwo/)
* [Androidベンダー向けメモ: Make 変数でビルド範囲を制御する](/p/mco3pis/)
* [Androidベンダー向けメモ: システムプロパティのあれこれ](/p/4yw4wwr/)
* [Androidベンダー向けメモ: `findmakefile` コマンドで `Android.mk` のあるディレクトリを調べる](/p/63w3r2r/)
* [Androidベンダー向けメモ: libhardware.so が hardware サポートライブラリをロードする仕組み](/p/6qsndck/)


Android アプリ実装／Framework／SDK
----

* [Androidメモ: Jetpack Compose の宣言型 (declarative) の UI 定義とは？](/p/4kdso44/)
* [Androidメモ: Activity の起動モードと起動フラグ（タスクとアフィニティ）](/p/4zpco9q/)
* [Androidメモ: SpeechRecognizer で音声入力を実現する](/p/bcmfu3e/)
* [Androidメモ: リソース ID を示す変数やパラメータにアノテーションを付ける](/p/33jn7zy/)
* [Androidメモ: AsyncTask による非同期処理と UI 更新処理](/p/o3hggfz/)

### 設定（Settings、SharedPreferences）

* [Androidメモ: SharedPreferences でアプリの設定値を保存する](/p/d7dho89/)
* [Androidメモ: Preference フレームワークを使って設定画面を簡単に作成する](/p/b9ij3zq/)
* [画面消灯 (SCREEN OFF) までの時間を取得・設定する](settings/screen-off-timeout.html)
* [Androidメモ: 現在のスクリーンレイアウトサイズを取得する (`Configuration`)](/p/7e9mdcp/)

### ファイル、I/O（入出力）、ネットワーク

* [Androidメモ: ネットワーク情報を取得する (`ConnectivityManager`, `NetworkInfo`)](/p/rm9ve9q/)
* [Androidメモ: Androidの特殊ディレクトリの情報を取得する (`android.os.Environment`)](/p/vjcad5m/)
* [Androidメモ: キーイベント (KeyEvent) を見やすく出力する](/p/8nbd6ca/)
* [Androidメモ: タッチイベントをハンドルする](/p/35cr5fp/)
* [Androidメモ: Emulator での開発時にキーボードによるキー入力が長押しかどうかを判別する](/p/2imy2j9/)

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

* [Androidメモ: 数値をある範囲内［min, max］に丸める (`MathUtils.clamp`)](/p/qyh8496/)

### Service（サービス）

* [同じプロセス内のサービスへバインドする (Local Bind)](service/local-bind.html)
* [別プロセスのサービスへバインドする (Remote Bind)](service/remote-bind.html)
* [サービスからコールバックできるようにする](service/callback.html)


Native レイヤー実装
----

* [Android Nativeメモ: C++ 実装用のスマートポインタ (`RefBase`, `sp`) を使用する](/p/w48omg6/)
* [Android Nativeメモ: ネイティブサービスの実装 (1) Binder 関連のクラス](/p/ds6rhco/)
* [Android Nativeメモ: ネイティブサービスの実装 (2) サービスの実装から利用まで](/p/edjwtcr/)
* [Android Nativeメモ: ネイティブサービスの実装 (3) サービスのインタフェースを定義する](/p/33w8db9/)
* [Android Nativeメモ: ServiceManager に登録されたサービスを列挙する](/p/qn635m9/)
* [Android Nativeメモ: ネイティブライブラリ (.so) の展開先](/p/9dzwawd/)

