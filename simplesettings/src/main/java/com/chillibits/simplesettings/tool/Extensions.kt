package com.chillibits.simplesettings.tool

fun String.toCamelCase(): String = split(" ")
    .joinToString("") { it.toLowerCase().capitalize() }.decapitalize()