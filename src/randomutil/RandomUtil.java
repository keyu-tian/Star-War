package randomutil;

public class RandomUtil {

	public static int randomInRange(int begin, int end) {
		return begin + (int)(Math.random() * (end - begin));
	}
}
