---
title: リポジトリ名のついていない Docker イメージをすべて削除する
created: 2015-04-02
---

普通に Docker コンテナ上で編集作業などを行っていると、Docker イメージのキャッシュがどんどん増えていってしまいます。

```
$ docker images
REPOSITORY              TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
<none>                  <none>              8a15692b92ea        About an hour ago   1.077 GB
<none>                  <none>              0bcfd9025fab        About an hour ago   1.077 GB
<none>                  <none>              4a0a98efbd7e        About an hour ago   802.8 MB
...
```

`docker images` コマンドの出力で、REPOSITORY 名が `<none>` になっているイメージをすべて削除するには、例えば以下のようにします。

```
$ sudo docker rmi $(docker images | awk '/^<none>/ {print $3}')
```

