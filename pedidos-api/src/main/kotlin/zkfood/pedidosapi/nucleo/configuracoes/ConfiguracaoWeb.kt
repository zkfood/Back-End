package zkfood.pedidosapi.nucleo.configuracoes

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfiguracaoWeb:WebMvcConfigurer {
    override fun addInterceptors(registro: InterceptorRegistry) {
        registro.addInterceptor(InterceptadorRegistro())
    }
}