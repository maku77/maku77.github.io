---
title: "Blenderメモ: 常に選択したオブジェクトを中心に視点を回転する"
url: "p/krt9jir/"
date: "2018-04-01"
tags: ["blender"]
aliases: [/blender/settings/rotate-around-selection.html]
---

マウスの中央ボタンなどをドラッグして視点を回転させるとき、デフォルトでは回転の中心は、画面の中央になっています。
<kbd>Shift</kbd>+中央ボタンで視点の位置を平行移動していると、この回転の中心がずれてしまいます。
常に選択したオブジェクトが画面に収まるように回転させるようにするには、以下のように設定します。

{{< image w="800" border="true" src="img-001.png" >}}

1. メニューから <kbd><samp>ファイル</samp></kbd> → <kbd><samp>ユーザ設定</samp></kbd> を選択（あるいは <kbd>Cmd + ,</kbd>）
1. <kbd><samp>インターフェイス</samp></kbd> タブを選択
1. <kbd><samp>選択範囲を中心に回転</samp></kbd> にチェックを入れる
1. <kbd><samp>ユーザ設定の保存</samp></kbd> ボタンをクリック

