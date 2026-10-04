package F28PAAssignment2026.src;

public enum MemberType {
    STUDENT("s", 1, false, false),
    LECTURER("L", Integer.MAX_VALUE, true, true),
    STAFF("S", 2, false, false);

    private final String code;
    private final int maximumRenewals;
    private final boolean fineExempt;
    private final boolean automaticRenewal;

    MemberType(String code, int maximumRenewals,
               boolean fineExempt, boolean automaticRenewal) {
        this.code = code;
        this.maximumRenewals = maximumRenewals;
        this.fineExempt = fineExempt;
        this.automaticRenewal = automaticRenewal;
    }

    public String getCode() {
        return code;
    }

    public int getMaximumRenewals() {
        return maximumRenewals;
    }

    public boolean isFineExempt() {
        return fineExempt;
    }

    public boolean hasAutomaticRenewal() {
        return automaticRenewal;
    }

    public boolean canRenewUnlimited() {
        return maximumRenewals == Integer.MAX_VALUE;
    }

    public static MemberType fromCode(String code) {
        String cleanedCode = code.trim();

        for (MemberType type : MemberType.values()) {
            if (type.code.equals(cleanedCode)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Invalid member type code: " + code
        );
    }
}
