public abstract class Raum {
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
}
