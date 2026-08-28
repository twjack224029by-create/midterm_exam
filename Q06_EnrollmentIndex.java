import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Q06_EnrollmentIndex {
    private final Map<String, Set<String>> courseMap;

    public Q06_EnrollmentIndex() {
        this.courseMap = new HashMap<>();
    }

    public boolean enroll(String courseCode, String studentId) {
        if (isInvalid(courseCode) || isInvalid(studentId)) {
            return false;
        }

        Set<String> students = courseMap.computeIfAbsent(courseCode, k -> new HashSet<>());
        return students.add(studentId); 
    }

    public boolean drop(String courseCode, String studentId) {
        if (isInvalid(courseCode) || isInvalid(studentId)) {
            return false;
        }

        Set<String> students = courseMap.get(courseCode);
        if (students == null) {
            return false;
        }

        boolean removed = students.remove(studentId);
        if (removed && students.isEmpty()) {
            courseMap.remove(courseCode); 
        }
        return removed;
    }

    public int courseSize(String courseCode) {
        if (isInvalid(courseCode)) {
            return 0;
        }
        Set<String> students = courseMap.get(courseCode);
        return students == null ? 0 : students.size();
    }

    public List<String> studentsOf(String courseCode) {
        if (isInvalid(courseCode)) {
            return Collections.emptyList();
        }
        Set<String> students = courseMap.get(courseCode);
        if (students == null || students.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> list = new ArrayList<>(students);
        Collections.sort(list); 
        return Collections.unmodifiableList(list); 
    }

    public List<String> coursesOf(String studentId) {
        if (isInvalid(studentId)) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : courseMap.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result); 
        return Collections.unmodifiableList(result); 
    }

    public Map<String, Integer> summary() {        Map<String, Integer> summaryMap = new TreeMap<>();
        for (Map.Entry<String, Set<String>> entry : courseMap.entrySet()) {
            summaryMap.put(entry.getKey(), entry.getValue().size());
        }
        return Collections.unmodifiableMap(summaryMap); 
    }

    private boolean isInvalid(String str) {
        return str == null || str.trim().isEmpty();
    }
}
