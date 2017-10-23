// WITH_NEW_INFERENCE

@Target(AnnotationTarget.FUNCTION)
annotation class FunAnn

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class SourceAnn

@Target(AnnotationTarget.EXPRESSION)
annotation class ExprAnn

fun bar(arg: () -> Int) = arg()

inline fun fast(arg: () -> Int) = arg()

inline fun fast2(x: Int, arg: () -> Int) = x + arg()

@FunAnn fun gav() = 13

fun foo(<!UNUSED_PARAMETER!>arg<!>: Int) {
    bar(<!WRONG_ANNOTATION_TARGET!>@<!NI|DEBUG_INFO_MISSING_UNRESOLVED!>FunAnn<!><!> ::gav)
}