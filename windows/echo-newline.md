---
title: "echo で改行だけ出力する／改行しないで出力する"
date: "2014-08-04"
lastmod: "2021-03-10"
---

改行だけ出力する
----

下記のように、`echo` コマンドの直後に記号を付けると、空白行を出力することができます（`echo` の直後にスペースを入れないことに注意）。

```bat
echo;
echo:
echo.
echo,
echo+
```

`echo` コマンドを引数なしで実行すると、現在の `echo` 設定が表示されてしまう（例: `ECHO is off.`）ので、上記のように記号を指定する必要があります。


改行しないで出力する
----

逆に、行末で改行しないで `echo` 出力する方法はないようです。

裏技的なものとしては、下記のようにすると、改行なしの `echo` もどきとして使えるというのがあります。

```bat
C:\> set /p x=Hello<nul
Hello
```

`set /p x=Hello` というコマンドは、プロンプトとしてユーザーに `Hello` と表示しつつ、ユーザー入力を取得して変数 `x` に格納するということを表しています。
このユーザー入力を強引に空入力 `<nul` にすることで、プロンプトだけ表示するという振る舞いにしています。
結果的に画面上に `Hello`（改行なし）とだけ表示されることになります。

副作用として、変数 `x` は削除されてしまうことに注意してください（確実に使用していない変数名を使えば安全です）。

