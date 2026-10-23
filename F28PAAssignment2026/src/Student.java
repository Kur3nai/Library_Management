package F28PAAssignment2026.src;

import java.util.List;

public class Student extends Member{
    private int renewalCount;

    Student(String memberID, String memberName, List<String> borrowedBook) {
        super(memberID, memberName, borrowedBook);
    }
}
