package sample;

public class Employee {
    String id,name,position,salary,address;

    public Employee(String id, String name, String position, String salary, String address) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getSalary() {
        return salary;
    }

    public String getAddress() {
        return address;
    }
}
