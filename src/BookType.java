package F28PAAssignment2026.src;

public enum BookType {
    NORMAL("N"),
    REFERENCE("R");

    private final String code;

    BookType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static BookType fromCode(String code) {
        String cleanedCode = code.trim();
        for (BookType type : BookType.values()) {
            if (type.code.equals(cleanedCode)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid book type code: " + code);
    }
}
