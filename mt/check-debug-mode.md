---
title: デバッグモードでコンパイルされているか調べる
date: "2015-10-24"
---

MQL のプログラムが、デバッグモードとリリースモードのどちらでコンパイルされているかを調べるには、`#ifdef` プリプロセッサで下記のマクロが定義されているかどうかを調べます。

* `_DEBUG` -- デバッグモードでコンパイルされている（MetaEditor 上で最後に F5 で実行したとき）
* `_RELEASE` -- リリースモードでコンパイルされている（MetaEditor 上で最後に F7 でコンパイルしたとき）

```mql
void OnStart() {
    #ifdef _DEBUG
        Print("Run in debug mode");
    #else
        Print("Run in release mode");
    #endif
}
```

上記はプリプロセッサで調べる例ですが、実行時に動的にチェックすることもできるようです。

```mql
// MQL5 の場合
bool isDebug = MQL5InfoInteger(MQL5_DEBUGGING);

// MQL4 の場合
bool isDebug = IS_DEBUG_MODE;
```

