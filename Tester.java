package F28PAAssignment2026;
import F28PAAssignment2026.src.FileReader;
import F28PAAssignment2026.src.Member;
import F28PAAssignment2026.src.MemberParser;

import java.util.List;

public class Tester {
    public static void main(String[] args) {
        try {
            List<String> lines =
                    FileReader.readLines("resource/member.txt");

            List<Member> members =
                    MemberParser.parse(lines);

            for (Member member : members) {
                System.out.println("ID: " + member.getId());
                System.out.println("Name: " + member.getName());
                System.out.println("Type: " + member.getMemberType());
                System.out.println(
                        "Maximum renewals: " +
                                member.getMemberType().getMaximumRenewals()
                );
                System.out.println(
                        "Fine exempt: " +
                                member.getMemberType().isFineExempt()
                );
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
