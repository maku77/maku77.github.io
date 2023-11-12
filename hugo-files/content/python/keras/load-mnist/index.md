---
title: "MNIST の手書き数字データをダウンロードして表示する (keras.datasets.mnists)"
url: "p/849syej/"
date: "2023-11-12"
tags: ["Python", "Keras", "MNIST"]
---

MNIST とは
----

__MNIST__ (Modified National Institute of Standards and Technology) は、__手書き数字認識のためのデータセット__ で、0 から 9 までの単一の数字が手書きされた 28x28 ピクセルのグレースケール画像が含まれています。
各画像は対応する数字のラベルを持っており、機械学習や深層学習のアルゴリズムのトレーニングやテストに使用されます。


MNIST データの取得
----

MNIST のデータは、Python の `keras.datasets` パッケージを使って簡単に取得することができます。

{{< code lang="python" title="MNIST のデータを取得" hl_lines="5" >}}
import math
from keras.datasets import mnist

# MNIST の手書き数字データをロード
(X_train, y_train), (X_test, y_test) = mnist.load_data()

# データ形状を確認
print("X_train: {}".format(X_train.shape))
print("y_train: {}".format(y_train.shape))
print("X_test: {}".format(X_test.shape))
print("y_test: {}".format(y_test.shape))
{{< /code >}}

{{< code title="実行結果" >}}
X_train: (60000, 28, 28)
y_train: (60000,)
X_test: (10000, 28, 28)
y_test: (10000,)
{{< /code >}}

__`mnist.load_data()`__ が返す MNIST データは、学習用のデータとテスト用のデータに分かれており、それぞれ下記のような内容の NumPy 配列になっています。

`X_train`
: 学習用の画像（データ数: 60,000、サイズ: 28x28）。各要素には 0〜255 の輝度値が含まれており、`X_train[画像Index][行Index][列Index]` で参照できます。

`y_train`
: 学習用のラベル（データ数: 60,000）。各要素には 0〜9 の正解ラベルが含まれており、`y_train[画像Index]` で参照できます。

`X_test`
: テスト用の画像（データ数: 10,000、サイズ: 28x28）。各要素には 0〜255 の輝度値が含まれており、`X_test[画像Index][行Index][列Index]` で参照できます。

`y_test`
: テスト用のラベル（データ数: 10,000）。各要素には 0〜9 の正解ラベルが含まれており、`y_test[画像Index]` で参照できます。

ちなみに、`X_train` や `X_test` の `X` が大文字になっているのは、多次元ベクトルであることを示すための慣例です。
逆に正解ラベルである `y_train` や `y_test` は 1 次元データなので `y` を小文字にしています。


MNIST の手書き画像を表示する
----

`matplotlib` などを使って、MNIST の画像データを表示することができます。
次の例では、学習用データの先頭の 15 個を表示しています。

{{< image border="true" src="img-001.png" title="MNIST に含まれる手書き数字" >}}

{{< code lang="python" title="MNIST の手書き数字を表示する" >}}
import math

from keras.datasets import mnist
from matplotlib import pyplot as plt

# MNIST の手書き数字データをロード
(X_train, y_train), (X_test, y_test) = mnist.load_data()

# 画像をまとめて表示
def show_images(images, ncols=5):
    nrows = math.ceil(len(images) / ncols)
    _, ax = plt.subplots(nrows, ncols, tight_layout=True, figsize=(ncols*1.5, nrows*1.5))
    for i, img in enumerate(images):
        x = i % ncols
        y = i // ncols
        axis = ax[x] if nrows == 1 else ax[y][x]
        axis.set_title(i)
        axis.imshow(img, cmap="gray")
    plt.show()

# 先頭の 15 個の画像を表示
show_images(X_train[:15])
{{< /code >}}

サンプルコード内の `show_images` 関数は、任意の数の画像を表示できるようにしてあります。


（おまけ）CNN による学習
----

Keras で CNN モデルを構築して手書き数字画像を学習する実装例です。

```python
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Conv2D, Dense, Dropout, Flatten, MaxPooling2D
from keras.optimizers import RMSprop
from keras.utils import to_categorical

# MNIST の手書き数字データをロード
(X_train, y_train), (X_test, y_test) = mnist.load_data()

# サイズ情報
WIDTH, HEIGHT = X_train[0].shape # 画像の横幅と高さを取得
CHANNELS = 1  # 画像のチャネル数はグレースケールなので 1
INPUT_SHAPE = (HEIGHT, WIDTH, CHANNELS)
OUTPUT_SIZE = 10

# CNN への入力用に三次元データに変換し、画素値を 0〜1 に正規化
X_train = X_train.reshape(-1, HEIGHT, WIDTH, CHANNELS)  #=> (60000, 28, 28, 1)
X_train = X_train.astype("float32") / 255
X_test = X_test.reshape(-1, HEIGHT, WIDTH, CHANNELS) #=> (10000, 28, 28, 1)
X_test = X_test.astype("float32") / 255

# 正解ラベルを one-hot ベクトル化
y_train = to_categorical(y_train.astype("int32"), OUTPUT_SIZE)  #=> (60000, 10)
y_test = to_categorical(y_test.astype("int32"), OUTPUT_SIZE)  #=> (10000, 10)

# CNN モデルを作成
model = Sequential()
model.add(Conv2D(32, kernel_size=(3, 3), activation="relu", input_shape=INPUT_SHAPE))
model.add(Conv2D(64, kernel_size=(3, 3), activation="relu"))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Dropout(0.25))
model.add(Flatten())
model.add(Dense(128, activation="relu"))
model.add(Dropout(0.5))
model.add(Dense(OUTPUT_SIZE, activation="softmax"))

# モデルのコンパイル
model.compile(loss="categorical_crossentropy", optimizer=RMSprop(), metrics=["accuracy"])

# 学習
hist = model.fit(X_train, y_train, batch_size=128, epochs=6, verbose=1, validation_data=(X_test, y_test))

# 評価
score = model.evaluate(X_test, y_test, verbose=1)
print("accuracy={}, loss={}".format(score[1], score[0]))
# epochs=1 で accuracy=0.983 くらい
# epochs=3 で accuracy=0.988 くらい
# epochs=6 で accuracy=0.991 くらい
```

