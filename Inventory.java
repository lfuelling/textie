public class Inventory {
	private static final int MAX_SLOTS = 5;
	private boolean alive;
	int[] inventory = new int[MAX_SLOTS];

	public Inventory(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void setItemForStartHack(int item, int slot) {
		this.inventory[slot] = item;
	}
	
	public boolean addToInventory(int objectID, int[] umgebung, int vorhanden) {
		int objektInUmgebung = -128;
		for (int i = 0; i < MAX_SLOTS; i++) {
			if (inventory[i] == 0) {
				inventory[i] = objectID;
				for (int y = 0; y < vorhanden; y++) {
					if (umgebung[y] == objectID) {
						objektInUmgebung = i;
					}
				}
				if (objektInUmgebung != -128) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeFromInventory(int objectID) {
		for (int i = 0; i < MAX_SLOTS; i++) {
			if (inventory[i] == objectID) {
				inventory[i] = 0;
				return true;
			}
		}
		return false;
	}

	public void listInventory() {
		System.out.println("In deiner Tasche befindet sich:");
		for (int i = 0; i < MAX_SLOTS; i++) {
			String objectName = Textie.getObjectName(inventory[i]);
			System.out.println("\t" + objectName);
		}
	}

	public int findInInventory(int objectID) {
		for (int i = 0; i < MAX_SLOTS; i++) {
			if (inventory[i] == objectID) {
				return i;
			}
		}
		return -128;
	}

}
