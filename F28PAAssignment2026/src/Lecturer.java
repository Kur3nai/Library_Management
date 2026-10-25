package F28PAAssignment2026.src;

import java.util.List;

public class Lecturer extends Member{

    public Lecturer(String memberID, String memberName, List<String> borrowedBook) {
        super(memberID, memberName, borrowedBook);
    }

    @Override
    public String getMemberType(){
        return "L";
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nMembership Tier: Lecturer" +
                "\n------------------------------------------------------";
    }
}
