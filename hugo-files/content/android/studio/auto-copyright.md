---
title: "Androidメモ: Android Studio で新規ファイル作成時に Copyright を自動挿入する"
url: "p/hv7we2a/"
date: "2016-03-22"
tags: ["android"]
aliases: ["/android/studio/auto-copyright.html"]
---

Android Studio で下記のコピーライト設定を行っておくと、新規ファイルを作成したときに、ファイルの先頭に自動でコピーライト表記を挿入してくれるようになります。

1. Android Studio のメニューから [File] → [Settings] を選択
2. Settings ウィンドウから [Editor] → [Copyright] → [Copyright Profiles] を選択
3. [+] ボタンを押して任意のコピーライト設定を行う
4. [Editor] → [Copyright] に戻り、上記で作成したコピーライト設定を選択する

ステップ 3 での Copyright text の設定では、純粋にコピーライト表記用の文言だけを記入すれば OK です。

```
Copyright 2016 MakuMaku Corporation
```

上記のように記入しておけば、あとは作成するファイルの種類によって、自動的にコメント化されて挿入されるようになります。

```java
/*
 * Copyright 2016 MakuMaku Corporation
 */
```

