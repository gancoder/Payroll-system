import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PayrollGUI extends JFrame {
    private JTextField idField, nameField, deptField, salaryField, overtimeField;
    private JLabel hourlyRateLabel, overtimePayLabel, grossPayLabel, sssLabel, philHealthLabel, pagIbigLabel, incomeTaxLabel, totalDedLabel, netPayLabel;
    private Employee currentEmployee;
    private PayrollSystem payrollSystem;
    private DefaultListModel<String> employeeListModel;
    private JList<String> employeeJList;

    public PayrollGUI() {
        setTitle("ABC SOLUTIONS PAYROLL SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        payrollSystem = new PayrollSystem();
        employeeListModel = new DefaultListModel<>();
        employeeJList = new JList<>(employeeListModel);
        JScrollPane listScrollPane = new JScrollPane(employeeJList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));

        JPanel inputPanel = new JPanel(new GridLayout(14, 2, 5, 5));
        inputPanel.add(new JLabel("Employee ID (EMP-XXXX):"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Department:"));
        deptField = new JTextField();
        inputPanel.add(deptField);
        inputPanel.add(new JLabel("Basic Monthly Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Overtime Hours Worked:"));
        overtimeField = new JTextField();
        inputPanel.add(overtimeField);
        inputPanel.add(new JLabel("Hourly Rate:"));
        hourlyRateLabel = new JLabel();
        inputPanel.add(hourlyRateLabel);
        inputPanel.add(new JLabel("Overtime Pay:"));
        overtimePayLabel = new JLabel();
        inputPanel.add(overtimePayLabel);
        inputPanel.add(new JLabel("Gross Pay:"));
        grossPayLabel = new JLabel();
        inputPanel.add(grossPayLabel);
        inputPanel.add(new JLabel("SSS Contribution:"));
        sssLabel = new JLabel();
        inputPanel.add(sssLabel);
        inputPanel.add(new JLabel("PhilHealth:"));
        philHealthLabel = new JLabel();
        inputPanel.add(philHealthLabel);
        inputPanel.add(new JLabel("PAG-IBIG:"));
        pagIbigLabel = new JLabel();
        inputPanel.add(pagIbigLabel);
        inputPanel.add(new JLabel("Income Tax:"));
        incomeTaxLabel = new JLabel();
        inputPanel.add(incomeTaxLabel);
        inputPanel.add(new JLabel("Total Deductions:"));
        totalDedLabel = new JLabel();
        inputPanel.add(totalDedLabel);
        inputPanel.add(new JLabel("Net Pay:"));
        netPayLabel = new JLabel();
        inputPanel.add(netPayLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JButton calcButton = new JButton("Calculate");
        JButton addButton = new JButton("Add Employee");
        JButton removeButton = new JButton("Remove Employee");
        JButton payslipButton = new JButton("Generate Payslip");
        JButton viewButton = new JButton("View Employees");
        buttonPanel.add(calcButton);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(payslipButton);
        buttonPanel.add(viewButton);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(inputPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.CENTER);
        add(listScrollPane, BorderLayout.EAST);

        calcButton.addActionListener((ActionEvent e) -> calculateAndDisplay());
        addButton.addActionListener((ActionEvent e) -> addEmployee());
        removeButton.addActionListener((ActionEvent e) -> removeEmployee());
        payslipButton.addActionListener((ActionEvent e) -> generatePayslip());
        viewButton.addActionListener((ActionEvent e) -> refreshEmployeeList());
        employeeJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = employeeJList.getSelectedIndex();
                if (idx >= 0 && idx < payrollSystem.getEmployees().size()) {
                    displayEmployee(payrollSystem.getEmployees().get(idx));
                }
            }
        });
    }

    private void calculateAndDisplay() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            double overtime = Double.parseDouble(overtimeField.getText().trim());
            currentEmployee = new Employee(id, name, dept, salary, overtime);
            displayEmployee(currentEmployee);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for salary and overtime hours.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayEmployee(Employee emp) {
        idField.setText(emp.getId());
        nameField.setText(emp.getName());
        deptField.setText(emp.getDepartment());
        salaryField.setText(String.valueOf(emp.getBasicSalary()));
        overtimeField.setText(String.valueOf(emp.getOvertimeHours()));
        hourlyRateLabel.setText(String.format("%.2f", emp.getHourlyRate()));
        overtimePayLabel.setText(String.format("%.2f", emp.getOvertimePay()));
        grossPayLabel.setText(String.format("%.2f", emp.getGrossPay()));
        sssLabel.setText(String.format("%.2f", emp.getSSS()));
        philHealthLabel.setText(String.format("%.2f", emp.getPhilHealth()));
        pagIbigLabel.setText(String.format("%.2f", emp.getPagIbig()));
        incomeTaxLabel.setText(String.format("%.2f", emp.getIncomeTax()));
        totalDedLabel.setText(String.format("%.2f", emp.getTotalDeductions()));
        netPayLabel.setText(String.format("%.2f", emp.getNetPay()));
        currentEmployee = emp;
    }

    private void addEmployee() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            double overtime = Double.parseDouble(overtimeField.getText().trim());
            Employee emp = new Employee(id, name, dept, salary, overtime);
            payrollSystem.addEmployee(emp);
            refreshEmployeeList();
            JOptionPane.showMessageDialog(this, "Employee added.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for salary and overtime hours.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeEmployee() {
        int idx = employeeJList.getSelectedIndex();
        if (idx >= 0 && idx < payrollSystem.getEmployees().size()) {
            Employee emp = payrollSystem.getEmployees().get(idx);
            payrollSystem.removeEmployee(emp);
            refreshEmployeeList();
            JOptionPane.showMessageDialog(this, "Employee removed.", "Removed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshEmployeeList() {
        employeeListModel.clear();
        List<Employee> emps = payrollSystem.getEmployees();
        for (Employee emp : emps) {
            employeeListModel.addElement(emp.getId() + " - " + emp.getName());
        }
    }

    private void generatePayslip() {
        int idx = employeeJList.getSelectedIndex();
        Employee emp = null;
        if (idx >= 0 && idx < payrollSystem.getEmployees().size()) {
            emp = payrollSystem.getEmployees().get(idx);
        } else if (currentEmployee != null) {
            emp = currentEmployee;
        }
        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Please select or calculate an employee first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Generate payslip for this employee?", "Generate Payslip", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        String filename = emp.getName() + " payslip.txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("--- Payslip ---\n");
            writer.write("Employee ID: " + emp.getId() + "\n");
            writer.write("Name: " + emp.getName() + "\n");
            writer.write("Department: " + emp.getDepartment() + "\n");
            writer.write(String.format("Basic Salary: %.2f\n", emp.getBasicSalary()));
            writer.write(String.format("Hourly Rate: %.2f\n", emp.getHourlyRate()));
            writer.write(String.format("Overtime Hours: %.2f\n", emp.getOvertimeHours()));
            writer.write(String.format("Overtime Pay: %.2f\n", emp.getOvertimePay()));
            writer.write(String.format("Gross Pay: %.2f\n", emp.getGrossPay()));
            writer.write(String.format("SSS: %.2f\n", emp.getSSS()));
            writer.write(String.format("PhilHealth: %.2f\n", emp.getPhilHealth()));
            writer.write(String.format("PAG-IBIG: %.2f\n", emp.getPagIbig()));
            writer.write(String.format("Income Tax: %.2f\n", emp.getIncomeTax()));
            writer.write(String.format("Total Deductions: %.2f\n", emp.getTotalDeductions()));
            writer.write(String.format("Net Pay: %.2f\n", emp.getNetPay()));
            writer.write("----------------\n");
            JOptionPane.showMessageDialog(this, "Payslip generated: " + filename, "Payslip", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing payslip file.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PayrollGUI().setVisible(true);
        });
    }
} 