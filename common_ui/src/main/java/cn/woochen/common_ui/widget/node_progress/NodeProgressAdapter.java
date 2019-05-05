package cn.woochen.common_ui.widget.node_progress;

import java.util.List;

public interface NodeProgressAdapter{

    /**
     * 返回集合大小
     *
     * @return
     */
    int getCount();

    /**
     * 适配数据的集合
     *
     * @return
     */
    List<LogisticsData> getData();

}