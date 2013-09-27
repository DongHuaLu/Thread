package Thread;

public class ThreadCommunication {

	public static void main(String[] args) {

		final Business b = new Business();
		new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					b.sub(i);
				}
			};
		}.start();

		for (int i = 0; i < 50; i++) {
			b.main(i);
		}
	}
}

class Business {
	private boolean runSub = true;

	public synchronized void sub(int i) {
		while (!runSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 50; j++) {
			System.out.println("sub thread " + j + " loop " + i);
		}
		runSub = false;
		System.out.println("+++++++++++++++++++++++++++++++++");
		this.notify();
	}

	public synchronized void main(int i) {
		while (runSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 50; j++) {
			System.out.println("main thread " + j + " loop " + i);
		}
		runSub = true;
		System.out.println("-----------------------------------");
		this.notify();
	}

}
