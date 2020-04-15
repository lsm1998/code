import java.util.*;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-01 15:05
 **/
public class GsonTest {

    public static void main(String[] args) {

        LinkedHashSet<Student> s=new LinkedHashSet<>();

        Set<Student> students=new HashSet<>();

        Student s1=new Student();
        s1.id=1;
        s1.age=1;
        students.add(s1);

        Student s2=new Student();
        s2.id=1;
        s2.age=1;
        students.add(s2);

        System.out.println(students);
    }

    static class Student{
        int id;
        int age;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return id == student.id &&
                    age == student.age;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, age);
        }
    }
}
