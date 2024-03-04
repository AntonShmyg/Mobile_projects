package ru.mirea.shmyglev.a.d.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();

        Employee employee = new Employee();
        employee.id = 1;
        employee.name = "Zigota";
        employee.strength = 3;
        employeeDao.insert(employee);

        List<Employee> employees = employeeDao.getAll();
        employee = employeeDao.getById(1);
        employee.strength = 2;
        employeeDao.update(employee);
        Log.d(TAG, employee.name +" "+employee.strength);
    }
}