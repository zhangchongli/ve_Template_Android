package com.volcengine.mars.app

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.volcengine.mars.activity.BaseActivity
import com.volcengine.mars.app.R.id
import com.volcengine.mars.app.R.layout

class DeveloperActivity : BaseActivity() {

    private var demoLabel: HashMap<ResolveInfo, CharSequence> = HashMap()

    private val resolveInfosOrigin by lazy {
        var intent = Intent()
        intent.`package` = this@DeveloperActivity.packageName
        intent.action = "com.volcengine.mars.action.demo"
        val res = this@DeveloperActivity.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in res) {
            val label = resolveInfo.activityInfo.loadLabel(this@DeveloperActivity.packageManager)
            demoLabel[resolveInfo] = label
        }
        res
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_developer_layout)
        val demoAdapter = DemoAdapter(resolveInfosOrigin);
        val listView = findViewById<ListView>(id.demoList)
        listView.adapter = demoAdapter
        listView.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            var item = demoAdapter.getItem(position - listView.headerViewsCount)
            var activity = item.activityInfo.name
            val intent = Intent(DeveloperActivity@ this, Class.forName(activity))
            startActivity(intent)
        }
    }

    inner class DemoAdapter(var resolveInfos: MutableList<ResolveInfo>) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            if (convertView == null) {
                val viewHolder = ViewHolder()
                val contentView = LayoutInflater.from(this@DeveloperActivity)
                    .inflate(layout.listview_item, null, false)
                contentView.tag = viewHolder
                viewHolder.tv = contentView.findViewById(id.tv_item)
                viewHolder.tv.text = demoLabel[getItem(position)]
                return contentView;
            } else {
                val viewHolder = convertView.tag as ViewHolder
                viewHolder.tv.text = demoLabel[getItem(position)]
                return convertView;
            }
        }

        override fun getItem(position: Int): ResolveInfo {
            return resolveInfos[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return resolveInfos.size;
        }

        private inner class ViewHolder() {
            lateinit var tv: TextView
        }
    }
}