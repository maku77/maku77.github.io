---
title: ユーザ入力を取得して "y" が入力された場合だけ処理を継続する
date: "2012-08-01"
---

#### sample.sh

```bash
#!/bin/bash

function remove_all {
    echo -n 'Are you sure? (y/n): '
    read line
    if [ "$line" != 'y' ]; then
        return
    fi

    # Process continues
    echo 'All things have been removed!'
}

remove_all
```

`if` の中で `$line` の内容を確認するときに、ダブルクォーテーションで囲んでいるのは、ユーザの入力がなかった場合に空文字 `""` を取得するためです。
これがないと、ユーザが何も入力せずにエンターした場合にエラーになります。


#### 実行例

```bash
$ ./sample.sh
Are you sure? (y/n): y
All things have been removed!
```

