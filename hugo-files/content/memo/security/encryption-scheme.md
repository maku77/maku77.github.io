---
title: "共通鍵暗号化方式と公開鍵暗号化方式"
url: "p/xkoyxnh/"
date: "2010-11-28"
tags: ["memo", "network", "cryptography"]
aliases: ["/memo/security/encryption-scheme.html"]
---

共通鍵暗号化方式
----

### 用途

* 暗号化／複合化 -- 暗号化と複合化を同じ共通鍵（秘密鍵 Secret Key）で行う。

### 実装

* DES (Data Encryption Standard) -- 1960年代。IBM。FIPS 46。暗号強度が低すぎる。鍵が 56 ビットなので、ブルートフォースアタックですぐに解読されてしまう。
* 3-DES (Triple DES) -- 1999年。IBM。AES までの繋ぎ。
* AES (Advanced Encryption Standard) -- 2001年3月。米国商務省標準技術局(NIST)。FIPS 197 として公表。


公開鍵暗号化方式
----

### 用途

* 暗号化／複合化 -- 送信者が受信者の公開鍵 (Public Key) で暗号化し、受信者は自分の非公開鍵 (Private Key) で複合化する。
* 署名 -- 送信者が自分の非公開鍵で電子署名し、受信者が送信者の公開鍵で改変されていないことを確認する。公開鍵がそもそも偽造だと意味がないので、公開鍵を正しいものだと証明するための機関として、認証局 (CA) というものがある。

### 実装

* RSA（Ronald Rivest, Adi Shamir, Leonard Adleman）-- 1977年。
* DSA (Digital Signature Algorithm) -- 1991年8月。米国商務省標準技術局(NIST)。FIPS 186 として公表。1024 bits が一般的。
* ECDSA (Elliptic Curve Digital Signature Algorithm) -- 楕円曲線暗号版の DSA。RSA や DSA よりも短い鍵長で、強い強度を持つという特徴がある。
* Ed25519 (Edwards-curve Digital Signature Algorithm) -- エドワーズ曲線暗号。ECDSA よりも高速に処理できるという特徴がある。

「**認証局 (CA)**」は、鍵の所有者の情報と、その公開鍵を管理しており、公開鍵が偽造されていないことを「**証明書 (Certificate)**」を発行することで証明します。
「証明書」とは、ある公開鍵とその所有者情報を「**認証局の非公開鍵で署名**」したものです。
この署名により、認証局が発行している証明書がそもそも本物であるということを証明します。
ようするに、公開鍵暗号方式では、どこかの時点で公開鍵を無条件に信頼しなければいけなくて、その代表になるのが認証局ということです。
証明書の形式には **X.509** というものが使われます（バージョンとして X.509 V1 や V3 がよく使用される）。

HTTPS、SSH、SFTP などで使用する **SSL** は、公開鍵暗号方式でまず共通鍵（秘密鍵）を交換しておいて、その後の暗号化は共通鍵暗号方式で行われます。
これは、暗号化に共通鍵を使うと速度的に有利という特徴があるからです。

