using Godot;
using Simplerpgkataclient.Network;

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
			velocity.x = walkSpeed;
			MoveAndSlide(velocity, new Vector2(0, -1));
		}
	}

	public void UpdateSprite(MoveDirection moveDirection)
    {
		switch (moveDirection)
        {
			case MoveDirection.None:
				this.npcSprite.Play("idle");
				break;
			case MoveDirection.Up:
				this.npcSprite.Play("walk_up_facing");
				break;
			case MoveDirection.Right:
				this.npcSprite.FlipH = true;
				this.npcSprite.Play("walk_side_facing");
				break;
			case MoveDirection.Down:
				this.npcSprite.Play("walk_down_facing");
				break;
			case MoveDirection.Left:
				this.npcSprite.FlipH = false;
				this.npcSprite.Play("walk_side_facing");
				break;
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
