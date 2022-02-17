package collisions.listeners;

/**
 * interface.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl .
     */
    void addHitListener(HitListener hl);
    /**
     * Remove hl from the list of collisions.listeners to hit events.
     * @param hl .
     */
    void removeHitListener(HitListener hl);
}
