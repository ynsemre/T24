# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yunusemre/Library/Android/sdk/tools/proguard/proguard-android.txt
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

-keep class com.nostra13.** { *; }
-keep class android.support.** { *; }
-keep class com.google.** { *; }
-keep class org.apache.** { *; }

-dontwarn com.squareup.okhttp.**
-dontwarn com.google.**
-dontwarn org.apache.**
-dontwarn android.support.**

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }