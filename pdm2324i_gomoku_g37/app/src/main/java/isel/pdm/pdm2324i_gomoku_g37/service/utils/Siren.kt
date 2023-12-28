package isel.pdm.pdm2324i_gomoku_g37.service.utils

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.internal.http.HttpMethod
import java.net.URI

private const val APPLICATION_TYPE = "application"
private const val SIREN_SUBTYPE = "vnd.siren+json"

val SirenMediaType = "$APPLICATION_TYPE/$SIREN_SUBTYPE".toMediaType()

data class SirenModel<T>(
    @SerializedName("class")
    val clazz: List<String>,
    val properties: T,
    val links: List<LinkModel>,
    val entities: List<EntityModel<*>>,
    val actions: List<ActionModel>
) {
    companion object {
        inline fun <reified T> getType(): TypeToken<SirenModel<T>> =
            object : TypeToken<SirenModel<T>>() { }
    }
}

data class LinkModel(
    val rel: List<String>,
    val href: String,
)

data class EntityModel<T>(
    val properties: T,
    val links: List<LinkModel>,
    val rel: List<String>,
)

data class ActionModel(
    val name: String,
    val href: String,
    val method: String,
    val type: String,
    val fields: List<FieldModel>,
)

data class FieldModel(
    val name: String,
    val type: String,
    val value: String? = null,
)

class SirenBuilderScope<T>(val properties: T) {
    private val links = mutableListOf<LinkModel>()
    private val entities = mutableListOf<EntityModel<*>>()
    private val classes = mutableListOf<String>()
    private val actions = mutableListOf<ActionModel>()

    fun clazz(value: String) = classes.add(value)

    fun link(href: URI, rel: LinkRelation) =
        links.add(LinkModel(listOf(rel.value), href.toASCIIString()))

    fun <U> entity(
        value: U, rel: LinkRelation, block: EntityBuilderScope<U>.() -> Unit
    ) {
        val scope = EntityBuilderScope(value, listOf(rel.value))
        scope.block()
        entities.add(scope.build())
    }

    fun action(
        name: String, href: URI, method: HttpMethod,
        type: String, block: ActionBuilderScope.() -> Unit
    ) {
        val scope = ActionBuilderScope(name, href, method, type)
        scope.block()
        actions.add(scope.build())
    }

    fun build(): SirenModel<T> = SirenModel(
        clazz = classes,
        properties = properties,
        links = links,
        entities = entities,
        actions = actions
    )
}

class EntityBuilderScope<T>(
    val properties: T,
    private val rel: List<String>,
) {
    private val links = mutableListOf<LinkModel>()

    fun link(href: URI, rel: LinkRelation) =
        links.add(LinkModel(listOf(rel.value), href.toASCIIString()))

    fun build(): EntityModel<T> = EntityModel(
        properties = properties,
        links = links,
        rel = rel,
    )
}

class ActionBuilderScope(
    private val name: String,
    private val href: URI,
    private val method: HttpMethod,
    private val type: String,
) {
    private val fields = mutableListOf<FieldModel>()

    fun textField(name: String) = fields.add(FieldModel(name, "text"))

    fun numberField(name: String) = fields.add(FieldModel(name, "number"))

    fun hiddenField(name: String, value: String) =
        fields.add(FieldModel(name, "hidden", value))

    fun build() = ActionModel(name, href.toASCIIString(), method.toString(), type, fields)
}

fun <T> siren(value: T, block: SirenBuilderScope<T>.() -> Unit): SirenModel<T> {
    val scope = SirenBuilderScope(value)
    scope.block()
    return scope.build()
}