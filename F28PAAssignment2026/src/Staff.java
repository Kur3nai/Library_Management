package F28PAAssignment2026.src;

import java.util.List;

public class Staff extends Member{
    public Staff(String memberID, String memberName, List<String> borrowedBook) {
        super(memberID, memberName, borrowedBook);
    }

    @Override
    public String getMemberType(){
        return "S";
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nMembership Tier: Staff" +
                "\n------------------------------------------------------";
    }
}
