package edu.hfut.innovate.common.renren;

import java.io.Serial;
import java.util.HashMap;

/**
 * 返回数据
 * <p>格式如下<pre class="code">
 *     {
 *         code: 200,
 *         msg: 'success',
 *         data: {}
 *     }
 * </pre>
 *
 * @author : Chowhound
 * @since : 2023/07/19 - 00:07
 */
@SuppressWarnings("unused")
public class R extends HashMap<String, Object> {
	@Serial
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 200);
		put("msg", "success");
		putData(null);
	}
	
	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}

	/**
	 * 默认错误为500
	 *
	 * @author : Chowhound
	 * @since : 2023/07/19 - 00:11
	 */
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	
	public static R ok() {
		return new R();
	}
	public static R ok(Object data) {
		return R.ok().putData(data);
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public R putData(Object value) {
		super.put("data", value);
		return this;
	}
}
