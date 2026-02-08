---
title: "JavaScriptメモ: JavaScript において偽 (false) と評価される値"
url: "p/x7qigy6/"
date: "2015-02-11"
tags: ["javascript"]
aliases: [/js/syntax/false-values.html]
---

JavaScript では下記のような値は、「偽 (false)」と判断されます。

- `false`
- `undefined`
- `null`
- `0`
- `-0`
- `NaN`
- `''` (the empty string)

下記の値は、JavaScript では真 (true) と判断されることに注意してください。

- `[]`  （空の配列）
- `'0'` （1文字以上の文字列は常に真）
