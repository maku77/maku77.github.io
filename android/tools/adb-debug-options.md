---
title: "ADB で描画パフォーマンス計測のデバッグ機能を有効にする"
url: "/p/9knsuts"
date: "2021-01-29"
---

Android の開発者向けオプション (Developer options) には、描画パフォーマンスなどを測定するための便利なツールが揃っています。
特に、GPU オーバードロー領域の表示や、レンダリングプロファイル（画面上にバー表示）は、描画パフォーマンス 60 FPS を達成するために必須のツールです。

でも、これらの設定項目は設定メニューの深いところにあって、ホーム画面に戻って毎回切り替えるのは手間がかかります。
実はこれらのオプション設定は、`adb shell` から `setprop` コマンドを実行することで簡単に切り替えることができます。

コマンドからこれらの設定を変更した場合は、すぐに設定が有効にならないことがあるので注意してください。
その場合は、アプリの再起動や、アプリ内での画面遷移などを発生させると有効になるようです。


各種デバッグ項目の ADB からの設定方法
----

### Show view updates（GPU表示の更新を表示）

ON にすると、GPU での描画時にウィンドウ内の表示を点滅させます。
(Flash views inside windows when drawn)

| 設定方法 | 意味 |
| ---- | ---- |
| `adb shell setprop debug.hwui.show_dirty_regions false` | GPU描画点滅を OFF |
| `adb shell setprop debug.hwui.show_dirty_regions false` | GPU描画点滅を ON |

### Show hardware layers updates（ハードウェア層情報を表示）

ON にすると、ハードウェア層が更新されると緑を表示します。
(Flash hardware layers green when they update)

GPU を活用した描画が行えているのであれば、GPU 層での更新は頻繁に発生するものではありません。
新しいビューが生成されたときのみ緑色のフラッシュが発生していれば OK です。

| 設定方法 | 意味 |
| ---- | ---- |
| `adb shell setprop debug.hwui.show_layers_updates false` | HW層更新表示を OFF |
| `adb shell setprop debug.hwui.show_layers_updates true` | HW層更新表示を ON |

### Debug GPU overdraw（GPU オーバードローをデバッグ）

ON にすると、何度も重ねて描画されてしまっている部分を色付きで表示します。
緑色や赤色の矩形がたくさん出てきたら、レイアウトの階層構造や、描画まわりの実装を見直す必要があります。

| 設定方法 | 意味 |
| ---- | ---- |
| `adb shell setprop debug.hwui.overdraw false` | OFF |
| `adb shell setprop debug.hwui.overdraw show` | __オーバードロー領域の表示__ |
| `adb shell setprop debug.hwui.overdraw show_deuteranomaly` | 第二色弱の方向けの領域の表示 |

- 参考: [GPU レンダリングの速度とオーバードローを検査する](https://developer.android.com/topic/performance/rendering/inspect-gpu-rendering?hl=ja)

### Profile HWUI rendering（GPU レンダリングのプロファイル作成）

設定を `visual_bars` にすると、描画時にフレームあたり何ミリ秒かかったかを画面上の棒グラフで表示してくれます。
緑色の水平線以下にあれば、16.66ミリ秒、つまり 60FPS での描画を達成できています。

| 設定方法 | 意味 |
| ---- | ---- |
| `adb shell setprop debug.hwui.profile false` | レンダリングプロファイルを OFF |
| `adb shell setprop debug.hwui.profile visual_bars` | __バーとして画面に表示__ |
| `adb shell setprop debug.hwui.profile true` | dumpsys gfxinfo での確認用 |

- 参考: [GPU レンダリングの速度とオーバードローを検査する](https://developer.android.com/topic/performance/rendering/inspect-gpu-rendering?hl=ja)


（おまけ）設定項目のプロパティ名はどうやって調べたの？
----

次のようにすると、Android 端末の現在のプロパティ設定の一覧を取得できます。

```
adb shell getprop
```

あらかじめ上記の結果をファイルに保存しておいて、開発者向けオプションの設定を変更したときに値が変化する項目を調べれば、その設定項目のプロパティ名（および設定値）が分かります。

1. `adb shell getprop > props1.txt`
2. 開発者向けオプションで設定を変更
3. `adb shell getprop > props2.txt`
4. 比較
    - Windows: `fc props1.txt props2.txt | findstr debug`
    - Linux: `diff props1.txt props2.txt | grep debug`

プロパティ名は Android のバージョンによって変わる可能性がありますが、この方法でプロパティ名を調べれば対応できます。

