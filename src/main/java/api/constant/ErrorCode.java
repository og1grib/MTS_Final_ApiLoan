package api.constant;

public enum ErrorCode {
    TARIFF_NOT_FOUND("Тариф не найден"),
    ORDER_NOT_FOUND("Заявка не найдена"),
    ORDER_IMPOSSIBLE_TO_DELETE("Невозможно удалить заявку"),
    TRY_LATER("Прошло менее 2 минут"),
    LOAN_ALREADY_APPROVED("Заявка уже одобрена"),
    LOAN_CONSIDERATION("Заявка уже на рассмотрении"),
    INTERNAL_SERVER_ERROR("Внутренняя ошибка сервера");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getError() {
        return message;
    }
}
