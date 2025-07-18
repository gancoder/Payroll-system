public class Employee {
    private String id;
    private String name;
    private String department;
    private double basicSalary;
    private double overtimeHours;

    public Employee(String id, String name, String department, double basicSalary, double overtimeHours) {
        if (!id.matches("^EMP-\\d{4}$")) {
            throw new IllegalArgumentException("Employee ID must be in the format EMP-XXXX");
        }
        if (basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary cannot be negative");
        }
        if (overtimeHours < 0) {
            throw new IllegalArgumentException("Overtime hours cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.department = department;
        this.basicSalary = basicSalary;
        this.overtimeHours = overtimeHours;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getBasicSalary() { return basicSalary; }
    public double getOvertimeHours() { return overtimeHours; }

    public double getHourlyRate() {
        return basicSalary / 160.0;
    }

    public double getOvertimePay() {
        return getHourlyRate() * 1.25 * overtimeHours;
    }

    public double getGrossPay() {
        return basicSalary + getOvertimePay();
    }

    public double getSSS() {
        return StatutoryDeductions.getSSSContribution(getBasicSalary());
    }

    public double getPhilHealth() {
        return StatutoryDeductions.getPhilHealth(getGrossPay());
    }

    public double getPagIbig() {
        return StatutoryDeductions.PAG_IBIG;
    }

    public double getIncomeTax() {
        return StatutoryDeductions.calculateIncomeTax(getGrossPay());
    }

    public double getTotalDeductions() {
        return getSSS() + getPhilHealth() + getPagIbig() + getIncomeTax();
    }

    public double getNetPay() {
        return getGrossPay() - getTotalDeductions();
    }
} 