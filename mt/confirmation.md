---
title: "スクリプトを実行する前に確認画面を表示する"
date: "2014-11-11"
---

スクリプトに下記のプロパティを設定しておくと、スクリプトを実行する前に、確認ダイアログが表示されるようになります。
注文、決済を行うようなプログラグムは、誤って実行してしまわないように、これを設定しておくと安心です。

```mql
#property show_confirm
```