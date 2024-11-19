package yourevent.app.entity.event

enum class Status(s: String) {
    PLANNED("Запланировано"),
    COMPLETED("Выполнено"),
    UNDER_CONSIDERATION("Рассматривется"),
    CLOSED("Закрыто"),
}