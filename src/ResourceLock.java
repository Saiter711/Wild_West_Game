/**
 * 
 * Klasa przechowująca zmienną flag
 *
 */

public class ResourceLock {
	/**
	 * Zmienna przechowująca wartość aktualnego poziomu 
	 */
	public volatile int flag = GamePanel.level;
}