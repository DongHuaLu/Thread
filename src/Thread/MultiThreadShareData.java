package Thread;

/***
 * 通过synchronized锁住共享类变量来防止线程安全问题
 * 
 * @author dlu
 * 
 */
public class MultiThreadShareData {
	private static int j = 2;

	public static void main(String[] args) {

		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (MultiThreadShareData.class) {
						j++;
					}
				}
			});
		}
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (MultiThreadShareData.class) {
						j--;
					}
				}
			});
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(j);

	}
}
