package designpattern.adapter.tree.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import designpattern.adapter.tree.SimpleTreeViewer;
import designpattern.adapter.tree.TreeContentProvider;
import designpattern.adapter.tree.VisualTreeVIewer;

class Employee {
    private String name;
    private String superviser;

    public Employee(String name, String superviser) {
        this.name = name;
        this.superviser = superviser;
    }

    public String getName() {
        return name;
    }

    public String getSuperviser() {
        return superviser;
    }
}

class OrgManager {
    public List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee("John", null),
                new Employee("Mary", "John"),
                new Employee("Jim", "John"),
                new Employee("Joe", "Mary"),
                new Employee("Jane", "Mary"));
    }

}

public class OrgTree {

    public static void main(String[] args) {

        OrgManager orgManager = new OrgManager();
        TreeContentProvider<Employee> contentProvider = new TreeContentProvider<Employee>() {

            @Override
            public List<Employee> getRoots() {
                return orgManager.getEmployees().stream().filter(c -> c.getSuperviser() == null)
                        .collect(Collectors.toList());
            }

            @Override
            public List<Employee> getChildren(Employee parent) {
                return orgManager.getEmployees().stream().filter(c -> parent.getName().equals(c.getSuperviser()))
                        .collect(Collectors.toList());

            }
        };

        new SimpleTreeViewer<Employee>(
                contentProvider,
                (node) -> node.getName()).show();
        new VisualTreeVIewer<Employee>(
                contentProvider,
                (node) -> node.getName()).show();

    }
}
