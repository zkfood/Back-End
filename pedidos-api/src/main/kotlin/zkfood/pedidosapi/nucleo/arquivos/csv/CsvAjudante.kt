package zkfood.pedidosapi.nucleo.arquivos.csv

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.util.Formatter
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class CsvAjudante {
    fun <T : Any> escrever(lista: List<T>): ByteArray {
        if (lista.isEmpty()) {
            throw IllegalArgumentException("A lista está vazia e não há nada para gravar.");
        };

        val outputStream = ByteArrayOutputStream();
        val output = Formatter(outputStream);

        val fields = lista.first()::class.java.declaredFields.apply {
            forEach { it.isAccessible = true }
        }

        fields.joinToString(";") { it.name }.let { output.format("%s\n", it) }

        lista.forEach { item ->
            val values = fields.joinToString(";") { field ->
                field.get(item)?.toString() ?: ""
            }
            output.format("%s\n", values)
        }

        output.close();
        return outputStream.toByteArray();
    }

    fun <T : Any> lerCsv(inputStream: InputStream, entityType: KClass<T>): List<T> {
        val lines = inputStream.bufferedReader().use { it.readLines() }
        if (lines.isEmpty()) {
            throw IllegalArgumentException("O arquivo CSV está vazio.")
        }

        val headers = lines.first().split(";").map { it.trim() }

        val constructor = entityType.primaryConstructor
            ?: throw IllegalArgumentException("A classe ${entityType.simpleName} precisa ter um construtor primário.")

        val properties = entityType.memberProperties.associateBy { it.name }

        return lines.drop(1).map { line ->
            val values = line.split(";").map { it.trim() }

            val args = constructor.parameters.associateWith { param ->
                val headerIndex = headers.indexOf(param.name)
                if (headerIndex == -1) {
                    throw IllegalArgumentException("O campo ${param.name} não foi encontrado no cabeçalho do CSV.")
                }
                val rawValue = values[headerIndex]
                val propertyType = properties[param.name]?.returnType

                when {
                    propertyType?.classifier == Int::class -> rawValue.toIntOrNull()
                    propertyType?.classifier == Double::class -> rawValue.toDoubleOrNull()
                    propertyType?.classifier == Boolean::class -> rawValue.toBooleanStrictOrNull()
                    propertyType?.classifier == LocalDate::class -> LocalDate.parse(rawValue)
                    propertyType?.classifier == String::class -> rawValue
                    else -> throw IllegalArgumentException("Tipo não suportado para o campo ${param.name}.")
                }
            }

            constructor.callBy(args)
        }
    }
}