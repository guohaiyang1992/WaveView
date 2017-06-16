

# WaveView
## android 中模仿水波效果的控件
---
功能：

 - 可设定颜色且自动设置透明度
 - 可设定动画是否自动执行
 - 可设定动画执行时间
 - 可设定波纹数量
 

---
使用方法:

 - 属性配置：

```
 <com.ghy.wave.ui.WaveView
        android:id="@+id/waveView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        app:wave_anim_auto_run="true"
        app:wave_color="@color/colorPrimary"
        app:wave_count="2"
        app:wave_duration="2000" />
```

 - 代码操作
 

```
startAnim() //开启动画
endAnim() //关闭动画
pauseAnim() //暂停动画
resumeAnim() //恢复动画
setWaveCount(int waveCount) //设置波纹数量
setColor(int color) //设置波纹颜色
setDuration(int duration) //设置动画时间（时间长速度慢，时间短速度快）
```

---

引入方法：

 - 在你的Project的 build.gradle 按下面的操作配置仓库。
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 - 然后在你对应的Modlule内的build.gradle内按下面的方式进行引入。

	

```
dependencies {
     compile 'com.github.guohaiyang1992:WaveView:0.2'
	}
```
