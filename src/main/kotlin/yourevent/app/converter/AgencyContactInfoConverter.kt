package yourevent.app.converter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import yourevent.app.entity.agency.AgencyContactInfoEntity

@Converter(autoApply = true)
class AgencyContactInfoConverter : AttributeConverter<AgencyContactInfoEntity, String> {
    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: AgencyContactInfoEntity): String {
        return try {
            objectMapper.writeValueAsString(attribute)
        } catch (ex: Exception) {
            throw IllegalStateException("Error converting AgencyDetails to JSON string", ex)
        }
    }

    override fun convertToEntityAttribute(dbData: String): AgencyContactInfoEntity {
        return try {
            objectMapper.readValue(dbData, AgencyContactInfoEntity::class.java)
        } catch (ex: Exception) {
            throw IllegalStateException("Error converting JSON string to AgencyDetails", ex)
        }
    }
}

