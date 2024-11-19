package yourevent.app.dto.agencyDto

import yourevent.app.entity.agency.AddressEntity

data class AddressDto(
    var city: String,
    var street: String,
    var building: String
) {
    fun AddressDto.toEntity() = AddressEntity(
        city = this.city,
        street = this.street,
        building = this.building
    )

}