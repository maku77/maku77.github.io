---
title: ValueAnimator でアニメーションに使用する値を計算する
date: "2014-08-13"
---

`ValueAnimator` を使用すると、ある数値からある数値まで、指定した時間で変化する値を取得することができます。
例えば、次の `animator` は、1000 ミリ秒の間に、0 から 360 まで変化する値を生成します。

~~~ java
ValueAnimator animator = ValueAnimator.ofFloat(0, 360).setDuration(1000);
~~~

`AnimatorUpdateListener` をセットし、`ValueAnimator#start()` を呼び出すと、値の変化を検出したときにコールバックされます。
その時点での変化中の値は、`getAnimatedValue()` で取得できます。

以下のサンプルは、ある `View` を 1 秒間かけて 360°回転させます。

~~~ java
// final View view = findViewById(...);
ValueAnimator animator = ValueAnimator.ofFloat(0, 360).setDuration(1000);

animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    @Override
    public void onAnimationUpdate(ValueAnimator anim) {
        view.setRotation((float) anim.getAnimatedValue());
    }
});

animator.start();
~~~

`ValueAnimator` では、数値の変化方法を柔軟に設定することができます。
例えば、以下の例では、数値が 0 → 100 → 0 → 200 のように変化します（4 段階ではなく、滑らかに変化します）。

~~~ java
ValueAnimator animator = ValueAnimator.ofFloat(0, 100, 0, 200);
animator.addUpdateListener(...);
animator.setDuration(1000).start();
~~~

また、`ValueAnimator.setInterpolator()` で、補間アルゴリズムを設定することで、数値がどのような割合で変化していくかを指定することができます。
デフォルトでは、`AccelerateDecelerateInterpolator` が設定されているため、最初に加速し、最後に減速するという値の変化をします。

~~~
animator.setInterpolator(...);
~~~

