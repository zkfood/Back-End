package zkfood.pedidosapi.nucleo

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class InterceptadorRegistro:HandlerInterceptor {
    override fun preHandle(requisicao: HttpServletRequest, resposta: HttpServletResponse, manipulador: Any): Boolean {
        println("Endpoint chamado: ${requisicao.requestURI}, Método: ${requisicao.method}")
        return true
    }
    override fun postHandle(requisicao: HttpServletRequest, resposta: HttpServletResponse, manipulador: Any, modeloEVisualizacao: ModelAndView?) {
        println("Resposta da solicitação para ${requisicao.requestURI}: ${resposta.status}")
    }
}