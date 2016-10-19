---
title: Vagrant とは？ Vagrant をインストールする
created: 2016-10-18
---

Vagrant とは？
----

Vagrant（ベイグラント）は、仮想環境の構築（OS 自体のインストールを含む）から設定までを自動で行うためのソフトウェアです。

* Vagrantfile という設定ファイルを記述しておくと、同じ OS 環境、同じソフトウェアのインストールから設定を１ステップ (`vagrant up`) で再現できるようになります。
* VirtualBox、VMware、Hyper-V などのローカルの仮想環境だけでなく、AWS (Amazon EC2) などのクラウド上の仮想環境もサポートします（Vagrant ではこれらを provider と呼びます）。共通の Vagrantfile を使用しつつ、立ち上げ先の環境だけを切り替えることができる (`vagrant up --provider=aws`) ので、VirtualBox から AWS へ乗り換えた場合なども、同じワークフローで環境構築できます（→ 運用エンジニア (Ops) のメリット）。
* 各開発者が同一のサーバ環境を構築できるようになり、開発環境の構築ミスによるエラーを防ぐことができます（→ 開発エンジニア (Dev) のメリット）。
* 環境構築の手順がコードによって管理されることにより、従来のソフトウェア開発においてソースコードに対して適用されていた、バージョン管理、レビュー、テストなどのプロセスを環境構築にも適用することができるようになります (→ IaaS: Infrastructure as a Code)。
* Vagrant 自体の開発言語は Ruby です。

Vagrant をインストールする
----

各 OS 用の Vagrant パッケージは下記からダウンロードできます。

- [Vagrant by HashiCorp](https://www.vagrantup.com/)

Vagrant 1.8.6 for Windows のパッケージサイズは 167 MB です。
正しくインストールできたかどうかは、下記のように確認できます。

```
D:\> vagrant --version
Vagrant 1.8.6
```

仮想環境として、VirtualBox を使用する場合は、VirtualBox も別途インストールしておく必要があります。
AWS などのクラウド環境を使用する場合は、VirtualBox のインストールは必要ありません。

- [Oracle VM VirtualBox](https://www.virtualbox.org/)

VirtualBox 5.1.8 for Window のパッケージサイズは 117 MB です。

