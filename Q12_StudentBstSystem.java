import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q12_StudentBstSystem {

    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0 || name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("ID must be > 0 and Name must not be null or blank.");
            }
            this.id = id;
            this.name = name;
            this.score = clampScore(score);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = clampScore(score);
        }

        @Override
        public String toString() {
            return id + "|" + name + "|" + score;
        }

        private static int clampScore(int score) {
            if (score < 0) return 0;
            if (score > 100) return 100;
            return score;
        }
    }

    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private boolean removedFlag;

    public Q12_StudentBstSystem() {
        this.root = null;
    }

    public boolean add(Student student) {
        if (student == null) {
            return false;
        }

        if (root == null) {
            root = new Node(student);
            return true;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            if (student.getId() == current.student.getId()) {
                return false;
            }
            parent = current;
            if (student.getId() < current.student.getId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (student.getId() < parent.student.getId()) {
            parent.left = new Node(student);
        } else {
            parent.right = new Node(student);
        }
        return true;
    }

    public Student find(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.student.getId()) {
                return current.student;
            } else if (id < current.student.getId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student student = find(id);
        if (student == null) {
            return false;
        }
        student.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        removedFlag = false;
        root = removeRecursive(root, id);
        return removedFlag;
    }

    private Node removeRecursive(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id < current.student.getId()) {
            current.left = removeRecursive(current.left, id);
        } else if (id > current.student.getId()) {
            current.right = removeRecursive(current.right, id);
        } else {
            removedFlag = true;

            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            Node successor = findMin(current.right);
            current.student = successor.student;
            current.right = removeRecursive(current.right, successor.student.getId());
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        if (lowId > highId || root == null) {
            return Collections.emptyList();
        }
        List<Student> result = new ArrayList<>();
        rangeSearchHelper(root, lowId, highId, result);
        return result;
    }

    private void rangeSearchHelper(Node node, int lowId, int highId, List<Student> result) {
        if (node == null) {
            return;
        }

        if (node.student.getId() > lowId) {
            rangeSearchHelper(node.left, lowId, highId, result);
        }

        if (node.student.getId() >= lowId && node.student.getId() <= highId) {
            result.add(node.student);
        }

        if (node.student.getId() < highId) {
            rangeSearchHelper(node.right, lowId, highId, result);
        }
    }

    public List<Student> inorder() {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Student> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Student> result) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, result);
        result.add(node.student);
        inorderHelper(node.right, result);
    }
}
