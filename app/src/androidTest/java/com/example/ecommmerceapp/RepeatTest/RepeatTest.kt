package com.example.ecommmerceapp.RepeatTest

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
annotation class RepeatTest(val value: Int = 1)