package utils;

import domain.NotaDTO;

public class NotaAddEvent implements Event {
    private NotaDTO data;
    private NotaEventType type;
    public NotaAddEvent(NotaEventType type, NotaDTO data) {
        this.type = type;
        this.data = data;
    }

    public NotaEventType getType() {
        return type;
    }
    public NotaDTO getData() {
        return data;
    }
}
