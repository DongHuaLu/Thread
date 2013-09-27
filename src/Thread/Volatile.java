package Thread;

public class Volatile {

	private volatile static int volatileCount = 0;
	private static int syncCount = 0;
	private static int defalutCount = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Volatile().call();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("volatileCount=" + volatileCount);
			System.out.println("syncCount=" + syncCount);
			System.out.println("defalutCount" + defalutCount);
		}
	}

	public void call() {
		for (int i = 0; i < 50; i++) {
			new Thread() {
				@Override
				public void run() {
					volatileCount++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					volatileCount++;
					defalutCount += 2;
					synchronized (Volatile.class) {
						syncCount++;
						syncCount++;
					}
				}
			}.start();
		}
	}

}
