package io.patamon.erpc.common.model

import java.io.Serializable

/**
 * Desc: rpc 响应类封装
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/28
 */
data class ErpcResponse(
        var result: Any?,        // 返回的结果
        var error: Throwable?,   // 异常
        var requestId: Long = 1  // 请求追踪ID
) : Serializable {

    constructor(requestId: Long = 1): this(null, null, requestId)

}