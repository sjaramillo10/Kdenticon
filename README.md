# Kdenticon
An extension of Jdenticon for Android.

### Demo

![Kdenticon](https://i.postimg.cc/GpBXv37H/image.png)

### Add the library

First, make sure you have the following in your project level `build.gradle`:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the library to the project:

```groovy
dependencies {
   implementation 'com.github.sjaramillo10:Kdenticon:0.1.0'
}
```

### Usage

`KdenticonView`, which is the main component of the library, extends from `ImageView` so you can use many of its attributes too.

Use it in your layouts:

```xml
<dev.sjaramillo.kdenticon.KdenticonView
    android:id="@+id/kdenticonView"
    android:layout_width="180dp"
    android:layout_height="180dp"
    app:text="text"
    app:hash_algo="sha256" />
```

You can change also modify the `text` and `hashAlgo` parameters to obtain the desired Kdenticon like so:

`kdenticonView.text = "text"`
`kdenticonView.hashAlgo = HashAlgos.SHA256.ordinal`
