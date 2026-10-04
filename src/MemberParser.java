package F28PAAssignment2026.src;
import java.util.ArrayList;
import java.util.List;

public class MemberParser {

    public static List<Member> parse(List<String> lines) {
        List<Member> members = new ArrayList<>();

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            String[] fields = line.split("\\s*,\\s*");

            String id = fields[0];
            String name = fields[1];
            String typeCode = fields[2];

            MemberType memberType = MemberType.fromCode(typeCode);

            List<String> books = new ArrayList<>();

            for (int i = 4; i < fields.length; i++) {
                books.add(fields[i]);
            }

            Member member = new Member(
                    id,
                    name,
                    memberType,
                    books
            );

            members.add(member);
        }

        return members;
    }
}

