package cn.kiway.webapp.model;

import lombok.Data;

import java.util.List;

/**
 * @author  刘玉祥
 * @param <V> 视图实体
 */
@Data
public class PageModel<V> {

	/**
	 * 降序
	 */
	public static final String ACS    ="ACS";
	
	/**
	 * 升序
	 */
	public static final String DESC  = "desc";
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页的数量
     */
    private int pageSize;
    /**
     * 当前页的数量
     */
    private int size;
    /**
     * 数据
     */
    private List<V> data;
    /**
     * 请求次数
     */
   private int draw;
    /**
     * 总记录数据
     */
  private long recordsTotal;

    /**
     * 过滤总数据
     */
  private long recordsFiltered;

    /**
     * 总页数
     */
  private int pages;

    /**
     * 排序
     */
  private String orderBy;


}
