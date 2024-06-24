---
title: "HTML の video 要素で Web カメラ（USB カメラ）の映像を表示する"
url: "p/vap3zpa/"
date: "2024-06-24"
tags: ["HTML"]
---

Web カメラの表示テスト
----

Web カメラ（USB カメラ）からの入力映像を Web ページ上で表示するテストです。

<style>
#webcamButton {
  padding: 0.5em 1em;
  font-size: large;
}
</style>
<div style="text-align: center;">
  <button id="webcamButton">Enable webcam</button>
</div>
<div style="text-align: center;">
  <video id="webcam" playsinline style="max-width: 100%; border: solid 1px #aaa;"></video>
</div>
<script>
document.addEventListener("DOMContentLoaded", () => {
  const video = document.getElementById("webcam");
  const button = document.getElementById("webcamButton");
  let isWebcamRunning = false;
  button.addEventListener("click", () => {
    if (isWebcamRunning) {
      disableWebcam();
    } else {
      enableWebcam();
    }
    isWebcamRunning = !isWebcamRunning;
  });
  function enableWebcam() {
    button.disabled = true;
    // Web カメラのストリームを取得して video 要素に紐付ける
    navigator.mediaDevices.getUserMedia({ video: true })
      .then((mediaStream) => {
        video.srcObject = mediaStream;
        video.play();
        button.disabled = false;
        button.innerText = "Disable webcam";
      })
      .catch((err) => {
        console.error("Web カメラの取得に失敗しました:", err);
      });
  }
  function disableWebcam() {
    if (video.srcObject) {
        video.srcObject.getTracks().forEach(track => track.stop());
        button.innerText = "Enable webcam";
        video.srcObject = null;
    }
  }
});
</script>


Web カメラからの入力映像を取得する
----

ブラウザ上の JavaScript で Web カメラ（USB カメラ）からの入力映像を取得するには、__`MediaDevices`__ オブジェクトの __`getUserMedia()`__ メソッドを使って、__`MediaStream`__ を取得します。
__`navigator.mediaDevices`__ で `MediaDevices` のシングルトンオブジェクトを参照できるので、通常はこれを使用します。
取得した `MediaStream` を `video` 要素の __`srcObject`__ プロパティにセットすることで、Web カメラの映像を `video` 要素に流し込むことができます。

{{< code lang="html" title="HTML" >}}
<video id="webcam" playsinline></video>
{{< /code >}}

{{< code lang="js" title="JavaScript" >}}
const video = document.getElementById("webcam");

// Web カメラのストリームを取得して video 要素に紐付ける
navigator.mediaDevices.getUserMedia({ video: true })
  .then((mediaStream) => {
    video.srcObject = mediaStream;
    video.play();
  })
  .catch((err) => {
    console.error("Web カメラの取得に失敗しました:", err);
  });
{{< /code >}}


ボタンで Web カメラを On/Off する
----

Web カメラからの `MediaStream` を取得しようとすると、Web ブラウザ上に「カメラの使用」を許可するかどうかの確認ダイアログが表示されます。
Web サイトを開いたときに急にこのダイアログが出てくるとびっくりしてしまうので、ユーザーが「開始」ボタンをなど押したときにキャプチャを開始するのがよいでしょう。

次の例では、`Enable webcam` というボタンを配置しています。
ボタンを押すと、`Disable webcam` に変化します。

{{< code lang="html" title="HTML" >}}
<button id="webcamButton">Enable webcam</button>
<video id="webcam" playsinline></video>
{{< /code >}}

{{< code lang="js" title="JavaScript" >}}
const video = document.getElementById("webcam");
const button = document.getElementById("webcamButton");
let isWebcamRunning = false;

// Webcam の開始／停止用ボタンを押したとき
button.addEventListener("click", () => {
  if (isWebcamRunning) {
    disableWebcam();
  } else {
    enableWebcam();
  }
  isWebcamRunning = !isWebcamRunning;
});

// Webcam を有効化
function enableWebcam() {
  button.disabled = true;  // ボタン連打防止
  navigator.mediaDevices.getUserMedia({ video: true })
    .then((mediaStream) => {
      video.srcObject = mediaStream;
      video.play();
      button.disabled = false;
      button.innerText = "Disable webcam";
    })
    .catch((err) => {
      console.error("Web カメラの取得に失敗しました:", err);
    });
}

// Webcam を無効化
function disableWebcam() {
  if (video.srcObject) {
      video.srcObject.getTracks().forEach(track => track.stop());
      button.innerText = "Enable webcam";
      video.srcObject = null;
  }
}
{{< /code >}}

