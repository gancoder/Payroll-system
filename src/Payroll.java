// Payroll.java
import java.util.*;

public class Payroll {
    private Scanner scanner;

    public Payroll(Scanner scanner) {
        this.scanner = scanner;
    }

    public void processPayroll() {
        String employeeID;
        while (true) {
            System.out.print("Enter Employee ID (EMP-XXXX): ");
            employeeID = scanner.nextLine();
            if (employeeID.matches("^EMP-\\d{4}$")) break;
            System.out.println("Invalid ID format. Try again.");
        }

        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        double basicSalary = readPositiveDouble("Enter Basic Monthly Salary (₱): ");
        double overtimeHours = readPositiveDouble("Enter Overtime Hours Worked: ");

        double hourlyRate = basicSalary / 160;
        double overtimePay = SalaryCalculations.calculateOvertimePay(hourlyRate, overtimeHours);
        double grossPay = SalaryCalculations.calculateGrossPay(basicSalary, overtimePay);

        double sss = StatutoryDeductions.getSSSContribution(basicSalary);
        double philhealth = StatutoryDeductions.getPhilHealth(basicSalary);
        double pagibig = StatutoryDeductions.PAG_IBIG;
        double incomeTax = StatutoryDeductions.calculateIncomeTax(basicSalary);
        double totalDeductions = sss + philhealth + pagibig + incomeTax;
        double netPay = grossPay - totalDeductions;

        printPayslip(employeeID, fullName, department, basicSalary, hourlyRate, overtimeHours,
                overtimePay, grossPay, sss, philhealth, pagibig, incomeTax, totalDeductions, netPay);
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= 0) return value;
                System.out.println("Value must be non-negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a numeric value.");
            }
        }
    }

    private void printPayslip(String id, String name, String dept, double basic, double rate,
                              double otHours, double otPay, double gross, double sss, double ph, double pagibig,
                              double tax, double deductions, double net) {
        System.out.println("\n--- Payslip ---");
        System.out.printf("Employee: %s\nID: %s\nDepartment: %s\n\n", name, id, dept);
        System.out.printf("Basic Salary: ₱%.2f\nHourly Rate: ₱%.2f\n", basic, rate);
        System.out.printf("Overtime Hours: %.2f\nOvertime Pay: ₱%.2f\n", otHours, otPay);
        System.out.printf("Gross Pay: ₱%.2f\n\n", gross);
        System.out.println("Deductions:");
        System.out.printf("- SSS: ₱%.2f\n- PhilHealth: ₱%.2f\n- Pag-IBIG: ₱%.2f\n- Income Tax: ₱%.2f\n",
                sss, ph, pagibig, tax);
        System.out.printf("Total Deductions: ₱%.2f\n", deductions);
        System.out.printf("Net Pay: ₱%.2f\n", net);
    }
}
