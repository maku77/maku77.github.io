---
title: "Android エミュレーターをコマンドラインから操作する (emulator, adb emu)"
url: "p/zgyhygw/"
date: "2022-11-08"
tags: ["Android"]
---

emulator コマンドと adb コマンド
----

Android SDK に付属している __`emulator`__ コマンドや __`adb`__ コマンドを使うことで、Android のエミュレーターを操作することができます。

emulator コマンド
: エミュレーター端末 (= AVD: Android Virtual Device) の一覧を表示したり、エミュレーター端末を起動したりするのに使うコマンドです。

adb コマンド
: エミュレーター端末が起動した後に、コマンドを送り込んで各種操作を行います。`adb` はエミュレーター専用のコマンドではありませんが、__`adb emu`__ のようなエミュレーターに特化したサブコマンドがあります。

`emulator` コマンドは、Android SDK をインストールしたディレクトリの `emulator` ディレクトリに格納されています。
例えば次のようなパスにあります（ユーザー名が `maku` の場合）。

- Windows の場合: `C:\Users\maku\AppData\Local\Android\Sdk\emulator\emulator.exe`
- macOS の場合: `/Users/maku/Library/Android/sdk/emulator/emulator`

次のような感じで OS のパスを通しておくと、どこからでもコマンドを実行できるようになります。

{{< code lang="bash" title="~/.zlogin（macOS で zsh の場合）" >}}
export ANDROID_HOME=~/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
{{< /code >}}

{{% note title="emulator/emulator と tools/emulator" %}}
`tools` ディレクトリにも同名の `emulator` コマンドがあったりしますが、こちらではなく、`emuator` ディレクトリの方にあるコマンドを使うのが正解のようです。
なので、パスを通すときは、`emulator` ディレクトリが先に来るように指定しておく必要があります。
{{% /code %}}


emulator コマンドの使用例
----

{{< code lang="console" title="AVD（エミュレーター端末）のリストを表示する (emulator -list-avds)" >}}
$ emulator -list-avds
phone-s
phone-t
tv-s
tv-t
{{< /code >}}

AVD 名には、上記のようなシンプルな名前を付けておくのがおすすめです。
エミュレーターを起動するときに AVD を指定するのが楽になります。

{{< code lang="console" title="エミュレーターを起動する (emulator)" >}}
$ emulator @AVD_NAME
$ emulator -avd AVD_NAME
{{< /code >}}

ここで指定する AVD 名は、前述の `emulator -list-avds` コマンドで表示されたものの中から選択します。
AVD 名の一覧を表示して、ユーザーに起動する AVD を選択させるバッチファイルなどを作っておくと便利です。

{{% accordion title="emu.cmd（AVD を選択して起動するバッチファイル）" %}}
```bat
@echo off
setlocal

echo Available AVDs:
emulator -list-avds

set /p AvdName="Which AVD do you want to start? "
if "%AvdName%"=="" (
    echo ERROR: No AVD specified.
    exit /b
)
emulator @%AvdName%
```
{{% /accordion %}}


adb コマンドの使用例
----

{{< code lang="console" title="稼働中のエミュレーターの一覧を表示する (adb devices)" >}}
$ adb devices
List of devices attached
emulator-5554	device
emulator-5556	device
{{< /code >}}

上記の例では、2 つのエミュレーターが起動しており、それぞれのシリアル（識別子のようなもの）は `emulator-5554` と `emulator-5556` であることがわかります。
複数のエミュレーターが起動している場合、ほとんどのケースでは `adb` コマンド実行時に操作対処とするエミュレーターを __`-s`__ オプションで指定する必要があります。

{{< code lang="console" title="シリアルを指定して adb コマンド実行" >}}
$ adb -s emulator-5554 logcat
{{< /code >}}

`adb devices` コマンドで __`-l`__ オプションを指定すると、さらに詳細な情報を表示できます。

```console
$ adb devices -l
List of devices attached
emulator-5554  device product:sdk_gphone64_arm64 model:sdk_gphone64_arm64 device:emulator64_arm64 transport_id:1
emulator-5556  device product:sdk_google_atv64_amati_arm64 model:sdk_google_atv64_amati_arm64 device:emu64a transport_id:2
```

この出力の末尾には、各エミュレーター端末のトランスポート ID が表示されており、これをシリアルの代わりに使うことができます。

```console
$ adb -t 1 logcat
```

{{< code lang="console" title="エミュレーターを停止する (adb emu kill)" >}}
$ adb emu kill
$ adb -s emulator-5554 emu kill  # 複数のエミュレーターを起動している場合
{{< /code >}}

画面上からエミュレーターが消えているのに、なぜかエミュレーターが起動し続けている（`adb devices` のリストから消えない）という状態になってしまった場合は、このコマンドを実行すれば確実に終了できます。

### Android OS を再起動する / AVD を再起動する

{{< code lang="console" title="Android の再起動" >}}
$ adb reboot
{{< /code >}}

エミュレーター内で動作している Android を再起動します。
`BOOT_COMPLETED` インテントまわりのテストをするときに必要になったりします。
エミュレーター (AVD) 自体を再起動したいときは次のように実行します。

{{< code lang="console" title="AVD (Emulator) の再起動" >}}
$ adb emu restart
{{< /code >}}

{{< code lang="console" title="その他の adb emu サブコマンド" >}}
$ adb emu rotate         # 画面を回転
$ adb emu avd name       # AVD 名を表示
$ adb emu avd status     # AVD の状態を表示
$ adb emu avd bugreport  # バグレポートを生成
{{< /code >}}

`adb emu` コマンドにはあまりドキュメントがなかったりしますが、__`adb emu help-verbose`__ で簡単なコマンド一覧を表示できます。
もしかすると便利なコマンドが見つかるかもしれません。

