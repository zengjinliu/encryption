package cn.kiway.webapp.converter;


import cn.kiway.webapp.model.PageModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 刘玉祥
 * @date 2019/8/22 11:04
 */
public interface BaseBeanConverter<P, V> {


    /**
     * po -->vo
     *
     * @param pojo 对象
     * @return
     */
    V toVo(P pojo);

    /**
     * po -->vo
     *
     * @param pojo 对象
     * @return
     */
    List<V> toVo(List<P> pojo);

    /**
     * vo ---> po
     *
     * @param vo
     * @return
     */
    P toPo(V vo);

    /**
     * vo ---> po
     *
     * @param vo
     * @return
     */
    List<P> toPo(List<V> vo);

    /**
     * 分页转换
     *
     * @param pageInfo
     * @return
     */
    default PageModel<V> toPageVo(PageInfo<P> pageInfo) {

        PageModel<V> pageMode = new PageModel<V>();
        pageMode.setPageNum(pageInfo.getPageNum());
        pageMode.setPages(pageInfo.getPages());
        pageMode.setPageSize(pageInfo.getPageSize());
        pageMode.setSize(pageInfo.getSize());

        List<P> poList = pageInfo.getList();
        List<V> voList = toVo(poList);
        pageMode.setData(voList);
        return pageMode;
    }
}
