# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep Room entities and DAOs
-keep class com.example.arroomscanner.data.** { *; }

# Keep ARCore classes
-keep class com.google.ar.core.** { *; }

# Keep Firebase classes
-keep class com.google.firebase.** { *; }

# Keep ML Kit classes
-keep class com.google.mlkit.** { *; }
