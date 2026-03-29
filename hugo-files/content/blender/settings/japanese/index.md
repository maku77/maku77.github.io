---
title: "Blenderメモ: メニューなどを日本語表示にする"
url: "p/47yzsk9/"
date: "2018-03-13"
tags: ["blender"]
aliases: [/blender/settings/japanese.html]
---

Blender アプリケーション内の表示は、デフォルトではすべて英語表示になっています。
次のように設定することで、日本語表示に切り替えることができます。

{{< image w="500" border="true" title="日本語表示の設定" src="img-001.png" >}}

1. メニューから <kbd><samp>File</samp></kbd> → <kbd><samp>User Preferences...</samp></kbd> を選択（あるいは <kbd>Cmd + ,</kbd>）
1. <kbd><samp>System</samp></kbd> タブを選択
1. <kbd><samp>International Fonts</samp></kbd> にチェック
1. <samp>Language</samp> の項目で <kbd><samp>Japanese（日本語）</samp></kbd> を選択
1. <samp>Translate</samp> の項目で翻訳したい項目 (<kbd><samp>Interface</samp></kbd> / <kbd><samp>Tooltip</samp></kbd> / <kbd><samp>New Data</samp></kbd>) を ON

最後のステップで <kbd><samp>Interface</samp></kbd> のボタンを押した瞬間に、ダイアログ上のテキスト表示が日本語に変わります。

{{< image w="500" border="true" src="img-002.png" >}}

<kbd><samp>Tooltip</samp></kbd> を ON にしておくと、ボタンなどにマウスカーソルを合わせたときに表示されるツールチップが日本語になります。

{{% note %}}
<kbd><samp>New Data</samp></kbd> を ON にすると、新規作成するオブジェクトの名前が日本語になってしまい、不都合が生じる可能性があるので、ここは OFF のままにしておくのがオススメです（オブジェクト名は英語で扱う）。
{{% /note %}}

