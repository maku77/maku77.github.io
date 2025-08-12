---
title: "faceswap/ffmpeg で動画の顔を好きな顔に置き換える"
url: "p/vfsc5ra/"
date: "2018-04-28"
tags: ["memo"]
aliases: ["/memo/tool/faceswap.html"]
---

概要
----

[faceswap](https://github.com/deepfakes/faceswap) というツールを使用すると、機械学習の技術を使用して2人の画像の顔を入れ替えることができます。

このツール自体は静止画を対象としているので、動画の顔を入れ替えることはできないのですが、

1. [ffmpeg](https://www.ffmpeg.org/) を使って動画から静止画を生成
2. faceswap を使って静止画の顔を入れ替える
3. ffmpeg を使って静止画を結合して動画を生成

というステップを踏むことによって、結果的に動画内の顔を入れ替えるということができます。


faceswap 環境のインストール
----

### faceswap のダウンロード

まずは、下記サイトの Clone or download ボタンを押して、zip ファイルをダウンロードします（約100MB）。

- [https://github.com/deepfakes/faceswap](https://github.com/deepfakes/faceswap)

faceswap は Python 製のツールであり、tensorflow などパッケージをインストールする必要があります。
`Dockerfile` が用意されていますので、[Docker の実行環境](/docker/) がインストールされているのであれば、比較的簡単に faceswap の実行環境を整えることができます。
Python 製のツールなので、[Virtualenv](/p/yqjs3aw/) を使って仮想環境を整えることもできます。

以下、Docker による環境構築方法と、Virtualenv による環境構築方法をそれぞれ示します（本家の [INSTALL ドキュメントはこちら](https://github.com/deepfakes/faceswap/blob/master/INSTALL.md)の情報が元になっています）。

### Docker コンテナで faceswap 環境を作る場合

Docker がインストールされていない (`docker` コマンドが使えない) 場合は、まずは Docker をインストールします。

- [Install Docker](https://docs.docker.com/install/)

例えば Mac であれば、下記から `docker.dmg` をダウンロードして簡単にインストールできます。

- [Docker Community Edition for Mac](https://store.docker.com/editions/community/docker-ce-desktop-mac)

Docker 環境のインストールができたら、faceswap に付属している `Dockerfile` を使用して、Docker コンテナをビルドします（10分程度かかります）。
ここでは、CPU 版のコンテナを作成します。

```console
$ cd faceswap
$ docker build -t deepfakes -f Dockerfile.cpu .
```

コンテナの作成が終わったら、シェルを起動します。

```console
$ docker run --rm --name deepfakes -v $(pwd):/srv -it deepfakes bash
```

`faceswap.py` が実行できるようになっていれば成功です。

```
root@xxxxx:/srv# ./faceswap.py -h
```

### Virtualenv を使って faceswap 環境を作る場合

ここでは、Virtualenv による faceswap 実行環境を構築してみます。
MacBook Pro (A1398) で確認しています。
必ずしも Virtualenv を使用する必要はないのですが、Virtualenv を使って実行環境を構築すると、faceswap のためにインストールした Python パッケージ群をその実行環境内に閉じて管理することができます（ディレクトリ削除すれば綺麗な環境に戻る）。
Virtualenv の使い方に関しては、下記に詳しく記述されていますので、使用したことのない方は読んでみてください。
faceswap に限らず、Python 製のツールを使用するときに活用できると思います。

- [Python の実行環境を切り替えて使用する (virtualenv)](/p/yqjs3aw/)


#### 1) Python3 のインストール

faceswap は Python スクリプトで作成されているので、Python 3 の処理系をインストールしておく必要があります。
例えば、Mac では Homebrew を使用して下記のようにインストールできます。

```console
$ brew install python3
```

#### 2) Virtualenv で仮想環境を作成

Virtualenv で、faceswap 用の Python 実行環境を作成します。
ここでは、ベタに `ENV` という名前で作成しましょう。
間違いなく Python 3 の処理系が使われるように、`--python` オプションを指定しています。
指定しなくても大丈夫かもしれません。

```console
$ cd faceswap
$ virtualenv --python=python3 ENV
```

#### 3) 仮想環境のセットアップ

Virtualenv で作成した仮想環境内に入り、その中で必要なツール群をインストールしていきます。
NVIDIA の GPU が搭載されていれば、GPU 版の環境を使用することができます。
その場合は、CUDA（GPU 機能を使用するプラットフォーム）、cuDNN（CUDA 用の Deep Learning ライブラリ）をインストールしておく必要があります。

- [CUDA Download](https://developer.nvidia.com/cuda-downloads)
- [cuDNN Download](https://developer.nvidia.com/cudnn)

faceswap には、pip 用の requirements ファイルが用意されているので、これを使って必要な Python パッケージをまとめてインストールできます。
CPU 用、GPU 用という具合に別れていますので、環境に応じて使い分けてください。

```
$ source ENV/bin/activate
(ENV)$ pip install -r requirements-python36.txt           （GPUを使えない場合）
(ENV)$ pip install -r requirements-gpu-python36-cuda9.txt （GPUを使える場合）
```

Mac の場合、下記のような感じで `tensorflow` パッケージのインストールに失敗することがあります。

```
Could not find a version that satisfies the requirement tensorflow-gpu==1.5.0
```

このような場合は、TensorFlow 公式サイトの指示にしたがって、明示的に URL 指定します（参考: [Installing TensorFlow on macOS](https://www.tensorflow.org/install/install_mac)）。

```
$ sudo pip3 install --upgrade \
    https://storage.googleapis.com/tensorflow/mac/cpu/tensorflow-1.7.0-py3-none-any.whl
```

`pip` によるパッケージインストール時に、`cmake` が入ってなくて、

```
No such file or directory: 'cmake'
```

というビルドできない感じのエラーが出たら、

```console
$ brew install cmake
```

などで `cmake` をインストールしてから再チャレンジしてみてください。


faceswap で静止画の画像を入れ替える
----

`faceswap` を使用すると、2人の画像の顔を置換することができます（本家の [USAGE ドキュメントはこちら](https://github.com/deepfakes/faceswap/blob/master/USAGE.md)）。

### 写真から顔を抽出する (Extract)

ここでは、人物1の写真と人物2の写真をそれぞれ用意し、人物1の写真の中の顔を人物2の顔に入れ替えることを考えます。
作業用の `data` ディレクトリを作成し、次のような構成で2人の写真を置いてください（少なくとも 64 ファイル以上ないと、スクリプト内で assert が発生するみたいです）。

```
+-- faceswap/
     +-- data/
          +-- photos-person1/  （人物1の写真 - この顔を）
          +-- photos-person2/  （人物2の写真 - この顔に入れ替える）
```

次のように実行すると、`data/photos-person1` の中の写真から顔だけを抽出した画像ファイルが、`data/faces-person1` に保存されます。

```console
$ ./faceswap.py extract -i data/photos-person1 -o data/faces-person1
```

同様にして、人物2の写真 (`data/photos-person2`) からも顔画像を抽出すれば完了です。
抽出した顔画像のサイズは、256x256 で統一されています。

### 機械学習で顔1から顔2への変換モデルを作成する (Train)

2人分の顔画像が準備できたら、顔1 (`data/faces-person1`) を顔2 (`data/faces-person2`) に置き換えるためのモデルデータを作成します。

<!--
まず、下記の学習済みモデル (Trained model) のアーカイブをダウンロードして、モデル格納用のディレクトリに展開します。

- [https://anonfile.com/p7w3m0d5be/face-swap.zip](https://anonfile.com/p7w3m0d5be/face-swap.zip) （約300MB）

```
+-- faceswap/
     +-- data/
          +-- models-person1-person2/  （人物1の顔を人物2の顔に置換するモデル）
               +-- encoder.h5    （このファイルを配置）
               +-- decoder_A.h5  （このファイルを配置）
               +-- decoder_B.h5  （このファイルを配置）
```
下記の機械学習処理では、これらのモデルを強化していくため、ダウンロードした学習済みモデルのアーカイブは、バックアップしておくとよいでしょう。
-->

次のように実行すると学習が始まり、`data/models-person1-person2` 以下にモデルデータが作成されます。

```console
$ ./faceswap.py train -A data/faces-person1 -B data/faces-person2 -m data/models-person1-person2
```

この学習処理はものすごく時間がかかりますが、特に CPU で処理している場合は忍耐強く待つ必要があります。

顔画像のファイル数が少なすぎると、以下のようなアサーションエラーが発生します。

```
AssertionError: Number of images is lower than batch-size (Note that too few images may lead to bad training). # images: 13, batch-size: 64
```

メッセージの通り、デフォルトではバッチサイズが 64 となっており、顔画像が 64 ファイル以上ないと怒られます。
バッチサイズは、下記のように `-bs (--batch-size)` オプションで2のべき乗の値 (64,128,256) を指定できます。

```console
$ ./faceswap.py train -A data/faces-person1 -B data/faces-person2 -m data/models-person1-person2 -bs ＜2のべき乗＞
```

### 顔を入れ替える

作成したモデルデータ (`data/models-person1-person2`) を使って、人物1の写真 (`data/photos-person1`) の顔を入れ替えるには、下記のように実行します。

```console
$ ./faceswap.py convert -i data/photos-person1 -o data/photos-result -m data/models-person1-person2
```

`data/photos-result` ディレクトリに、結果の写真が格納されます。


ffmpeg で動画ファイルの顔を入れ替える
----

ffmpeg を使うと、動画ファイルを静止画ファイルに分割したり、逆に、静止画ファイルから動画ファイルを作成したりすることができます。
faceswap では、直接動画ファイル内の顔を置換することはできないので、まずは ffmpeg を使って、動画を静止画に変換しておく必要があります。

### ffmpeg のインストール

ffmpeg は下記サイトからダウンロードすることができます。

- [https://www.ffmpeg.org/](https://www.ffmpeg.org/)

それぞれのプラットフォーム用のパッケージをダウンロードして、`ffmpeg` ファイルを、パスの通ったディレクトリに置けば OK です。

### 動画を静止画ファイルに変換

ffmpeg を使って動画ファイルから静止画ファイルを生成するには以下のようにします。

{{< code lang="console" title="例: video.mp4 から静止画ファイルを生成する" >}}
$ mkdir data/photos-someone  # 出力先ディレクトリを作成しておく
$ ffmpeg -i data/video.mp4 -vf fps=25 data/photos-someone/%06d.png
{{< /code >}}

上記では `fps=25`（1 秒あたり 25 枚の静止画）で切り出していますが、学習用のファイルとしては、あまり似たような画像があってもしょうがないので、`fps=1` などに調整して作成するとよいでしょう。
ただし、最終的に顔の置換対象とする画像ファイル群は、ある程度のフレーム数で切り出しておいて、動画ファイルの形に結合する必要があります。

- 学習用の静止画: 1fps
- 置換対象の静止画: 25fps

という感じで作り分けるとよいかもしれません。

### 静止画ファイルから動画ファイルに変換

静止画ファイルができたら、faceswap を使用して顔の入れ替えなどの画像処理を行います。
画像処理が終わったら、下記のようにして動画ファイルの形に結合することができます。

{{< code lang="console" title="例: data/photos-result ディレクトリ内の静止画から動画ファイルを生成する" >}}
$ ffmpeg -r 25 -i data/photos-result/%06d.png -vcodec libx264 -pix_fmt yuv420p -r 60 result.mp4
{{< /code >}}

最初の `-r 25` は入力画像のフレームレート（何枚で1分になるか）を表しており、次の `-r 60` は出力される動画のフレームレートを表しています。

