package com.example.androidprojectdn2021.converter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Retrofit
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type


class StringConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody?, String?>? {
        return if (String::class.java == type) {
            Converter<ResponseBody?, String?> { value -> value.string() }
        } else null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<String?, RequestBody?>? {
        return if (String::class.java == type) {
            Converter<String?, RequestBody?> { value -> RequestBody.create(MEDIA_TYPE, value) }
        } else null
    }

    companion object {
        private val MEDIA_TYPE: MediaType? = "text/plain".toMediaTypeOrNull()
        fun create(): StringConverterFactory {
            return StringConverterFactory()
        }
    }
}