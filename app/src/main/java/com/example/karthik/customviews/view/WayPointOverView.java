package com.example.karthik.customviews.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karthik.customviews.custom.AbstractTripView;
import com.example.karthik.customviews.R;

import java.util.LinkedList;
import java.util.List;

public class WayPointOverView extends AbstractTripView {

  private String name;
  private String time;
  private List<Employee> employeeList;
  private ListView listViewEmployee;

  public WayPointOverView(Context context, ViewGroup rootView, int layoutID,
                          IRootViewCallback callback) {
    super(context, rootView, layoutID, callback);
    listViewEmployee = (ListView) getView().findViewById(R.id.list_employee_details);
  }

  public void injectData(String name, String time) {
    this.name = name;
    this.time = time;
  }

  @Override public void onRenderView() {
    getActionBar().show();
    employeeList = new LinkedList<>();
    for (int i = 0; i < 6; i++) {
      Employee employee = new Employee();
      employee.setName(name);
      employee.setId(i);
      employeeList.add(employee);
    }
    listViewEmployee.setAdapter(new EmployeeAdapter(getContext(), employeeList));
  }

  @Override public void onDestroyView() {
    this.name = null;
    this.time = null;
    employeeList = null;
    listViewEmployee.setAdapter(null);
  }

  private class EmployeeAdapter extends BaseAdapter {

    private Context context;
    private List<Employee> employeeList;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
      this.context = context;
      this.employeeList = employeeList;
    }

    @Override public int getCount() {
      return employeeList.size();
    }

    @Override public Object getItem(int position) {
      return employeeList.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      EmployeeRenderer employeeRenderer;
      final Employee employee = (Employee) getItem(position);
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE);
        int viewId = R.layout.employee_detail_row;
        convertView = inflater.inflate(viewId, parent, false);
        employeeRenderer = new EmployeeRenderer(convertView);
        convertView.setTag(employeeRenderer);
      } else {
        employeeRenderer = (EmployeeRenderer) convertView.getTag();
      }
      employeeRenderer.renderView(employee);
      return convertView;
    }

    private class EmployeeRenderer {
      public TextView employeeName;
      public TextView employeeID;

      public EmployeeRenderer(View view) {
        employeeName = (TextView) view.findViewById(R.id.text_emp_name);
        employeeID = (TextView) view.findViewById(R.id.text_emp_id);
      }

      public void renderView(Employee employee) {
        String empName = "* Employee" + employee.getName() + " : " + name;
        String empID = "Time : " + employee.getId() + " : " + time;
        employeeName.setText(empName);
        employeeID.setText(empID);
      }
    }
  }
}
