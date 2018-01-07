---
title: "トラブル: Systrace の画面が真っ白になる"
date: "2016-06-08"
---

Android の DDMS などで利用可能な systrace を実行すると、trace.html ファイルが生成されますが、この中で非推奨になった JavaScript API(Object.observe) を使用しているため、最新の Chrome では表示できない問題が出ています。

- 参考: http://stackoverflow.com/questions/36865899/react-native-android-systrace-html-is-blank-in-ubuntu-14-04/37008945

上記サイトでの回避策としては、生成された HTML のヘッダに下記を追加することがあげられています。

```
<script src="https://rawgit.com/MaxArt2501/object-observe/master/dist/object-observe.min.js"></script>
```

生成される HTML ファイルを毎回修正するのが面倒な場合は、Android SDK のディレクトリにある下記のファイルを編集するという方法もあります。
このファイルに追記した内容は、生成される HTML にも反映されるようになります。

#### $ANDROID_SDK/platform-tools/systrace/prefix.html

```html
<!DOCTYPE HTML>
<html>
<head i18n-values="dir:textdirection;">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8"/>
<script src="https://rawgit.com/MaxArt2501/object-observe/master/dist/object-observe.min.js"></script>  ★これを追加
```

