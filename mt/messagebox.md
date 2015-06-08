---
title: メッセージボックスを表示する
created: 2015-02-01
---

MessageBox 関数を使用すると、Yes/Cancel ボタンなどを表示し、ユーザの意思を確かめることができます。

* https://www.mql5.com/en/docs/common/messagebox

```mql
int ret = MessageBox("Are you sure?", "", MB_OKCANCEL | MB_ICONQUESTION);
if (ret == IDOK) {
    // OK button was pressed
}
```
