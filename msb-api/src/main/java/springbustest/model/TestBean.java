package springbustest.model;

import com.mvw.china.bus.annotation.ApiDescribe;

/**
 * 测试注入bean
 * 
 * @author gaotingping@cyberzone.cn
 *
 */
@ApiDescribe("测试注入bean")
public class TestBean {

	@ApiDescribe("主鍵")
	private int id;
	
	@ApiDescribe("描述")
	private String name;
	
	@ApiDescribe("引用本身")
	private TestBean2 vo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestBean2 getVo() {
		return vo;
	}

	public void setVo(TestBean2 vo) {
		this.vo = vo;
	}
}
