package ru.barboskin.storeappreview.base.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

private val BACKEND_DATE_FORMAT = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss zzzz", Locale.US)

class DateJsonAdapter : JsonDeserializer<Date> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Date {
        return BACKEND_DATE_FORMAT.parse(json.asString)
    }
}
