---
title: 画面上にテキスト出力する (2) Comment 関数を使いやすくする
created: 2015-02-01
layout: mt
---

チャートの左上にテキストメッセージを表示する `Comment` 関数は、続けて呼び出すと、最後に指定したメッセージで表示が上書きされてしまいます。次のような関数を用意しておけば、過去に出力したメッセージを残しながら追加出力していくことができます。

```mql
void Debug(string msg) {
    static string lines = "";
    lines = msg + "\n" + lines;
    Comment(lines);
}
```

#### 使用例

```mql
void OnStart() {
    Debug("AAA");
    Debug("BBB");
    Debug("CCC");
}
```

上記の `Debug` 関数は、呼び出すごとにどんどんメッセージの行数が増えていってしまいますが、出力を最大 10 行に抑えたい場合などは、例えば以下のようにします。

```mql
#include <Arrays\ArrayString.mqh>

void Debug(string msg) {
    static const int MAX_LINES = 10;
    static CArrayString lines;
    int len = lines.Total();
    if (len > MAX_LINES - 1) {
        lines.Delete(0);
        --len;
    }
    lines.Add(msg);
    string text = "";
    for (int i = len; i >= 0; --i) {
        text += lines.At(i) + "\n";
    }
    Comment(text);
}
```

