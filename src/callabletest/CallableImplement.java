package callabletest;

import java.util.Random;

/***
 * 单线程回调模板的使用示例
 * 
 * @author dlu
 * 
 */
public class CallableImplement {
	private static int data = 0;

	public static void main(String[] args) {
		CallableAndFutureTemplate s = new CallableAndFutureTemplate() {

			@Override
			public void onSuccess(Object o) {
				System.out.println("成功逻辑 " + o);
			}

			@Override
			public void onFaile() {
				System.out.println("失败逻辑");
			}

			@Override
			public Object execute() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "成功返回";
			}
		};
		s.setTimeout(1);
		s.get();
	}
}