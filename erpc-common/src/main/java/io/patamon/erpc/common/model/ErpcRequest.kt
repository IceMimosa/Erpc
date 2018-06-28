package io.patamon.erpc.common.model

import java.io.Serializable
import java.util.*

/**
 * Desc: rpc 请求类封装
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
data class ErpcRequest(
        val className: String,              // 请求服务类的全名
        val methodName: String,             // 请求执行的方法名
        val parameterTypes: Array<Class<*>>,// 请求参数类型
        val params: Array<*>,               // 请求参数
        val requestId: Long = 1             // 请求追踪ID
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErpcRequest

        if (className != other.className) return false
        if (methodName != other.methodName) return false
        if (!Arrays.equals(parameterTypes, other.parameterTypes)) return false
        if (!Arrays.equals(params, other.params)) return false
        if (requestId != other.requestId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = className.hashCode()
        result = 31 * result + methodName.hashCode()
        result = 31 * result + Arrays.hashCode(parameterTypes)
        result = 31 * result + Arrays.hashCode(params)
        result = 31 * result + requestId.hashCode()
        return result
    }
}