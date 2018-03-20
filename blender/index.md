---
title: "Blender 操作リファレンス"
layout: category-index
---

設定
----
- [メニューなどを日本語表示にする](settings/japanese.html)
- [テンキーなしキーボードでテンキーをエミュレートする](settings/tenkeys.html)


基本操作
----
- [視点を操作する (Navigation)](basic/navigation.html)
- [下絵を表示する](basic/underdrawing.html)
- [オブジェクトを追加する (Add)](basic/add.html)
- [オブジェクトを移動する (Move)](basic/move.html)
- [オブジェクトを回転する (Rotate)](basic/rotate.html)
- [オブジェクトを拡大／縮小する (Scale)](basic/scale.html)

エディタの操作
----
- [エディタを最大化する](ui/expand-editor.html)

リファレンス
----
- [Blender の基本操作まとめ](op/)
- [Blender API Reference](https://docs.blender.org/api/blender_python_api_current/)
- Python で Blender を操作する <!-- scripting/ -->


{% if jekyll.environment == "development" %}
<h3>記述ポリシー</h3>
編集操作の画面キャプチャは LICEcap などで **400x300 (MaxFPS:8)** で作成。
ウィンドウ全体の画面キャプチャは **600x400** で作成（クライアント領域がそこにちょうど収まるようにしてレコーディング）。

ホットキーは、次のようなフォーマットで記述する。

- マウス: <kbd>LMB</kbd> / <kbd>RMB</kbd> / <kbd>MMB</kbd> / <kbd>Wheel</kbd>
- キーボード: <kbd>A</kbd> / <kbd>B</kbd> / <kbd>Numpad0</kbd> / <kbd>Esc</kbd> / <kbd>Tab</kbd> / <kbd>Space</kbd> / <kbd>Left</kbd> / <kbd>Right</kbd> / <kbd>Up</kbd> / <kbd>Down</kbd>
- 同時押し: <kbd>Shift-Alt-A</kbd>, <kbd>Ctrl-W</kbd>, <kbd>Shift-S</kbd>, <kbd>Shift-Drag</kbd>, <kbd>Alt-F1</kbd>

メニューを辿る場合は、

- <kbd><kbd><samp>File</samp></kbd> → <kbd><samp>Foo</samp></kbd> → <kbd><samp>Bar</samp></kbd></kbd>

のように表現。
{% endif %}
