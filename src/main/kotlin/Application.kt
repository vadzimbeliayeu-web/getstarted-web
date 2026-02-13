import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TestRequest(val ts: Long? = null, val msg: String? = null)

@Serializable
data class TestResponse(val ok: Boolean, val echo: TestRequest)

fun main() {
    embeddedServer(Netty, port = (System.getenv("PORT") ?: "8080").toInt()) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        // Нужно для GitHub Pages -> Render (разные домены)
        install(CORS) {
            // Быстро/просто для теста: разрешаем всё.
            // Потом лучше ограничить конкретным доменом pages.
            anyHost()

            allowHeader(HttpHeaders.ContentType)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Options)
        }

        routing {
            get("/health") { call.respondText("ok") }

            post("/api/test") {
                val body = call.receive<TestRequest>()
                call.respond(TestResponse(ok = true, echo = body))
            }
        }
    }.start(wait = true)
}
