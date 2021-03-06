package umn.ac.mecinan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import umn.ac.mecinan.R;
import umn.ac.mecinan.activity.EmployeeDetails;
import umn.ac.mecinan.model.User;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> implements Filterable {

    private Context mCtx;
    private List<User> employeeList;
    private boolean myRequest;

    private List<User> searchList;
    private List<User> storedList;

    public EmployeeAdapter(Context mCtx, List<User> employeeList) {
        this.mCtx = mCtx;
        this.employeeList = this.searchList = this.storedList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.employee_list, viewGroup, false);
        EmployeeAdapter.EmployeeViewHolder holder = new EmployeeAdapter.EmployeeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder employeeViewHolder, int i) {
        final User employee = storedList.get(i);
        Float rating;
        Integer completed_project;

        completed_project = employee.getTotalProjectCompleted();

        if(employee.getTotalProjectCompleted() > 0) {
            rating = employee.getRatingEmployee() / completed_project;
        } else {
            rating = employee.getRatingEmployee();
        }

        employeeViewHolder.employeeName.setText(employee.getUsername());
        employeeViewHolder.employeePhone.setText(employee.getPhoneNumber());
        //employeeViewHolder.employeeDesc.setText(employee.getDesc());
        employeeViewHolder.employeeCategory.setText(employee.getCategory());
        employeeViewHolder.employeeField.setText(employee.getField());
        employeeViewHolder.employeeRatingBar.setRating(rating);
        employeeViewHolder.employeeRatingText.setText(rating.toString());
        employeeViewHolder.employeeCompletedProject.setText(completed_project.toString());
        employeeViewHolder.employeeFee.setText("Fee: " + employee.getFee());
        employeeViewHolder.employeeId.setText(employee.getId());

        /** Recycler On Click Listener */
        employeeViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EmployeeDetails.class);

                intent.putExtra("username", employee.getUsername());
                intent.putExtra("field", employee.getField());
                intent.putExtra("fee", employee.getFee());
                intent.putExtra("phone", employee.getPhoneNumber());
                intent.putExtra("id_employee", employee.getId());
                intent.putExtra("category", employee.getCategory());
                intent.putExtra("rating", employee.getRatingEmployee());
                intent.putExtra("completed_project", employee.getTotalProjectCompleted());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storedList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView employeeName, employeePhone, employeeRatingText, employeeCategory, employeeField, employeeFee, employeeCompletedProject, employeeId;
        RatingBar employeeRatingBar;
        View view;

        public EmployeeViewHolder(@NonNull final View itemView) {
            super(itemView);

            employeeName = itemView.findViewById(R.id.employeeName);
            employeePhone = itemView.findViewById(R.id.tv_employeeList_phone);
            employeeCategory = itemView.findViewById(R.id.tv_employeeList_cat);
            employeeField = itemView.findViewById(R.id.employeeField);
            employeeFee = itemView.findViewById(R.id.tv_employeeList_fee);
            employeeRatingBar = itemView.findViewById(R.id.employeeRatingBar);
            employeeRatingText = itemView.findViewById(R.id.tv_employeeList_rating);
            employeeCompletedProject = itemView.findViewById(R.id.employeeCompletedProject);
            employeeId = itemView.findViewById(R.id.tv_employeeList_id);
            view = itemView;
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                Log.d("charString", charString);
                if (charString.isEmpty()) {
                    searchList = storedList = employeeList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : employeeList) {
                        Log.d("user", row.getUsername());
                        if (row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                            Log.d("username ", "username sama");
                            filteredList.add(row);
                        }
                    }
                    searchList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchList = (ArrayList<User>) results.values;
                storedList = searchList;
                notifyDataSetChanged();
            }
        };
    }
}
