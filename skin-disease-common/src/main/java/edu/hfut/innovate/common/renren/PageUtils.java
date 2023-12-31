package edu.hfut.innovate.common.renren;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class PageUtils<T> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private Long totalCount;
	/**
	 * 每页记录数
	 */
	private Long pageSize;
	/**
	 * 总页数
	 */
	private Long totalPage;
	/**
	 * 当前页数
	 */
	private Long currPage;
	/**
	 * 列表数据
	 */
	private List<T> list;

	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<T> list, Long totalCount, Long pageSize, Long currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (long)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(IPage<T> page) {
		this.list = page.getRecords();
		this.totalCount = page.getTotal();
		this.pageSize = page.getSize();
		this.currPage = page.getCurrent();
		this.totalPage = page.getPages();
	}
}
