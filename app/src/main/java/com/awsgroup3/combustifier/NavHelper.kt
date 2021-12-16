package com.awsgroup3.combustifier

abstract class Route {
    abstract val label: String
    abstract val arguments: List<String>?
    abstract val optionalArguments: List<String>?
    val route: String by lazy {
        StringBuilder(label).apply {
            arguments?.let {
                it.forEach {
                    append("/{$it}")
                }
            }
            optionalArguments?.let {
                it.forEach {
                    append("?$it={$it}")
                }
            }
        }.toString()
    }

    fun address(args: Map<String, String>? = null): String {
        var address: String = route

        args?.entries?.forEach {
            address = address.replace("{${it.key}}", it.value)
        }

        return address
    }
}