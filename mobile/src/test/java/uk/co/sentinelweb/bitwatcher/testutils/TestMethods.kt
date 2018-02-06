package uk.co.sentinelweb.bitwatcher.testutils

import org.mockito.Mockito

internal fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}
private fun <T> uninitialized(): T = null as T