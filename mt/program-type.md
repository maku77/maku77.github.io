---
title: 関数を実行しているのがスクリプトか、EA なのか、インジケータなのかを判別する
date: "2015-10-08"
---

`MQL5InfoInteger` にパラメータとして `MQL5_PROGRAM_TYPE` を指定すると、その関数の呼び出し元がスクリプトなのか、EA なのか、インジケータなのかを調べることができます。

```mql
bool isCalledFromScript() {
    return MQL5InfoInteger(MQL5_PROGRAM_TYPE) == PROGRAM_SCRIPT;
}

bool isCalledFromEa() {
    return MQL5InfoInteger(MQL5_PROGRAM_TYPE) == PROGRAM_EXPERT;
}

bool isCalledFromIndicator() {
    return MQL5InfoInteger(MQL5_PROGRAM_TYPE) == PROGRAM_INDICATOR;
}
```

