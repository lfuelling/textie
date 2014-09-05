public abstract class Raum {
	public static final int MAX_SLOTS_UMGEBUNG = 4;
	protected int[] items;
	protected Inventory inventory;

	public Raum(Inventory inventory, int... items) {
		this.inventory = inventory;
		this.items = items;
	}

	public abstract void start();

	public String[] parse() {
		String command = IOUtils.readine("Was möchtest du tun? ");
		String[] parsed_command = Textie.parseInput(command);
		return parsed_command;
	}

	public void listItems() {
		System.out.println("Im Raum befindet sich:");
		for (int i = 0; i < MAX_SLOTS_UMGEBUNG; i++) {
			String objectName = Textie.getObjectName(items[i]);
			System.out.println("\t" + objectName);
		}
	}
 /**
  * 
  * @param objectID
  * @return Returns -128 when the item is not in the room.
  */
	public int find(int objectID) {
		for (int i = 0; i < MAX_SLOTS_UMGEBUNG; i++) {
			if (items[i] == objectID) {
				return i;
			}
		}
		return -128;
	}
	
	public boolean isInRoom(int objectID) {
		for (int i = 0; i < MAX_SLOTS_UMGEBUNG; i++) {
			if (items[i] == objectID) {
				return true;
			}
		}
		return false;
	}
}
