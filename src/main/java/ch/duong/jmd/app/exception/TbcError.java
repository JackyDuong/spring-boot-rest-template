package ch.duong.jmd.#APP_ABBREVIATION.exception;

public enum #UPPER_CASE_APP_ABBREVIATIONError {
    UNAUTHORIZED("Unauthorized"),
    ACCESS_DENIED("Access denied"),
    BAD_CREDENTIALS("Bad credentials"),
    METHOD_NOT_ALLOWED("Method not allowed"),
    BAD_REQUEST("There is something wrong in your request"),
    SDIS_VALIDATED("The SDIS is validated. You cannot modify its components."),
    SDIS_CANCELLED("The SDIS is cancelled. You cannot modify its components."),
    INTERVENTION_CANCELLED("The intervention is cancelled. You cannot modify its components."),
    MISMATCH_DATA("There is a mismatch data in your request."),
    REPORT_VALIDATED("The report is validated. You cannot modify its components."),
    REPORT_UNMODIFIABLE("The report is unmodifiable. You cannot modify its components."),
    EMPTY_DATA("Cannot work with empty data"),
    ENTITY_NOT_FOUND("The object that you tried to do with is not found"),
    NOT_FOUND("The object that you tried to do with is not found"),
    INTERNAL_ERROR("Internal server error"),
    EXPIRED_TOKEN("The token is expired"),
    USER_NOT_EXISTS("The user does not exist"),
    INSUFFICIENT_STORAGE("Report documents use to much disk space"),
    INTERVENTION_INTEGRITY("The intervention has missing attributes"),
    MULTIPLE_PRESENCES_FOUND("The staff is already present in anothers interventions"),
    TRANSFER_ALREADY_SUCCESS("The transfer is already in success"),
    INTERVENTION_ID_EXISTS("Intervention id already exists"),
    DOCUMENT_OVERSIZED("The document size is oversized");

    private String msg;

    #UPPER_CASE_APP_ABBREVIATIONError(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
