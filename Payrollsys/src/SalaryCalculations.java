
public class SalaryCalculations {
    public static double calculateOvertimePay(double hourlyRate, double overtimeHours) {
        return hourlyRate * 1.25 * overtimeHours;
    }

    public static double calculateGrossPay(double basicSalary, double overtimePay) {
        return basicSalary + overtimePay;
    }
}
