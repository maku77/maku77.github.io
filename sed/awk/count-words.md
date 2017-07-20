---
title: awk でテキストファイル内の単語数と行数をカウントする
created: 2002-08-10
---

下記の awk スクリプトは、テキスト内の単語数と行数をカウントして表示します。
Linux の `wc` コマンドを使っても同様のことを行えるので、`wc.awk` というファイル名にしています。

`NF` で各行のフィールド数、`FNR` で読み込んだ行数を取得できることを利用しています。

#### wc.awk
```awk
{
    n += NF
}

END {
    print n, "words,", FNR, "lines"
}
```

#### 使用例

```
$ awk -f wc.awk input.txt
150 words, 80 lines
```

