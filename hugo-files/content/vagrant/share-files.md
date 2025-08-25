---
title: "Vagrantメモ: ホストマシンと仮想マシンでファイルを共有する"
url: "p/ad8jegg/"
date: "2016-10-21"
tags: ["vagrant"]
aliases: ["/vagrant/share-files.html"]
---

Vagrant ホスト側の `Vagrantfile` の置かれているディレクトリは、仮想マシン側からは、`/vagrant` というパスで参照することができます。

```
vagrant@precise64:~$ ls /vagrant
Vagrantfile
```

この性質を利用して、仮想マシン側で使用するシェルスクリプトを、ホスト側のエディタで編集するといったことが可能になります。

