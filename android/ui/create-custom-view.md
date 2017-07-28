---
title: カスタムビューを作成する
created: 2014-08-11
---

自力で描画するカスタムビューを作る
----

`android.view.View` を継承することで、簡単にカスタムビューを作成することができます。

下記の例では `com.example.myapp.MyCustomView` というクラスを作成しており、`layout.xml` の中から以下のように配置できます。

#### activity_main.xml（抜粋）

~~~ xml
<com.example.myapp.MyCustomView
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" />
~~~

#### MyCustomView.java

~~~ java
package com.example.myapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyCustomView extends View {
    private final Paint mBackgroundPaint = new Paint() {
        {
            setColor(Color.BLUE);
            setAntiAlias(true);
        }
    };

    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int radius = w < h ? w/2 : h/2;
        canvas.drawCircle(w / 2, h / 2, radius, mBackgroundPaint);
    }
}
~~~

カスタムビューの実装では、少なくともビューの描画方法を実装するための `View#onDraw()` をオーバライドする必要があります。
上記では、親ビューのサイズいっぱいに広がる円を描画しています。


既存のビューを組み合わせてカスタムビューを作る
----

既存のボタンウィジェットなどを組み合わせて、ひとつのカスタムビューを作成することができます。
意味のある単位でカスタムビューの形でカプセル化しておくと、コードの見通しがよくなります。

ここでは、ボタンを横方向に２つ並べただけの、`MyButtonsView` クラスを作成します。
まずは、カスタムビュー用のレイアウトファイルを作成します（もちろん、XML ファイルを使わずに、Java コードの中で動的に配置することもできます）。

#### res/layout/my_buttons_view.xml

~~~ xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">

    <Button
        android:id="@+id/button1"
        android:text="1"
        android:layout_width="wrap_content"
        android:layout_height="fill_parent"/>
    <Button
        android:id="@+id/button2"
        android:text="2"
        android:layout_width="wrap_content"
        android:layout_height="fill_parent"/>

</LinearLayout>
~~~

次に、`MyButtonsView` クラスの実装を行います。
レイアウトとして `LinearLayout` を採用したので、`View` クラスではなく、`LinearLayout` を継承して作成します。

#### MyButtonsView.java

~~~ java
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyButtonsView extends LinearLayout {
    public MyButtonsView(Context context) {
        super(context);
        init(context);
    }

    public MyButtonsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyButtonsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 第 2 引数で this を指定することで、Layout XML を自分自身に inflate する
        View layout = LayoutInflater.from(context).inflate(R.layout.my_buttons_view, this);
        ((Button) layout.findViewById(R.id.button1)).setOnClickListener(mListener);
        ((Button) layout.findViewById(R.id.button2)).setOnClickListener(mListener);
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button) view;
            Toast.makeText(view.getContext(), button.getText(), Toast.LENGTH_SHORT).show();
        }
    };
}
~~~

このカスタムビューを使用するときは、他の `View` クラスと同様に以下のように使用できます。

~~~ xml
<com.example.myapp.MyButtonsView
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"/>
~~~

