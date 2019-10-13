package app;

public class JoJoCharacter extends WeightedItem {
	private String name;
	private String imageName;
	
	public JoJoCharacter(String name, String imageName, double weight) {
		super(weight);
		this.name = name;
		this.imageName = imageName;
	}
	
    @Override
    public String toString() {
        return this.imageName;
    }

	public String getName() {
		return name;
	}

	public String getImageName() {
		return imageName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}
