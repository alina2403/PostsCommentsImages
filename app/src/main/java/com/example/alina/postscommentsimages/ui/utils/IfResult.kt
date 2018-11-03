package util

interface IfResult<T> {
    fun onSuccess(result: T)
    fun onError(message: String?)
}