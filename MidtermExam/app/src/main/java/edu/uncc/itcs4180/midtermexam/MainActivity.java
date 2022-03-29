package edu.uncc.itcs4180.midtermexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements ExpensesFragment.IExpenseFragment,
        AddExpenseFragment.IAddExpenseFragmentListener, PickCategoryFragment.IPickCategoryFragment {
    private static final String TAG = "midterm-MainActivity";
    public ArrayList<Expense> expenseArrayList = new ArrayList<>();
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load initial fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerView, new ExpensesFragment())
                .commit();
    }

    @Override
    public void deleteExpense(Expense expense) {
        Log.d(TAG, "deleteExpense: " + expense.getName());
        expenseArrayList.remove(expense);
    }

    @Override
    public void setCurrentFragment(int fragment) {
        switch (fragment) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, ExpensesFragment.newInstance(expenseArrayList))
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + fragment);
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, ExpensesSummaryFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + fragment);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, AddExpenseFragment.newInstance(category))
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + fragment);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, PickCategoryFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + fragment);
                break;
            default:
                Log.e(TAG, "setCurrentFragment: Error selecting fragment!");
        }
    }

    @Override
    public void addExpense(Expense expense) {
        expenseArrayList.add(expense);
        Collections.sort(expenseArrayList);
    }

    @Override
    public void setCurrentCategory(String category) {
        this.category = category;
    }
}