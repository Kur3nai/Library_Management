package F28PAAssignment2026.src;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private final String id;
    private final String name;
    private final MemberType memberType;
    private final List<String> books;

    public Member(String id, String name, MemberType memberType,
                  List<String> books) {
        this.id = id;
        this.name = name;
        this.memberType = memberType;
        this.books = books;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public List<String> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - " + memberType;
    }
}
