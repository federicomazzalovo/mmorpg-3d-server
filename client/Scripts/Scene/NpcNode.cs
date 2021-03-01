using Godot;

public class NpcNode : CharacterNode
{
	const int walkSpeed = 200;
	Vector2 velocity;

	private AnimatedSprite npcSprite { get; set; }

	private bool _selected;
	public bool IsSelected
	{
		get
		{
			return this._selected;
		}
		set
		{
			if (value)
				this.SelectedBorder.Show();
			else
				this.SelectedBorder.Hide();

			this._selected = value;
		}
	}

	public Battlefield ParentNode { get; private set; }
	public ColorRect SelectedBorder { get; private set; }

	public override void _Ready()
	{
		base._Ready();

		this.ParentNode = this.GetNode("/root/Battlefield") as Battlefield;
		this.SelectedBorder = this.GetNode("SelectedBorder") as ColorRect;
	}

    public override void _PhysicsProcess(float delta)
    {
        base._PhysicsProcess(delta);

		if (this.npcSprite == null)
			this.npcSprite = this.GetNode("CharacterSprite") as AnimatedSprite;


		if (this.npcSprite != null)
		{
			npcSprite.FlipH = true;
			npcSprite.Play("walk_side_facing");
			velocity.x = walkSpeed;
			MoveAndSlide(velocity, new Vector2(0, -1));
		}
	}

    public void Deselect()
	{
		this.IsSelected = false;
	}

	public void _on_SelectedButton_pressed()
	{
		this.ParentNode.DeselectAllEnemies();
		this.IsSelected = true;
	}
}
