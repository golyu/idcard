# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\ANDROID_SDK\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
#忽略警告
-ignorewarning

#保护jni
-keepclasseswithmembernames class * {
    native <methods>;
}

#保护枚举
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#保护序列化
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep public class com.lv.tools.IDCard {
    *;
}
-keep public class com.gpio.ex.PowerOperate{
    public *;
}
-keep public class com.lv.tools.CardHelper{
    public *;
}


#保护ReadIdResult类和ReadInfoResult
-keep public class com.lv.tools.ReadIdResult{
    *;
}
-keep public class com.lv.tools.ReadInfoResult{
    *;
}

-keep public class android_serialport_api.SerialPort{
    *;
}
-keep public class android_serialport_api.SerialPortHelper{
    public *;
}
-keep public class android_serialport_api.SerialPortPath{
    *;
}
-keep public class com.synjones.bluetooth.DecodeWlt{
    *;
}

