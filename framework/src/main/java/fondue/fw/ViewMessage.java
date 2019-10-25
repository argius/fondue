package fondue.fw;

public final class ViewMessage {

    private final MessageType type;
    private final String message;

    public ViewMessage(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public static ViewMessage ofError(String message) {
        return new ViewMessage(MessageType.ERROR, message);
    }

    public static ViewMessage ofWarning(String message) {
        return new ViewMessage(MessageType.WARNING, message);
    }

    public static ViewMessage ofSuccess(String message) {
        return new ViewMessage(MessageType.SUCCESS, message);
    }

    public static ViewMessage ofNotice(String message) {
        return new ViewMessage(MessageType.NOTICE, message);
    }

    public String getStyleClass() {
        return "message-" + type.toString().toLowerCase();
    }

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ViewMessage other = (ViewMessage) obj;
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViewMessage(type=" + type + ", message=" + message + ")";
    }
}
