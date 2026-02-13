import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = (System.getenv("PORT") ?: "8080").toInt()) {
        routing {
            get("/health") { call.respondText("ok") }
            get("/api/hello") { call.respondText("hello") }
        }
    }.start(wait = true)
}