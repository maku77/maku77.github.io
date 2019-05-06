---
title: "Blender"
layout: category-index
---

Blender の設定
----
- [メニューなどを日本語表示にする](settings/japanese.html)
- [テンキーなしキーボードでテンキーをエミュレートする](settings/emulate-tenkeys.html)
- [常に選択したオブジェクトを中心に視点を回転する](settings/rotate-around-selection.html)
- [自動バックアップとリストア](settings/backup.html)
- [ショートカットキー（ホットキー）の割り当てを確認する](settings/shortcut-keys.html)
- [3D ビューでのキー操作を画面に表示する (Screencast Keys)](settings/screencast-keys.html)


Blendar の画面
----
### 構成要素
- [画面の構成要素を理解する](ui/window.html)

### 各種エディタ共通の操作
- [エディタを最大化する](ui/expand-editor.html)
- [エディタの表示領域をスワップする](ui/swap-editors.html)
- [エディタを新規ウィンドウとして複製する](ui/duplicate-window.html)
- [3D ビューエディタのツールシェルフの位置を変更する](ui/flip-regions.html)


モデリング
----

### 「3Dビュー」エディタ
- [3D カーソルを原点（中心）に移動する (Shift + S)](3dview/snap-cursor-to-center.html)
- [視点を操作する (Navigation)](basic/navigation.html)
- [下絵を表示する](basic/underdrawing.html)
- [オブジェクトを追加する (Add) (Shift + A)](3dview/add.html)
- [オブジェクトを移動する (Move) (G)](3dview/move.html)
- [オブジェクトを回転する (Rotate) (R)](3dview/rotate.html)
- [オブジェクトを拡大／縮小する (Scale) (S)](3dview/scale.html)
- [直前の編集操作を繰り返す（リピート実行）(Shift + R)](3dview/repeat.html)
- [ループカットとスライドで辺を追加する (Ctrl + R)](3dview/loopcut.html)

テクスチャ
----

### 「UV/画像」エディタ
- [UV マッピングの基本](texture/uv-mapping.html)


リファレンス
----
- [Blender のショートカットキーまとめ](shortcut/)
- [Blender API Reference](https://docs.blender.org/api/blender_python_api_current/)
- Python で Blender を操作する <!-- scripting/ -->

{% if jekyll.environment == "development" %}
<h3 style="color:red">記述ポリシー（プライベートメモ）</h3>

編集操作の画面キャプチャは LICEcap などで **400x225 (16:9) (MaxFPS:8)** で作成。
ウィンドウ全体の画面キャプチャは **800x450 や 640x360 (16:9)** で作成（クライアント領域がそこにちょうど収まるようにしてレコーディング）。

ホットキーは、次のようなフォーマットで記述する。

- マウス: <kbd>LMB</kbd> / <kbd>RMB</kbd> / <kbd>MMB</kbd> / <kbd>Wheel</kbd>
- キーボード: <kbd>A</kbd> / <kbd>B</kbd> / <kbd>Numpad0</kbd> / <kbd>Esc</kbd> / <kbd>Tab</kbd> / <kbd>Space</kbd> / <kbd>Left</kbd> / <kbd>Up</kbd>
- 同時押し: <kbd>Shift + Alt + A</kbd> / <kbd>Ctrl + F1</kbd> / <kbd>Shift + Drag</kbd>

メニューを辿る場合は、

- <kbd><samp>File</samp></kbd> → <kbd><samp>Foo</samp></kbd> → <kbd><samp>Bar</samp></kbd>

のように表現。
{% endif %}

