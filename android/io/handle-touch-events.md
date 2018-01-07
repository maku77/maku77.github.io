---
title: タッチイベントをハンドルする
date: "2014-07-29"
---

onTouchEvent/OnTouchListener で基本情報を取得する
----

`Activity#onTouchEvent(MotionEvent event)` をオーバライドすると、その Activity 上で発生したタッチイベントをハンドルすることができます。

~~~ java
public class MainActivity extends Activity {
    ...

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ...
        return super.onTouchEvent(event);
    }
}
~~~

あるいは、`View#setOnTouchListener()` を使うと、オーバライドすることなしに、タッチイベントをハンドルするリスナを設定することができます。

~~~ java
view.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ...
        return true;
    }
});
~~~

上記のようなメソッドで渡される `MotionEvent` オブジェクトを参照すると、どのような内容のイベントが発生したかを調べることができます。
`MotionEvent#getAction()` では、どのような操作を行ったかを取得できます。

* `MotionEvent.ACTION_DOWN` (0) -- タッチ開始（指で触れた）
* `MotionEvent.ACTION_UP` (1)   -- タッチ終了（指を離した）
* `MotionEvent.ACTION_MOVE` (2) -- ドラッグ中
* その他いろいろ: [MotionEvent - Android Developers](http://developer.android.com/reference/android/view/MotionEvent.html#ACTION_DOWN)

`MotionEvent#getX()` や `getY()` では、タッチしている座標値を取得できます。
下記の例では、Activity 上でのタッチをハンドルし、座標値などを表示しています。

#### MainActivity.java

~~~ java
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        StringBuilder msg = new StringBuilder();
        msg.append("action=").append(event.getAction() & MotionEvent.ACTION_MASK);
        msg.append(", x=").append(event.getX());
        msg.append(", y=").append(event.getY());
        Log.d(TAG, msg.toString());
        return super.onTouchEvent(event);
    }
}
~~~

#### 出力例（一本指で少しドラッグした場合）

~~~
07-29 17:33:03.100: D/MainActivity(19400): action=0, x=797.0, y=468.0
07-29 17:33:03.110: D/MainActivity(19400): action=2, x=800.0, y=470.0
07-29 17:33:03.120: D/MainActivity(19400): action=2, x=804.0, y=473.0
07-29 17:33:03.140: D/MainActivity(19400): action=2, x=810.0, y=479.0
07-29 17:33:03.160: D/MainActivity(19400): action=2, x=817.0, y=486.0
07-29 17:33:03.170: D/MainActivity(19400): action=2, x=824.0, y=494.0
07-29 17:33:03.190: D/MainActivity(19400): action=2, x=831.0, y=502.0
07-29 17:33:03.210: D/MainActivity(19400): action=2, x=839.0, y=509.0
07-29 17:33:03.220: D/MainActivity(19400): action=2, x=848.0, y=517.0
07-29 17:33:03.230: D/MainActivity(19400): action=1, x=848.0, y=517.0
~~~


### onTouchEvent() の戻り値に注意

`onTouchEvent()` の戻り値で `false` が返された場合は、`MotionEvent.ACTION_DOWN` しかイベントが発生しません。
Activity の場合は `super.onTouchEvent()` の結果を return するようにすれば大丈夫ですが、SurfaceView などのサブクラスでは、明示的に `return true;` としないと罠にハマります。

~~~ java
@Override
public boolean onTouchEvent(MotionEvent event) {
    ...
    return true;  // 明示的に true を返す
}
~~~


### 複数指でのタッチ情報を取得する

`MotionEvent` オブジェクトからは、複数指でのタッチ情報を別々に取得することもできます。

* `MotionEvent#getPointerCount()` -- 何本の指でタッチしているか
* `MotionEvent#getX(int index)` -- 指定したインデックスの指の X 座標を取得
* `MotionEvent#getY(int index)` -- 指定したインデックスの指の Y 座標を取得

インデックスは、最初にタッチした指から順番に 0, 1, 2, 3 と振られていきます。
下記のコードでは、２本指でピンチ Open/Close 処理しているときのそれぞれの指の座標値を表示しています。

~~~ java
@Override
public boolean onTouchEvent(MotionEvent e) {
    StringBuilder msg = new StringBuilder();
    if (e.getPointerCount() == 2 && e.getAction() == MotionEvent.ACTION_MOVE) {
        msg.append(" [").append(e.getX(0)).append(", ").append(e.getY(0)).append("]");
        msg.append(" [").append(e.getX(1)).append(", ").append(e.getY(1)).append("]");
    }
    Log.d(TAG, msg.toString());
    return super.onTouchEvent(e);
}
~~~


GestureDetector で詳しいタッチイベントを取得する
---

- 参考: [Detecting Common Gestures - Android Developers](http://developer.android.com/training/gestures/detector.html)

`android.view.GestureDetector` を使用すると、`Activity#onTouchEvent()` や `View.OnTouchListener#onTouch()` で渡される `MotionEvent` 情報を解析し、より詳細なタッチイベントの形式でコールバックを受けることができます。
使い方は以下のような感じで、任意の Activity/View で受けたタッチイベントをそのまま `GestureDetector` インスタンスにフォワードしてやるだけです。
そうすると、`GestureDetector` インスタンスにセットした `GestureDetector.OnGestureListener` の各種コールバックが適切なタイミングで呼び出されます。

#### MainActivity.java

~~~ java
public class MainActivity extends Activity implements GestureDetector.OnGestureListener {
    // private static final String TAG = MainActivity.class.getSimpleName();
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetector(this,
                new GestureDetector.OnGestureListener() {...});
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return true;
    }
}
~~~

`GestureDetector.OnGestureListener` インタフェースでは下記のようなイベントをハンドルします。

~~~
onDown() - Notified when a tap occurs with the down MotionEvent that triggered it.
onFling() - Notified of a fling event when it occurs with the initial on down MotionEvent and the matching up MotionEvent.
onLongPress() - Notified when a long press occurs with the initial on down MotionEvent that trigged it.
onScroll() - Notified when a scroll occurs with the initial on down MotionEvent and the current move MotionEvent.
onShowPress() - The user has performed a down MotionEvent and not performed a move or up yet.
onSingleTapUp() - Notified when a tap occurs with the up MotionEvent that triggered it.
~~~

さらに、`GestureDetector#setOnDoubleTapListener()` で、`OnDoubleTapListener` をセットすることで、ダブルタップを判別できるようになります。
`OnDoubleTapListener` インタフェースでは下記のイベントをハンドルします。

~~~
onDoubleTap() - Notified when a double-tap occurs.
onDoubleTapEvent() - Notified when an event within a double-tap gesture occurs, including the down, move, and up events.
onSingleTapConfirmed() - Notified when a single-tap occurs.
~~~


### 各種操作を行った場合のイベント

いろいろなタイプのタッチ操作を行った場合、下記のような順序でイベントが発生します。

テスト用プログラム → [GestureDetectorActivity.java](GestureDetectorActivity.java)

- シングルタップ
  - OnGestureListener#onDown
  - OnGestureListener#onSingleTapUp
  - OnDoubleTapListener#onSingleTapConfirmed
- ダブルタップ
  - OnGestureListener#onDown
  - OnGestureListener#onSingleTapUp
  - OnDoubleTapListener#onDoubleTap
  - OnDoubleTapListener#onDoubleTapEvent
  - OnGestureListener#onDown
  - OnDoubleTapListener#onDoubleTapEvent
- 長押し
  - OnGestureListener#onDown
  - OnGestureListener#onShowPress
  - OnGestureListener#onLongPress
- フリック（左上から右下へ）
  - OnGestureListener#onDown
  - OnGestureListener#onScroll: -21.0, -25.0
  - OnGestureListener#onScroll: -30.0, -24.0
  - OnGestureListener#onScroll: -50.0, -45.0
  - OnGestureListener#onScroll: -84.0, -67.0
  - OnGestureListener#onScroll: -84.0, -70.0
  - OnGestureListener#onFling: 6211.229, 5071.926


### 必要なタッチイベントのみハンドルする

通常は、`OnGestureListener` インタフェースや、`OnDoubleTapListener` インタフェースをすべて実装するより、`SimpleOnGestureListener` クラスを継承したクラスを作ったほうが楽です。
`SimpleOnGestureListener` クラスは、２つのインタフェースの空実装を提供するので、必要なメソッドだけをオーバライドして使用できます。
例えば、`onLongPress` のイベントだけ欲しい場合は、下記のように実装するだけで OK です。

~~~ java
public class MainActivity extends Activity {
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a gesture detector and set a listener.
        mGestureDetector = new GestureDetector(this, mGestureListener);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);
    }

  private GestureDetector.SimpleOnGestureListener mGestureListener =
  new GestureDetector.SimpleOnGestureListener() {
  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            // ...
  return true;
  }
  };

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return true;
    }
}
~~~


### View の setOnTouchListener() を利用する

Activity では `onTouchEvent()` をオーバライドすることによって、`GestureDetector` へ渡す `MotionEvent` を取得していましたが、View では `OnTouchEventListener` インスタンスをセットすることでも、`MotionEvent` を取得することができます。

~~~ java
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private GestureDetector mGestureDetector;
    ...

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private GestureDetector.SimpleOnGestureListener mGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float distX, float distY) {
            Log.d("LOG", distX + ", " + distY);
            return true;
        }
    };
    ...
}
~~~

