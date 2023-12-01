---
title: "JavaScript の文字列処理チートシート"
url: "p/y4ccxc8/"
date: "2023-12-01"
tags: ["cheatsheet", "javascript"]
draft: true
---

| コード | 説明 |
| ---- | ---- |
| `const arr = s.split("")` | [文字列を 1 文字ずつに分割する](/p/dpp4v8n/) |
| `const arr = s.split(",")` | [文字列を区切り文字で分割する](/p/dpp4v8n/) |
| `const arr = s.trim().split(/\s*,\s*/)` | [文字列を分割する（前後の空白を削除）](/p/dpp4v8n/) |
| `const idx = s.search(/pattern/i)` | [文字列の検索（正規表現）=> インデックス](/p/p5nx3n9/)
| `const re = /java/i; const b = re.test(s);` | [文字列の検索（正規表現）=> true/false](/p/p5nx3n9/)
| `const s2 = s.replace("from", "to")` | [文字列の置換（1 つだけ）](/p/8pnuzk4/) |
| `const s2 = s.replaceAll("from", "to")` | [文字列の置換（すべて）](/p/8pnuzk4/) |
| `const s2 = s.replace(/from/gi, "to")` | [文字列の置換（正規表現）](/p/8pnuzk4/) |
| `const arr = s.match(/\d+/g)` | [部分文字列の抽出](/p/tvuztbm/) |
| `const arr = s.match(/(\d+).(\d+)/)` | [部分文字列の抽出（グルーピング）](/p/tvuztbm/) |
| `RegExp#exec(s) // 省略` | [部分文字列の抽出（最強）](/p/tvuztbm/) |
| `const s2 = s.padStart(10, "x")` | [指定した文字数になるまで前にパディング](/p/buatano/) |
| `const s2 = s.padEnd(10, "x")` | [指定した文字数になるまで後ろパディング](/p/buatano/) |

