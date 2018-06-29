package io.patamon.erpc.client

import io.patamon.erpc.common.model.ErpcRequest
import io.patamon.erpc.common.model.ErpcResponse

/**
 * Desc: rpc client 端
 *
 * Mail: chk19940609@gmail.com
 * Created by IceMimosa
 * Date: 2018/6/29
 */
interface ErpcClient {

    /**
     * 执行 rpc 请求
     */
    fun call(request: ErpcRequest): ErpcResponse

}