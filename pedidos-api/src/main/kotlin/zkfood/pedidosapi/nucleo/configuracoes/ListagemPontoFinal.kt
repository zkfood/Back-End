package zkfood.pedidosapi.nucleo.configuracoes

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import zkfood.pedidosapi.nucleo.enums.CoresTerminalEnum

@Component
class EndpointListener(
    val handlerMapping: RequestMappingHandlerMapping
):ApplicationListener<ContextRefreshedEvent> {
    override fun onApplicationEvent(evento: ContextRefreshedEvent) {
        println("${CoresTerminalEnum.ROXO}Endpoints disponíveis na aplicação:${CoresTerminalEnum.PADRAO}")
        handlerMapping.handlerMethods.forEach { (detalhes, manipulador) ->
            val pontoFinal:String; // montar a string aq
            val metodo = manipulador.method;
            val classeMetodo = metodo.declaringClass.simpleName;
            val nomeMetodo = metodo.name;

            val requisicaoMetodo = detalhes.methodsCondition.methods
            val valoresPadrao = detalhes.patternValues
            println(valoresPadrao)
            val requisicaoMetodoColorida = when {
                requisicaoMetodo.contains(RequestMethod.GET) -> "${CoresTerminalEnum.AZUL}GET${CoresTerminalEnum.PADRAO}"
                requisicaoMetodo.contains(RequestMethod.POST) -> "${CoresTerminalEnum.VERDE}POST${CoresTerminalEnum.PADRAO}"
                requisicaoMetodo.contains(RequestMethod.PATCH) -> "${CoresTerminalEnum.AMARELO}PATCH${CoresTerminalEnum.PADRAO}"
                requisicaoMetodo.contains(RequestMethod.PUT) -> "${CoresTerminalEnum.LARANJA}PUT${CoresTerminalEnum.PADRAO}"
                requisicaoMetodo.contains(RequestMethod.DELETE) -> "${CoresTerminalEnum.VERMELHO}DELETE${CoresTerminalEnum.PADRAO}"
                else -> {
                    if ("/error" in valoresPadrao)"${CoresTerminalEnum.PRETO}ERRO${CoresTerminalEnum.PADRAO}"
                    "${CoresTerminalEnum.BRANCO}OUTRO${CoresTerminalEnum.PADRAO}"
                }
            }
            println("- $requisicaoMetodoColorida, $valoresPadrao: $classeMetodo.$nomeMetodo()")
        }
    }
}

// outra forma de fazer
//@Component
//class EndpointsListener : ApplicationListener<ContextRefreshedEvent> {
//    override fun onApplicationEvent(event: ContextRefreshedEvent) {
//        val applicationContext: ApplicationContext = event.applicationContext
//        applicationContext.getBean(RequestMappingHandlerMapping::class.java).getHandlerMethods()
//            .forEach { (info, handler) ->
//                val method = (handler as HandlerMethod).method
//                val className = method.declaringClass.simpleName
//                val methodName = method.name
//                println("- ${info}: $className.$methodName()")
//            }
//    }
//}