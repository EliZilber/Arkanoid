package other;
/**
 * counter class.
 */
public class Counter {
    private int value;

    /**
     * constructor.
     * @param initialValue .
     */
    public Counter(int initialValue) {
        this.value = initialValue;
    }
    /**
     * add number to current count.
     * @param number .
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * subtract number from current count.
     * @param number .
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * get current count.
     * @return value of Counter.
     */
    public int getValue() {
        return this.value;
    }
}
