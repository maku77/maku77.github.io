---
title: "テンプレートの中で絵文字を使用する (emojify)"
date: "2020-04-13"
---

emojify 関数の概要
----

Hugo のテンプレートファイルの中で [emojify 関数](https://gohugo.io/functions/emojify/) を使用すると、GitHub や Slack などで使える有名どころの絵文字 (emoticons) を出力することができます。
使えるアイコンのリストは下記サイトで確認できます。

* [🎁 Emoji cheat sheet for GitHub, Basecamp, Slack & more](https://www.webfx.com/tools/emoji-cheat-sheet/)

例えば、`:smile:` アイコンを表示したければ、任意のテンプレートファイルの中で次のように記述します。

```
{{ "{{" }} emojify ":smile:" }}
```

emojify 関数の使用例
----

下記の例では、Hugo をサーバーモードで起動しているときに、ローカルファイルのパスをアイコン付きで表示します。

#### layouts/_default/single.html（抜粋）

```
{{ "{{" }} if .Site.IsServer }}
  {{ "{{" }} emojify ":memo:" }}{{ "{{" }} .File.Filename }}
{{ "{{" }} end }}
```

#### 表示結果

![emojify-001.png](emojify-001.png)

