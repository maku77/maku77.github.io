---
title: 組み込み関数の実行に失敗したときにエラー情報を表示する
created: 2016-03-20
---

MQL の組み込み関数の実行に失敗した時には、`GetLastError` によってそのエラーコードを取得できます。
さらに、このエラーコードを `ErrorDescription` に渡すことで、ユーザに表示するテキスト表現でエラー内容を取得することができます。
これらを使用するには、`stdlib.mqh` をインクルードしておく必要があります。

下記は、`ObjectCreate` の実行に失敗した時のエラー表示の例です。

```mql
// #include <stdlib.mqh>

if (!ObjectCreate(0 , name, OBJ_LABEL, subWindow, 0, 0)) {
    Alert("Failed to create a label: ", ErrorDescription(GetLastError()));
    return;
}
```

表示例:

```
Failed to create a label: object already exists
```

