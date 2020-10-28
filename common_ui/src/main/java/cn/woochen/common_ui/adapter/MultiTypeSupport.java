package cn.woochen.common_ui.adapter;

/**
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/10/28 5:33 PM
 * 修改备注：
 **/
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    int getLayoutId(T item,int position);
}
