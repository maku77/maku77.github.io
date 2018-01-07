---
title: R 環境の Locale 設定（日本語を使用する）
date: "2015-12-28"
---

R コンソールを起動したときに、システムの Locale 設定が正しく行われていないと、下記の様な警告メッセージが表示されます。

#### Mac OSX での Locale 設定の警告メッセージ

```
During startup - Warning messages:
1: Setting LC_CTYPE failed, using "C"
2: Setting LC_COLLATE failed, using "C"
3: Setting LC_TIME failed, using "C"
4: Setting LC_MESSAGES failed, using "C"
5: Setting LC_MONETARY failed, using "C"
[R.app GUI 1.66 (7060) x86_64-apple-darwin13.4.0]

WARNING: You're using a non-UTF8 locale, therefore only ASCII characters will work.
Please read R for Mac OS X FAQ (see Help) section 9 and adjust your system preferences accordingly.
```

R コンソールで正しくメッセージ表示を行うには、少なくとも UTF-8 を使用する設定を行っておく必要があります。


Mac OSX でシステムの Locale 設定を変更する
----

Mac の Terminal コンソールで `defaults` コマンドを使用すると、システムの Locale 設定を変更することができます。
ヘルプメッセージなどを日本語にしたい場合は、`js_JP.UTF-8` の方を指定してください。

```
$ defaults write org.R-project.R force.LANG en_US.UTF-8  # 英語表示
$ defaults write org.R-project.R force.LANG ja_JP.UTF-8  # 日本語表示
```

上記を実行してから R コンソールを立ち上げ直すと、正しく Locale 設定が反映されます。
あるいは、R コンソールから下記の様に設定することもできます。

```r
> system("defaults write org.R-project.R force.LANG ja_JP.UTF-8")
```

`locale` コマンドを実行すると、現在の設定を確認することができます。

```r
> system("locale")
LANG="ja_JP.UTF-8"
LC_COLLATE="ja_JP.UTF-8"
LC_CTYPE="ja_JP.UTF-8"
LC_MESSAGES="ja_JP.UTF-8"
LC_MONETARY="ja_JP.UTF-8"
LC_NUMERIC="ja_JP.UTF-8"
LC_TIME="ja_JP.UTF-8"
LC_ALL=
```


参考
----
* [R for Mac OS X FAQ - CRAN Project](https://cran.r-project.org/bin/macosx/RMacOSX-FAQ.html#Internationalization-of-the-R_002eapp)

