package com.jhl.pojo.weixin.menu;
/**
 * 复杂按钮（父类按钮）
 * @author liurui
 * @2016年3月5日 @下午5:47:12
 */
public class ComplexButton extends Button{

	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}


}
