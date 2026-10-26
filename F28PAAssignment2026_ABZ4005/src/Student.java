package F28PAAssignment2026_ABZ4005.src;

import java.util.List;

public class Student extends Member{

    public Student(String memberID, String memberName, List<String> borrowedBook) {
        super(memberID, memberName, borrowedBook);
    }

    @Override
    public String getMemberType(){
        return "s";
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nMembership Tier: Student" +
                "\n------------------------------------------------------";
    }
}
