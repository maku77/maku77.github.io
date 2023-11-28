---
title: "Python の dictionary（辞書）のチートシート"
url: "p/2tyvjmh/"
date: "2023-11-28"
tags: ["cheatsheet", "python"]
---

| コード | 説明 |
| ---- | ---- |
| `d = {}`<br>`d = dict()` | [dictionary の作成](/p/zpdyxso/#create) |
| `d = {"key1": 100}`<br>`d = dict(key1=100)` | [dictionary の作成と初期化](/p/zpdyxso/#create) |
| `d = dict(zip(["one", "two", "three"], [1, 2, 3]))` | [キーのリストと値のリストから dictionary を作成](/p/cmy6ar3/) |
| `len(d)` | [要素数](/p/zpdyxso/#len) |
| `d["key"] = 100` | 要素を追加・上書き |
| `val = d["key"]` | [要素を取得（キーが存在しないときは `KeyError`）](/p/zpdyxso/#get) |
| `val = d.get("key", default=0)` | [要素を取得（キーが存在しないときはデフォルト値を返す）](/p/zpdyxso/#get) |
| `val = d.setdefault("key", 0)` | [要素を取得（キーが存在しないときはデフォルト値をセットして返す）](/p/bq6yzpr/) |
| `keys = d.keys()` | [キーのリストを取得](/p/83e3wrw/) |
| `vals = d.values()` | [値のリストを取得](/p/83e3wrw/) |
| `items = d.items()` | [(キー, 値) のリストを取得](/p/83e3wrw/) |
| `if "key" in d:` | [キーが存在するか調べる](/p/zpdyxso/#in) |
| `del d["key"]` | [要素を削除する（キーが存在しないときは `KeyError`）](/p/zpdyxso/#del) |
| `for key in d:` | [キーでループ](/p/zpdyxso/#loop) |
| `for key, val in d.items():` | [キーと値でループ](/p/zpdyxso/#loop) |
| `for i, key in enumerate(d):` | [連番とキーでループ](/p/zpdyxso/#loop) |
| `for key in sorted(d.keys()):`<br>`for key in sorted(d.keys(), reverse=True):` | [キーでソートしてループ（昇順/降順）](/p/qqkggoz/#sort-by-key) |
| `for key in sorted(d, key=lambda x:d[x]):`<br>`for key in sorted(d, reverse=True, key=lambda x:d[x]):` | [値でソートしてループ（昇順/降順）](/p/qqkggoz/#sort-by-value) |
| `d1.update(d2)` | [`d1` に `d2` をマージ](/p/ds9wgfz/) |
| `d3 = {**d1, **d2}` | [`d1` と `d2` をマージした `d3` を作成](/p/ds9wgfz/) |

