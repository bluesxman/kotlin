// INTENTION_TEXT: Suppress: Add @SuppressLint("SdCardPath") annotation
// INSPECTION_CLASS: com.android.tools.idea.lintAndroidLintSdCardPathInspection

fun foo(path: String = "<caret>/sdcard") = path