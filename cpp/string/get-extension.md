---
title: "ファイルパスから拡張子を取得する"
date: "2005-08-22"
---

以下のサンプルでは、`/home/maku/sample.txt` というファイル名から `.txt` という拡張子を取得しています。
`strrchr()` で `.` の位置を探して拡張子部分を抽出しています。
取得した文字列はドットを含むことに注意してください。

~~~ cpp
#include <stdio.h>
#include <string.h>

int main(int argc, char **argv) {
    const char *fileName = "/home/maku/sample.txt";
    const char *ext = strrchr(fileName, '.');

    if (ext) {
        printf("Extension is %s\n", ext);
    } else {
        printf("No extension\n");
    }

    return 0;
}
~~~

#### 出力例

~~~
Extension is .txt
~~~

