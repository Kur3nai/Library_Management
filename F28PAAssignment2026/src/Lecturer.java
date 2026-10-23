package F28PAAssignment2026.src;

import java.util.List;

public class Lecturer extends Member{
    private int renewalCount;

    Lecturer(String memberID, String memberName, List<String> borrowedBook) {
        super(memberID, memberName, borrowedBook);
    }
}
