package com.saike.tzh.homepage.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.saike.tzh.R;
import com.saike.tzh.base.BaseFragment;

/**
 * 当前类是侧滑菜单内容
 * @author fenegdonfei
 *
 */
public class MenuFragment extends Fragment {
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	//返回指定Fragment对象的展示形式，Activity，setContentView(R.layout.main)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//加载当前Fragment对应的布局
		view = View.inflate(getActivity(), R.layout.menu_slide_view, null);
		return view;
	}

	//数据填充UI的操作
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		ListView listView = (ListView) view.findViewById(R.id.list_view);
		listView.setAdapter(new ArrayAdapter<String>(
				getActivity(),
				//item对应的布局文件
				android.R.layout.simple_list_item_1,
				android.R.id.text1,
				getData()));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				//在此次触发替换内容页内容操作
				BaseFragment baseFragment = null;
				switch (arg2) {
					case 0:
						//创建第0个条目对应的Fragment对象，然后去替换内容页面帧布局内部内容
						baseFragment = new MineFragment();
						break;
					case 1:
						baseFragment = new MainFragment();
						break;
					case 2:
						baseFragment = new MainFragment();
						break;
					case 3:
						baseFragment = new MainFragment();
						break;
					case 4:
						baseFragment = new MainFragment();
						break;
				}

				//baseFragment替换MainActivity对应内容页的帧布局内部内容
				replaceFragment(R.id.frame_container,baseFragment);
			}
		});

		super.onActivityCreated(savedInstanceState);
	}
	public void replaceFragment(int id_content, Fragment fragment) {
		FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
		transaction.replace(id_content, fragment);
		transaction.commit();
	}
	private void switchFragment(BaseFragment baseFragment) {
		//调用MainActivity上方法做替换，MainActivity对象
		if(getActivity() instanceof MainActivity_menudrawer_toolbar){
			//getActivity()是MainActivity的对象，或者是其子类对象
			((MainActivity_menudrawer_toolbar)getActivity()).replaceFragment(R.id.frame_container,baseFragment);
		}
	}
	private List<String> getData() {
		List<String> arrayList = new ArrayList<String>();
		arrayList.add("fragment0");
		arrayList.add("fragment1");
		arrayList.add("fragment2");
		arrayList.add("fragment3");
		arrayList.add("fragment4");
		return arrayList;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

//		if (activity instanceof MainActivity_drawlayout_toolbar ) {
//			MainActivity_drawlayout_toolbar mainActivity = (MainActivity_drawlayout_toolbar) activity;
//		} else {
//			throw new IllegalArgumentException("The activity must be a MainActivity !");
//		}
	}
}
