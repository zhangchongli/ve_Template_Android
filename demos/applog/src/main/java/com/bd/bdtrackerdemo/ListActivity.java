package com.bd.bdtrackerdemo;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bytedance.applog.AppLog;
import com.volcengine.mars.permissions.PermissionsManager;
import org.json.JSONObject;

/**
 * @author shiyanlong
 */
public class ListActivity extends AppCompatActivity implements OnItemClickListener, OnGroupClickListener,
        OnChildClickListener, OnItemSelectedListener, OnClickListener {

    public static final String KEY_TYPE = "KEY_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra(KEY_TYPE, 0);
        View view;
        if (id == R.id.listView) {
            view = getListView();
        } else if (id == R.id.gridView) {
            view = getGridView();
        } else if (id == R.id.expandableListView) {
            view = getExpandableListView();
        } else if (id == R.id.gallery) {
            view = getGallery();
        } else if (id == R.id.spinner) {
            view = getSpinner();
        } else if (id == R.id.viewPagerS) {
            view = getViewPagerS();
        } else {
            view = getListView();
        }
        setContentView(view);
    }

    /**
     * handle the permission request callbacks
     * */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode >> 16 & 0xffff) == 0) {
            // forwarding request to permission manager
            PermissionsManager.getInstance().notifyPermissionsChange(this, permissions, grantResults);
        }
    }

    @Override
    public void onClick(final View v) {
        Toast.makeText(this, "你点击了" + v.getId(), Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onClick_ListActivity" + v.getClass().getSimpleName(), (JSONObject) null);
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
        Toast.makeText(this, "你选择了第" + String.valueOf(position) + "项。", Toast.LENGTH_SHORT).show();
        if (view != null) {
            AppLog.onEventV3("onItemSelected" + view.getClass().getSimpleName(), (JSONObject) null);
        }
    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {

    }

    private View getSpinner() {
        Spinner spinner = new Spinner(this);
        spinner.setAdapter(getSpinnerAdapter());
        spinner.setOnItemSelectedListener(this);
        return spinner;
    }

    private View getGallery() {
        Gallery g = new Gallery(this);
        g.setAdapter(getSpinnerAdapter());
        g.setOnItemClickListener(this);
        g.setOnItemSelectedListener(this);
        return g;
    }

    private SpinnerAdapter getSpinnerAdapter() {
        SpinnerAdapter sa = new SpinnerAdapter() {
            @Override
            public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
                TextView tv = (TextView) convertView;
                if (tv == null) {
                    tv = new TextView(ListActivity.this);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setHeight(100);
                    tv.setPadding(100, 0, 0, 0);
                }
                tv.setText("这是第" + String.valueOf(position) + "项目。");
                return tv;
            }

            @Override
            public void registerDataSetObserver(final DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(final DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(final int position) {
                return null;
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                TextView tv = (TextView) convertView;
                if (tv == null) {
                    tv = new TextView(ListActivity.this);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setHeight(100);
                    tv.setPadding(100, 0, 0, 0);
                }
                tv.setText("这是第" + String.valueOf(position) + "项目。");
                return tv;
            }

            @Override
            public int getItemViewType(final int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
        return sa;
    }

    @Override
    public boolean onChildClick(final ExpandableListView parent, final View v, final int groupPosition,
            final int childPosition, final long id) {
        Toast.makeText(this, "你点击了第" + String.valueOf(groupPosition) + "组，第" + childPosition + "行.",
                Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onChildClick_ListActivity" + v.getClass().getSimpleName(), (JSONObject) null);
        return true;
    }

    @Override
    public boolean onGroupClick(final ExpandableListView parent, final View v, final int groupPosition,
            final long id) {
        Toast.makeText(this, "你点击了第" + String.valueOf(groupPosition) + "组。", Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onGroupClick_ListActivity" + v.getClass().getSimpleName(), (JSONObject) null);
        return false;
    }

    private View getExpandableListView() {
        ExpandableListView elv = new ExpandableListView(this);
        elv.setOnGroupClickListener(this);
        elv.setOnChildClickListener(this);
        elv.setAdapter(getExpandableListAdapter());
        return elv;
    }

    private ExpandableListAdapter getExpandableListAdapter() {
        ExpandableListAdapter ela = new ExpandableListAdapter() {
            @Override
            public void registerDataSetObserver(final DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(final DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return 10;
            }

            @Override
            public int getChildrenCount(final int groupPosition) {
                return groupPosition * 10 + 1;
            }

            @Override
            public Object getGroup(final int groupPosition) {
                return null;
            }

            @Override
            public Object getChild(final int groupPosition, final int childPosition) {
                return null;
            }

            @Override
            public long getGroupId(final int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(final int groupPosition, final int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(final int groupPosition, final boolean isExpanded, final View convertView,
                    final ViewGroup parent) {
                TextView tv = (TextView) convertView;
                if (tv == null) {
                    tv = new TextView(ListActivity.this);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setHeight(100);
                    tv.setPadding(100, 0, 0, 0);
                }
                tv.setText("这是第" + String.valueOf(groupPosition) + "组。");
                return tv;
            }

            @Override
            public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild,
                    final View convertView, final ViewGroup parent) {
                TextView tv = (TextView) convertView;
                if (tv == null) {
                    tv = new TextView(ListActivity.this);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setHeight(100);
                    tv.setPadding(100, 0, 0, 0);
                }
                tv.setText("这是第" + String.valueOf(groupPosition) + "组，第" + String.valueOf(childPosition) + "项。");
                return tv;
            }

            @Override
            public boolean isChildSelectable(final int groupPosition, final int childPosition) {
                return true;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(final int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(final int groupPosition) {

            }

            @Override
            public long getCombinedChildId(final long groupId, final long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(final long groupId) {
                return 0;
            }
        };
        return ela;
    }

    private GridView getGridView() {
        GridView gridView = new GridView(this);
        gridView.setOnItemClickListener(this);
        gridView.setColumnWidth(50 * 4);
        gridView.setNumColumns(4);
        gridView.setAdapter(getAdapter());
        return gridView;
    }

    private ListAdapter getAdapter() {
        return new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(final int position) {
                return null;
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                TextView tv = (TextView) convertView;
                if (tv == null) {
                    tv = new TextView(ListActivity.this);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setHeight(100);
                    tv.setPadding(100, 0, 0, 0);
                }
                tv.setText("这是第" + String.valueOf(position) + "项目。");
                return tv;
            }
        };
    }

    private View getViewPagerS() {
        ViewPager vp = new ViewPager(this);
        vp.setAdapter(getPageAdapter());
        return vp;
    }

    private PagerAdapter getPageAdapter() {
        PagerAdapter pa = new PagerAdapter() {
            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public boolean isViewFromObject(@NonNull final View view, @NonNull final Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
                TextView tv = new TextView(ListActivity.this);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setHeight(100);
                tv.setWidth(100);
                tv.setPadding(100, 0, 0, 0);
                tv.setText("这是第" + String.valueOf(position) + "页。");
                tv.setOnClickListener(ListActivity.this);
                container.addView(tv);
                return tv;
            }

            @Override
            public void destroyItem(@NonNull final ViewGroup container, final int position,
                    @NonNull final Object object) {
                container.removeView((View) object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(final int position) {
                return "第" + position + "页";
            }

            @Override
            public float getPageWidth(final int position) {
                return super.getPageWidth(position);
            }
        };
        return pa;
    }

    private ListView getListView() {
        ListView listView = new ListView(this);
        listView.setOnItemClickListener(this);
        listView.setAdapter(getAdapter());
        return listView;
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        Toast.makeText(this, "你点击了第" + String.valueOf(position) + "行。", Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onItemClick_ListActivity" + view.getClass().getSimpleName(), (JSONObject) null);
    }
}
