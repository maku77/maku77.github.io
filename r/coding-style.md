---
title: "R のコーディングスタイル"
date: "2015-01-27"
---

下記は Google が採用している R のコーディングスタイルを参考にしています。

* インデントはスペース **2** 文字
* 一行 **80** 文字まで
* 代入は `<-` を使用する（`=` は代入には使わない）
* `attach` は使わない
* エラーの生成には `stop()` を使う
* **S3** オブジェクトを使う（S4 オブジェクトを使わない）
* 命名規則
  - `FunctionName`
  - `variableName`
  - `kConstantName`
