---
title: お試しインスタンスとして Jenkins サーバを起動する
date: "2014-07-08"
---

下記のように、`JENKINS_HOME` や HTTP ポート番号を指定して `jenkins.war` を起動することで、お試しの Jenkins サーバを起動することができます。
未知のプラグインのインストールを試してみるときや、設定を大きく変更してみたいときに便利です。

```
$ java -DJENKINS_HOME=/path/to/jenkins_home jenkins.war --httpPort=8081
```

