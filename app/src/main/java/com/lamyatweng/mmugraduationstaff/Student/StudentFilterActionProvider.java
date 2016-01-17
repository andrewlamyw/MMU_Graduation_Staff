package com.lamyatweng.mmugraduationstaff.Student;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class StudentFilterActionProvider extends ActionProvider implements MenuItem.OnMenuItemClickListener {
    Context mContext;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public StudentFilterActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        // Only add item once, not every time the sub menu is clicked
        if (subMenu.size() == 0) {
            subMenu.add("Filter by status").setOnMenuItemClickListener(this);
            subMenu.add("Filter by programme").setOnMenuItemClickListener(this);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Filter by status":
                StudentStatusDialogFragment studentStatusDialogFragment = new StudentStatusDialogFragment();
                // I want to get FragmentManager here to launch a dialog
                return true;
            case "Filter by programme":
                return true;
        }
        return false;
    }
}
